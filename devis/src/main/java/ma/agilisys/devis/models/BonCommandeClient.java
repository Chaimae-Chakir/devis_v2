package ma.agilisys.devis.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "bon_commande_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonCommandeClient {
    @Column(name = "date_reception")
    private final ZonedDateTime dateReception = ZonedDateTime.now();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_devis", unique = true, nullable = false)
    private Devis devis;
    @Column(name = "numero_fournisseur", nullable = false, length = 50)
    private String numeroFournisseur;
    @Column(name = "date_bon_commande", nullable = false)
    private ZonedDateTime dateBonCommande;
    @Column(name = "fichier_pdf_url", length = 255)
    private String fichierPdfUrl; // scan/pdf du PO
    @Column(nullable = false, length = 20)
    private String statut; // RECU | VERIFIE | ACCEPTE | REJETE
    @Column(name = "date_validation")
    private ZonedDateTime dateValidation;

    @ManyToOne
    @JoinColumn(name = "id_contact_client")
    private Contact contactClient;

    @Column(name = "commentaire_validation", columnDefinition = "TEXT")
    private String commentaireValidation;
}