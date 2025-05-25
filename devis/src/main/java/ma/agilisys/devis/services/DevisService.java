package ma.agilisys.devis.services;

import jakarta.transaction.Transactional;
import ma.agilisys.devis.dtos.DevisPageDto;
import ma.agilisys.devis.dtos.DevisRequestDTO;
import ma.agilisys.devis.dtos.DevisResponseDTO;

public interface DevisService {
    DevisPageDto getAllDevis(int page, int size);

    DevisResponseDTO getDevisById(Long devisId);

    @Transactional
    DevisResponseDTO createDevis(DevisRequestDTO devisRequestDTO);

    @Transactional
    DevisResponseDTO updateDevis(Long id, DevisRequestDTO devisRequestDTO);

    @Transactional
    DevisResponseDTO validateDevis(Long id, String validatedBy);

    @Transactional
    DevisResponseDTO duplicateDevis(Long id);

    @Transactional
    void deleteDevis(Long id);

    DevisPageDto getAllDevisByClientId(Long id, int page, int size);
}
