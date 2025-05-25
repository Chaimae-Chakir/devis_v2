package ma.agilisys.devis.dtos;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ContactResponseDto {
    private Long id;
    private String email;
    private String telephone;
    private String fax;
    private ZonedDateTime dateCreation;
}