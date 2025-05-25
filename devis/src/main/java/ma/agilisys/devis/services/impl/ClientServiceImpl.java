package ma.agilisys.devis.services.impl;

import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.ClientPageDto;
import ma.agilisys.devis.dtos.ClientRequestDto;
import ma.agilisys.devis.dtos.ClientResponseDto;
import ma.agilisys.devis.exceptions.ResourceNotFoundException;
import ma.agilisys.devis.mappers.ClientMapper;
import ma.agilisys.devis.mappers.ContactMapper;
import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.models.Contact;
import ma.agilisys.devis.models.Devis;
import ma.agilisys.devis.repositories.ClientRepository;
import ma.agilisys.devis.repositories.DevisRepository;
import ma.agilisys.devis.services.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ContactMapper contactMapper;
    private final DevisRepository devisRepository;

    @Override
    public ClientResponseDto getClientById(Long id) {
        Client client = clientRepository.findByIdWithContacts(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return clientMapper.toDto(client);
    }

    @Override
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        if (existsByIce(clientRequestDto.getIce())) {
            throw new IllegalArgumentException("Un client avec cet ICE existe déjà");
        }

        Client client = clientMapper.toEntity(clientRequestDto);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }

    @Override
    public boolean existsByIce(String ice) {
        return clientRepository.existsByIce(ice);
    }


    @Override
    public ClientPageDto getAllClients(int page, int size) {
        Page<Client> clientPage = clientRepository.findAll(PageRequest.of(page, size));
        List<ClientResponseDto> clientList = clientPage.getContent().stream().map(clientMapper::toDto).toList();
        return ClientPageDto.builder().clients(clientList).totalPages(clientPage.getTotalPages()).pageSize(size).totalElements(clientPage.getTotalElements()).currentPage(page).build();
    }

    @Override
    public ClientResponseDto updateClient(Long id, ClientResponseDto clientDto) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        Client clientFromDto = clientMapper.toEntity(clientDto);
        clientFromDto.setId(existingClient.getId());
        clientFromDto.setDateCreation(existingClient.getDateCreation());
        if (clientDto.getContacts() != null) {
            Set<Contact> updatedContacts = new HashSet<>();
            clientDto.getContacts().forEach(contactDto -> {
                Contact contact = contactMapper.toEntity(contactDto);
                contact.setClient(clientFromDto);
                updatedContacts.add(contact);
            });
            clientFromDto.setContacts(updatedContacts);
        } else {
            clientFromDto.setContacts(existingClient.getContacts());
        }
        List<Devis> associatedDevis = devisRepository.findByClientId(id);
        if (!associatedDevis.isEmpty()) {
            associatedDevis.forEach(devis -> devis.setClient(clientFromDto));
        }
        Client updatedClient = clientRepository.save(clientFromDto);
        return clientMapper.toDto(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        List<Devis> associatedDevis = devisRepository.findByClientId(id);
        if (!associatedDevis.isEmpty()) {
            devisRepository.deleteAll(associatedDevis);
        }
        clientRepository.delete(client);
    }
}