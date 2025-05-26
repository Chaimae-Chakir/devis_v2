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
import ma.agilisys.devis.repositories.DevisRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentMessageService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final DevisMapper devisMapper;
    private final DevisRepository devisRepository;

    public void sendDocumentMessage(Devis devis) {
        // Récupérer tous les devis du même client
        List<Devis> allClientDevis = devisRepository.findByClientId(devis.getClient().getId());
        
        try {
            DocumentMessage message = new DocumentMessage();
            message.setClientId(devis.getClient().getId().toString());
            message.setCallbackUrl("https://app/documents/callback");
            
            List<DocumentMessage.Document> documents = new ArrayList<>();
            
            // Ajouter tous les devis du client dans la liste des documents
            for (Devis clientDevis : allClientDevis) {
                DocumentMessage.Document document = new DocumentMessage.Document();
                document.setTemplate("devis");
                document.setFormat("pdf");
                DevisResponseDTO devisDto = devisMapper.toDto(clientDevis);
                Map<String, Object> devisMap = objectMapper.convertValue(devisDto, Map.class);
                document.setData(devisMap);
                documents.add(document);
            }
            
            message.setDocuments(documents);
            
            log.info("Envoi d'un message groupé pour le client ID: {} contenant {} devis", 
                    devis.getClient().getId(), documents.size());
                    
            rabbitTemplate.convertAndSend(RabbitMQConfig.DOCUMENT_QUEUE, message);
            
            log.info("Message groupé envoyé avec succès pour le client ID: {}", 
                    devis.getClient().getId());
                    
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi du message groupé pour le client ID: {}", 
                    devis.getClient().getId(), e);
            throw new RuntimeException("Erreur lors de l'envoi du message", e);
        }
    }
} 