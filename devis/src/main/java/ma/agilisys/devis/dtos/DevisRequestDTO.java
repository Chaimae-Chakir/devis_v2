package ma.agilisys.devis.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisRequestDTO {
    @NotNull(message = "Le client est obligatoire")
    private ClientRequestDto client;

    @Valid
    private List<DevisLigneRequestDTO> lignes = new ArrayList<>();

    // DevisMeta
    private String perimetre;

    private String offreFonctionnelle;

    private String offreTechnique;

    private String conditions;

    private String planning;

    private String offrePdfUrl;
}
