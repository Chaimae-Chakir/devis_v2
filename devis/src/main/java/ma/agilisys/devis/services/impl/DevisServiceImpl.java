package ma.agilisys.devis.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.ClientResponseDto;
import ma.agilisys.devis.dtos.DevisPageDto;
import ma.agilisys.devis.dtos.DevisRequestDTO;
import ma.agilisys.devis.dtos.DevisResponseDTO;
import ma.agilisys.devis.mappers.DevisLigneMapper;
import ma.agilisys.devis.mappers.DevisMapper;
import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.models.Devis;
import ma.agilisys.devis.models.DevisLigne;
import ma.agilisys.devis.models.DevisMeta;
import ma.agilisys.devis.repositories.ClientRepository;
import ma.agilisys.devis.repositories.DevisPdfFileRepository;
import ma.agilisys.devis.repositories.DevisRepository;
import ma.agilisys.devis.services.ClientService;
import ma.agilisys.devis.services.DevisService;
import ma.agilisys.devis.utils.Constants;
import ma.agilisys.devis.utils.DevisNumberGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DevisServiceImpl implements DevisService {
    private final DevisRepository devisRepository;
    private final ClientRepository clientRepository;
    private final DevisNumberGenerator devisNumberGenerator;
    private final DevisMapper devisMapper;
    private final DevisLigneMapper devisLigneMapper;
    private final DevisPdfFileRepository devisPdfFileRepository;
    private final ClientService clientService;

    @Override
    public DevisPageDto getAllDevis(int page, int size) {
        Page<Devis> devisPage = devisRepository.findAll(PageRequest.of(page, size));
        return DevisPageDto.builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(devisPage.getTotalPages())
                .totalElements(devisPage.getTotalElements())
                .devis(devisPage.getContent().stream().map(devisMapper::toDto).toList())
                .build();
    }

    @Override
    public DevisResponseDTO getDevisById(Long devisId) {
        Devis devis = devisRepository.findById(devisId)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'id: " + devisId));
        return devisMapper.toDto(devis);
    }

    @Transactional
    @Override
    public DevisResponseDTO createDevis(DevisRequestDTO devisRequestDTO) {
        ClientResponseDto client = clientService.createClient(devisRequestDTO.getClient());
        Client clientEntity = clientRepository.findById(client.getId()).orElseThrow(() -> new EntityNotFoundException("client no found"));
        Devis devis = devisMapper.toEntity(devisRequestDTO);
        devis.setNumero(devisNumberGenerator.generateUniqueNumber());
        devis.setStatut(Constants.DRAFT_STATUS);
        devis.setClient(clientEntity);
        devis.setDateCreation(ZonedDateTime.now());
        devis.setCreatedBy(client.getNom());
        List<DevisLigne> lignes = devisRequestDTO.getLignes().stream()
                .map(devisLigneMapper::toEntity)
                .toList();
        lignes.forEach(ligne -> ligne.setDevis(devis));
        devis.setLignes(lignes);
        DevisMeta meta = DevisMeta.builder()
                .conditions(devisRequestDTO.getConditions())
                .offreFonctionnelle(devisRequestDTO.getOffreFonctionnelle())
                .offreTechnique(devisRequestDTO.getOffreTechnique())
                .perimetre(devisRequestDTO.getPerimetre())
                .offrePdfUrl(devisRequestDTO.getOffrePdfUrl())
                .planning(devisRequestDTO.getPlanning())
                .devis(devis)
                .build();
        devis.setMeta(meta);
        Devis savedDevis = devisRepository.save(devis);
        return devisMapper.toDto(savedDevis);
    }

    @Transactional
    @Override
    public DevisResponseDTO updateDevis(Long id, DevisRequestDTO devisRequestDTO) {
        Devis existingDevis = devisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'ID: " + id));
        if (existingDevis.getMeta() != null) {
            existingDevis.getMeta().setPerimetre(devisRequestDTO.getPerimetre());
            existingDevis.getMeta().setOffreFonctionnelle(devisRequestDTO.getOffreFonctionnelle());
            existingDevis.getMeta().setOffreTechnique(devisRequestDTO.getOffreTechnique());
            existingDevis.getMeta().setConditions(devisRequestDTO.getConditions());
            existingDevis.getMeta().setPlanning(devisRequestDTO.getPlanning());
            existingDevis.getMeta().setOffrePdfUrl(devisRequestDTO.getOffrePdfUrl());
        }
        if (devisRequestDTO.getClient() != null) {
            existingDevis.getClient().setNom(devisRequestDTO.getClient().getNom());
            existingDevis.getClient().setAdresse(devisRequestDTO.getClient().getAdresse());
            existingDevis.getClient().setIce(devisRequestDTO.getClient().getIce());
            existingDevis.getClient().setVille(devisRequestDTO.getClient().getVille());
            existingDevis.getClient().setPays(devisRequestDTO.getClient().getPays());
        }
        existingDevis.getLignes().clear();
        List<DevisLigne> nouvelleLignes = devisRequestDTO.getLignes().stream()
                .map(devisLigneMapper::toEntity)
                .toList();
        nouvelleLignes.forEach(ligne -> ligne.setDevis(existingDevis));
        existingDevis.getLignes().addAll(nouvelleLignes);
        existingDevis.calculerTotals();
        Devis updatedDevis = devisRepository.save(existingDevis);
        return devisMapper.toDto(updatedDevis);
    }

    @Transactional
    @Override
    public DevisResponseDTO validateDevis(Long id, String validatedBy) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'ID: " + id));
        if (!Constants.DRAFT_STATUS.equals(devis.getStatut())) {
            throw new IllegalStateException("Seul un devis en statut Brouillon peut être validé");
        }
        devis.setStatut(Constants.APPROVED_STATUS);
        devis.setDateValidation(ZonedDateTime.now());
        devis.setValidatedBy(validatedBy);
        Devis validatedDevis = devisRepository.save(devis);
        return devisMapper.toDto(validatedDevis);
    }

    @Transactional
    @Override
    public DevisResponseDTO duplicateDevis(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'ID: " + id));
        Devis duplicatedDevis = new Devis();
        duplicatedDevis.setNumero(devisNumberGenerator.generateUniqueNumber());
        duplicatedDevis.setClient(devis.getClient());
        duplicatedDevis.setStatut(Constants.DRAFT_STATUS);
        duplicatedDevis.setDateCreation(ZonedDateTime.now());
        duplicatedDevis.setCreatedBy(devis.getCreatedBy());
        List<DevisLigne> newLignes = new ArrayList<>();
        for (DevisLigne ligne : devis.getLignes()) {
            DevisLigne newLigne = new DevisLigne();
            newLigne.setDescriptionLibre(ligne.getDescriptionLibre());
            newLigne.setPrixUnitaireHt(ligne.getPrixUnitaireHt());
            newLigne.setQuantite(ligne.getQuantite());
            newLigne.setRistournePct(ligne.getRistournePct());
            newLigne.setTvaPct(ligne.getTvaPct());
            newLigne.setDevis(duplicatedDevis);
            newLignes.add(newLigne);
        }
        duplicatedDevis.setLignes(newLignes);
        if (devis.getMeta() != null) {
            DevisMeta newMeta = DevisMeta.builder()
                    .perimetre(devis.getMeta().getPerimetre())
                    .offreFonctionnelle(devis.getMeta().getOffreFonctionnelle())
                    .offreTechnique(devis.getMeta().getOffreTechnique())
                    .conditions(devis.getMeta().getConditions())
                    .planning(devis.getMeta().getPlanning())
                    .devis(duplicatedDevis)
                    .build();
            duplicatedDevis.setMeta(newMeta);
        }
        Devis savedDuplicate = devisRepository.save(duplicatedDevis);
        return devisMapper.toDto(savedDuplicate);
    }

    @Transactional
    @Override
    public void deleteDevis(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Devis non trouvé avec l'ID: " + id));
        devisPdfFileRepository.findByDevis_Id(id).ifPresent(devisPdfFileRepository::delete);
        devisRepository.delete(devis);
    }

    @Override
    public DevisPageDto getAllDevisByClientId(Long id, int page, int size) {
        Page<Devis> devisPage = devisRepository.findAllByClient_Id(id, PageRequest.of(page, size));
        return DevisPageDto.builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(devisPage.getTotalPages())
                .totalElements(devisPage.getTotalElements())
                .devis(devisPage.getContent().stream().map(devisMapper::toDto).toList())
                .build();
    }
}