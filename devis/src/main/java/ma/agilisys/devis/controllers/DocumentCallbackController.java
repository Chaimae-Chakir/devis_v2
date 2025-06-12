package ma.agilisys.devis.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.agilisys.devis.dtos.DocumentCallbackDTO;
import ma.agilisys.devis.services.DocumentCallbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Slf4j
public class DocumentCallbackController {
    
    private final DocumentCallbackService documentCallbackService;

    @PostMapping("/callback")
    public ResponseEntity<Void> handleDocumentCallback(@RequestBody DocumentCallbackDTO callback) {
        log.info("Réception du callback pour le document ID: {} avec le statut: {}", 
                callback.getDocumentId(), callback.getStatus());
        
        documentCallbackService.processCallback(callback);
        
        return ResponseEntity.ok().build();
    }
} 