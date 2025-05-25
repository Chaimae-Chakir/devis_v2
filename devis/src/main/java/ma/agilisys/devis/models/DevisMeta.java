package ma.agilisys.devis.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "devis_meta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"devis"})
public class DevisMeta {
    @Id
    private Long idDevis;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_devis")
    @JsonBackReference("devis-meta")
    private Devis devis;

    @Column(columnDefinition = "TEXT")
    private String perimetre;

    @Column(name = "offre_fonctionnelle", columnDefinition = "TEXT")
    private String offreFonctionnelle;

    @Column(name = "offre_technique", columnDefinition = "TEXT")
    private String offreTechnique;

    @Column(columnDefinition = "TEXT")
    private String conditions;

    @Column(columnDefinition = "TEXT")
    private String planning;

    @Column(name = "offre_pdf_url", length = 255)
    private String offrePdfUrl;// URL devis généré
}