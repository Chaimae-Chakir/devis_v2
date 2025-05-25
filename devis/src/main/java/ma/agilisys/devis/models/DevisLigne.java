package ma.agilisys.devis.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "devis_ligne")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"devis"})
public class DevisLigne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_devis", nullable = false)
    @JsonBackReference("devis-lignes")
    private Devis devis;

    @Column(name = "description_libre", length = 255)
    private String descriptionLibre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantite;

    @Column(name = "prix_unitaire_ht", nullable = false, precision = 12, scale = 2)
    private BigDecimal prixUnitaireHt;

    @Column(name = "tva_pct", precision = 4, scale = 2)
    private BigDecimal tvaPct = BigDecimal.valueOf(20.0);

    @Column(name = "ristourne_pct", precision = 4, scale = 2)
    private BigDecimal ristournePct = BigDecimal.valueOf(20.0);
}