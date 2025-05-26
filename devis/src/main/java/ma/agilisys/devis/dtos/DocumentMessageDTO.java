package ma.agilisys.devis.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class DocumentMessageDTO {
    private String clientId;
    private List<Document> documents;
    private String callbackUrl;

    @Data
    @NoArgsConstructor
    public static class Document {
        private String template;
        private String format;
        private Map<String, Object> data;
    }
} 