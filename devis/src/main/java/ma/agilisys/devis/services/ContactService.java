package ma.agilisys.devis.services;

import ma.agilisys.devis.dtos.ContactRequestDto;
import ma.agilisys.devis.dtos.ContactResponseDto;

import java.util.List;

public interface ContactService {

    ContactResponseDto createContact(ContactRequestDto contactRequestDto);

    List<ContactResponseDto> getContactsByClient(Long clientId);
}