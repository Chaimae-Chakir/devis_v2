package ma.agilisys.devis.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DevisPageDto {
    List<DevisResponseDTO> devis;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private long totalElements;
}
