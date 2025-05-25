package ma.agilisys.devis.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactRequestDto {
    @Size(max = 120)
    private String email;

    @Size(max = 20)
    private String telephone;

    @Size(max = 20)
    private String fax;

    @NotNull
    private Long clientId;
}
