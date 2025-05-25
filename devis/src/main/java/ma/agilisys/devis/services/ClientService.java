package ma.agilisys.devis.services;

import ma.agilisys.devis.dtos.ClientPageDto;
import ma.agilisys.devis.dtos.ClientRequestDto;
import ma.agilisys.devis.dtos.ClientResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface ClientService {

    ClientResponseDto getClientById(Long id);

    ClientResponseDto createClient(ClientRequestDto clientRequestDto);

    boolean existsByIce(String ice);

    ClientPageDto getAllClients(int page, int size);


    @Transactional
    ClientResponseDto updateClient(Long id, ClientResponseDto clientdto);

    void deleteClient(Long id);
}
