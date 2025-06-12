package ma.agilisys.devis.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.agilisys.devis.config.RabbitMQConfig;
import ma.agilisys.devis.models.Devis;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ma.agilisys.devis.mappers.DevisMapper;
import ma.agilisys.devis.dtos.DevisResponseDTO;
import ma.agilisys.devis.dtos.DocumentMessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentMessageService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final DevisMapper devisMapper;

    @CircuitBreaker(name = "documentGeneration", fallbackMethod = "sendDocumentMessageFallback")
    public void sendDocumentMessage(Devis devis) {
        try {
            var client = devis.getClient();
            DocumentMessageDTO message = new DocumentMessageDTO();
            message.setClientId(client.getId().toString());
            message.setCallbackUrl("http://localhost:8080/api/documents/callback");
            message.setDocuments(buildDocuments(client.getDevis()));
            
            rabbitTemplate.convertAndSend(RabbitMQConfig.DOCUMENT_QUEUE, message);
            log.info("Message envoyé avec succès pour le client ID: {}", client.getId());
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi du message pour le client ID: {}", devis.getClient().getId(), e);
            throw new RuntimeException("Erreur lors de l'envoi du message", e);
        }
    }

    public void sendDocumentMessageFallback(Devis devis, Exception e) {
        log.error("Fallback: Impossible d'envoyer le message pour le devis ID: {}. Erreur: {}", devis.getId(), e.getMessage());
        devis.setStatut("DOCUMENT_GENERATION_FAILED");
    }

    private List<DocumentMessageDTO.Document> buildDocuments(List<Devis> devisList) {
        return devisList.stream().map(d -> {
            DevisResponseDTO dto = devisMapper.toDto(d);
            Map<String, Object> data = objectMapper.convertValue(dto, Map.class);
            DocumentMessageDTO.Document doc = new DocumentMessageDTO.Document();
            doc.setTemplate("devis");
            doc.setFormat("pdf");
            doc.setData(data);
            return doc;
        }).collect(Collectors.toList());
    }
} 