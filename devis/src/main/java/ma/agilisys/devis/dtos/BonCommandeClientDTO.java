package ma.agilisys.devis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonCommandeClientDTO {
    private Long id;

    @NotNull(message = "L'ID du devis est obligatoire")
    private Long devisId;

    private String devisNumero; // Pour l'affichage

    @NotBlank(message = "Le numéro fournisseur est obligatoire")
    @Size(max = 50, message = "Le numéro fournisseur ne peut pas dépasser 50 caractères")
    private String numeroFournisseur;

    @NotNull(message = "La date du bon de commande est obligatoire")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateBonCommande;

    private String fichierPdfUrl;

    @NotBlank(message = "Le statut est obligatoire")
    @Size(max = 20, message = "Le statut ne peut pas dépasser 20 caractères")
    private String statut;

    private ZonedDateTime dateReception;

    private ZonedDateTime dateValidation;

    private Long contactClientId;

    private String commentaireValidation;
}
