package ma.agilisys.devis.dtos;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Set;

@Data
public class ClientResponseDto {
    private Long id;
    private String nom;
    private String ice;
    private String logoUrl;
    private String adresse;
    private String ville;
    private String pays;
    private ZonedDateTime dateCreation;
    private Set<ContactResponseDto> contacts;
}