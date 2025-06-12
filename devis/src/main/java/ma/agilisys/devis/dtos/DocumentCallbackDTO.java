package ma.agilisys.devis.dtos;

import lombok.Data;

@Data
public class DocumentCallbackDTO {
    private String documentId;
    private String status;
    private String documentUrl;
    private String errorMessage;
    private String clientId;
} 