package ma.agilisys.devis.mappers;

import ma.agilisys.devis.dtos.DevisLigneRequestDTO;
import ma.agilisys.devis.dtos.DevisLigneResponseDTO;
import ma.agilisys.devis.models.DevisLigne;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DevisLigneMapper {
    DevisLigneResponseDTO toDto(DevisLigne devisLigne);

    DevisLigne toEntity(DevisLigneRequestDTO devisLigneRequestDTO);
}