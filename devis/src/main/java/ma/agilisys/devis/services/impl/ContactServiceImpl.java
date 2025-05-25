package ma.agilisys.devis.services.impl;

import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.ContactRequestDto;
import ma.agilisys.devis.dtos.ContactResponseDto;
import ma.agilisys.devis.exceptions.ResourceNotFoundException;
import ma.agilisys.devis.mappers.ContactMapper;
import ma.agilisys.devis.models.Client;
import ma.agilisys.devis.models.Contact;
import ma.agilisys.devis.repositories.ClientRepository;
import ma.agilisys.devis.repositories.ContactRepository;
import ma.agilisys.devis.services.ContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;
    private final ContactMapper contactMapper;


    @Override
    public ContactResponseDto createContact(ContactRequestDto contactRequestDto) {
        Client client = clientRepository.findById(contactRequestDto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + contactRequestDto.getClientId()));

        Contact contact = contactMapper.toEntity(contactRequestDto);
        contact.setClient(client);

        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toDto(savedContact);
    }

    @Override
    public List<ContactResponseDto> getContactsByClient(Long clientId) {
        return contactRepository.findByClientId(clientId).stream()
                .map(contactMapper::toDto)
                .toList();
    }
}
