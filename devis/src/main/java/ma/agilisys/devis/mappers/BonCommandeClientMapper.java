package ma.agilisys.devis.mappers;

import ma.agilisys.devis.dtos.BonCommandeClientDTO;
import ma.agilisys.devis.models.BonCommandeClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BonCommandeClientMapper {

    @Mapping(target = "devisId", source = "devis.id")
    @Mapping(target = "devisNumero", source = "devis.numero")
    @Mapping(target = "contactClientId", source = "contactClient.id")
    BonCommandeClientDTO toDto(BonCommandeClient bonCommandeClient);

    @Mapping(target = "devis.id", source = "devisId")
    @Mapping(target = "contactClient.id", source = "contactClientId")
    BonCommandeClient toEntity(BonCommandeClientDTO bonCommandeClientDTO);
}