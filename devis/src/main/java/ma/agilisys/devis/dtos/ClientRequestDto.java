package ma.agilisys.devis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRequestDto {
    private Long id;
    @NotBlank
    @Size(max = 120)
    private String nom;

    @NotBlank
    @Size(max = 20)
    private String ice;

    @Size(max = 255)
    private String logoUrl;

    @Size(max = 255)
    private String adresse;

    @Size(max = 100)
    private String ville;

    @Size(max = 60)
    private String pays;
}