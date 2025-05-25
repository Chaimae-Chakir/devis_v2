package ma.agilisys.devis.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.agilisys.devis.dtos.DevisPageDto;
import ma.agilisys.devis.dtos.DevisRequestDTO;
import ma.agilisys.devis.dtos.DevisResponseDTO;
import ma.agilisys.devis.models.DevisPdfFile;
import ma.agilisys.devis.services.DevisPdfService;
import ma.agilisys.devis.services.DevisService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/devis")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.POST})
public class DevisController {
    private final DevisService devisService;
    private final DevisPdfService devisPdfService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DevisPageDto> getAllDevis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(devisService.getAllDevis(page, size));
    }

    @GetMapping("/client/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DevisPageDto> getAllDevisByClientId(@PathVariable Long id,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(devisService.getAllDevisByClientId(id, page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<DevisResponseDTO> getDevisById(@PathVariable Long id) {
        return ResponseEntity.ok(devisService.getDevisById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<DevisResponseDTO> createDevis(@Valid @RequestBody DevisRequestDTO devisRequestDTO) {
        return ResponseEntity.ok(devisService.createDevis(devisRequestDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<DevisResponseDTO> updateDevis(@PathVariable Long id,
                                                        @Valid @RequestBody DevisRequestDTO devisRequestDTO) {
        return ResponseEntity.ok(devisService.updateDevis(id, devisRequestDTO));
    }

    @PutMapping("/{id}/validate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DevisResponseDTO> validateDevis(@PathVariable Long id,
                                                          @RequestParam String validatedBy) {
        return ResponseEntity.ok(devisService.validateDevis(id, validatedBy));
    }

    @PostMapping("/{id}/duplicate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<DevisResponseDTO> duplicateDevis(@PathVariable Long id) {
        return ResponseEntity.ok(devisService.duplicateDevis(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDevis(@PathVariable Long id) {
        devisService.deleteDevis(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/pdf")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<byte[]> downloadDevisPdf(@PathVariable Long id) {
        DevisPdfFile pdfFile = devisPdfService.getDevisPdfFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + pdfFile.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfFile.getData());
    }
}