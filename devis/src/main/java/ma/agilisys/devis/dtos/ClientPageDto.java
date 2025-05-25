package ma.agilisys.devis.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientPageDto {
    List<ClientResponseDto> clients;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private long totalElements;
}
