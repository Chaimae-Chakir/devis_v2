package ma.agilisys.devis.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.agilisys.devis.config.RabbitMQConfig;
import ma.agilisys.devis.models.Devis;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ma.agilisys.devis.mappers.DevisMapper;
import ma.agilisys.devis.dtos.DevisResponseDTO;
import ma.agilisys.devis.dtos.DocumentMessageDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentMessageService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final DevisMapper devisMapper;

    public void sendDocumentMessage(Devis devis) {
        try {
            var client = devis.getClient();
            var devisList = client.getDevis();
            DocumentMessageDTO message = new DocumentMessageDTO();
            message.setClientId(client.getId().toString());
            message.setCallbackUrl("https://app/documents/callback");
            List<DocumentMessageDTO.Document> documents = new java.util.ArrayList<>();
            for (Devis d : devisList) {
                DocumentMessageDTO.Document document = new DocumentMessageDTO.Document();
                document.setTemplate("devis");
                document.setFormat("pdf");
                DevisResponseDTO devisDto = devisMapper.toDto(d);
                Map<String, Object> devisMap = objectMapper.convertValue(devisDto, Map.class);
                document.setData(devisMap);
                documents.add(document);
            }
            message.setDocuments(documents);
            log.info("Envoi du message pour le client ID: {} avec {} devis", client.getId(), documents.size());
            rabbitTemplate.convertAndSend(RabbitMQConfig.DOCUMENT_QUEUE, message);
            log.info("Message envoyé avec succès pour le client ID: {}", client.getId());
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi du message pour le client ID: {}", devis.getClient().getId(), e);
            throw new RuntimeException("Erreur lors de l'envoi du message", e);
        }
    }
} 