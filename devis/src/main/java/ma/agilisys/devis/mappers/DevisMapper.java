package ma.agilisys.devis.mappers;

import ma.agilisys.devis.dtos.DevisRequestDTO;
import ma.agilisys.devis.dtos.DevisResponseDTO;
import ma.agilisys.devis.models.Devis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DevisLigneMapper.class})
public interface DevisMapper {

    @Mapping(target = "perimetre", source = "meta.perimetre")
    @Mapping(target = "offreFonctionnelle", source = "meta.offreFonctionnelle")
    @Mapping(target = "offreTechnique", source = "meta.offreTechnique")
    @Mapping(target = "conditions", source = "meta.conditions")
    @Mapping(target = "planning", source = "meta.planning")
    @Mapping(target = "offrePdfUrl", source = "meta.offrePdfUrl")
    DevisResponseDTO toDto(Devis devis);

    @Mapping(target = "meta", ignore = true)
    @Mapping(target = "bonCommandeClient", ignore = true)
    Devis toEntity(DevisRequestDTO devisRequestDTO);
}