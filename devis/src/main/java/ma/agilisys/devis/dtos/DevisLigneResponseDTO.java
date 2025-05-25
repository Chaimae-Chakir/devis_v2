package ma.agilisys.devis.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevisLigneResponseDTO {
    private Long id;

    @Size(max = 255, message = "La description ne peut pas dépasser 255 caractères")
    private String descriptionLibre;

    @NotNull(message = "La quantité est obligatoire")
    @DecimalMin(value = "0.01", message = "La quantité doit être supérieure à 0")
    private BigDecimal quantite;

    @NotNull(message = "Le prix unitaire HT est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix unitaire HT doit être supérieur à 0")
    private BigDecimal prixUnitaireHt;

    private BigDecimal tvaPct = BigDecimal.valueOf(20.0);

    private BigDecimal ristournePct = BigDecimal.valueOf(20.0);
}