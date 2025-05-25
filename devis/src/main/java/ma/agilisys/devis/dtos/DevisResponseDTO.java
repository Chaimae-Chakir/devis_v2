package ma.agilisys.devis.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisResponseDTO {
    private Long id;

    private String numero; // Généré automatiquement

    @NotNull(message = "Le client est obligatoire")
    private ClientResponseDto client;

    @NotBlank(message = "Le statut est obligatoire")
    @Size(max = 20, message = "Le statut ne peut pas dépasser 20 caractères")
    private String statut;

    private ZonedDateTime dateCreation;

    private ZonedDateTime dateValidation;

    private BigDecimal totalHt;

    @Valid
    private List<DevisLigneResponseDTO> lignes = new ArrayList<>();

    // DevisMeta
    private String perimetre;

    private String offreFonctionnelle;

    private String offreTechnique;

    private String conditions;

    private String planning;

    private String offrePdfUrl;
}
