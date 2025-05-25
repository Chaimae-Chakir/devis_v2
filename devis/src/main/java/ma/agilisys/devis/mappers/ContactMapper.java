package ma.agilisys.devis.mappers;

import ma.agilisys.devis.dtos.ContactRequestDto;
import ma.agilisys.devis.dtos.ContactResponseDto;
import ma.agilisys.devis.models.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactResponseDto toDto(Contact contact);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "client", ignore = true)
    Contact toEntity(ContactRequestDto contactRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "client", ignore = true)
    Contact toEntity(ContactResponseDto contactResponseDto);
}