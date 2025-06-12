package ma.agilisys.devis.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.agilisys.devis.dtos.DocumentCallbackDTO;
import ma.agilisys.devis.models.Devis;
import ma.agilisys.devis.repositories.DevisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentCallbackService {

    private final DevisRepository devisRepository;

    @Transactional
    public void processCallback(DocumentCallbackDTO callback) {
        try {
            if ("SUCCESS".equals(callback.getStatus())) {
                Devis devis = devisRepository.findById(Long.parseLong(callback.getDocumentId()))
                        .orElseThrow(() -> new RuntimeException("Devis non trouvé: " + callback.getDocumentId()));
                
                devis.setDocumentUrl(callback.getDocumentUrl());
                devis.setStatut("DOCUMENT_GENERATED");
                devisRepository.save(devis);
                
                log.info("Document généré avec succès pour le devis ID: {}", callback.getDocumentId());
            } else {
                log.error("Erreur lors de la génération du document pour le devis ID: {}. Erreur: {}", 
                        callback.getDocumentId(), callback.getErrorMessage());
                Devis devis = devisRepository.findById(Long.parseLong(callback.getDocumentId()))
                        .orElseThrow(() -> new RuntimeException("Devis non trouvé: " + callback.getDocumentId()));
                devis.setStatut("DOCUMENT_GENERATION_FAILED");
                devisRepository.save(devis);
            }
        } catch (Exception e) {
            log.error("Erreur lors du traitement du callback pour le document ID: {}", 
                    callback.getDocumentId(), e);
            throw new RuntimeException("Erreur lors du traitement du callback", e);
        }
    }
} 