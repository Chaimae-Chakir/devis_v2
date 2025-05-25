package ma.agilisys.devis.mappers;

import ma.agilisys.devis.dtos.ClientRequestDto;
import ma.agilisys.devis.dtos.ClientResponseDto;
import ma.agilisys.devis.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ContactMapper.class)
public interface ClientMapper {
    @Mapping(target = "contacts", source = "contacts")
    ClientResponseDto toDto(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "devis", ignore = true)
    Client toEntity(ClientRequestDto clientRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "devis", ignore = true)
    Client toEntity(ClientResponseDto clientResponseDto);
}