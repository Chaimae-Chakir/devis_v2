package ma.agilisys.devis.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.agilisys.devis.config.RabbitMQConfig;
import ma.agilisys.devis.models.Devis;
import ma.agilisys.devis.models.DocumentMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ma.agilisys.devis.mappers.DevisMapper;
import ma.agilisys.devis.dtos.DevisResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
            DocumentMessage message = new DocumentMessage();
            message.setClientId(devis.getClient().getId().toString());
            message.setCallbackUrl("https://app/documents/callback");
            DocumentMessage.Document document = new DocumentMessage.Document();
            document.setTemplate("devis");
            document.setFormat("pdf");
            DevisResponseDTO devisDto = devisMapper.toDto(devis);
            Map<String, Object> devisMap = objectMapper.convertValue(devisDto, Map.class);
            document.setData(devisMap);
            message.setDocuments(Collections.singletonList(document));
            log.info("Envoi du message pour le devis ID: {}", devis.getId());
            rabbitTemplate.convertAndSend(RabbitMQConfig.DOCUMENT_QUEUE, message);
            log.info("Message envoyé avec succès pour le devis ID: {}", devis.getId());
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi du message pour le devis ID: {}", devis.getId(), e);
            throw new RuntimeException("Erreur lors de l'envoi du message", e);
        }
    }
} 