package ma.agilisys.devis.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "devis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"client", "meta", "bonCommandeClient", "lignes"})
public class Devis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 30)
    private String numero;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @Column(nullable = false, length = 20)
    private String statut;

    @Column(name = "date_creation")
    private ZonedDateTime dateCreation;

    @Column(name = "date_validation")
    private ZonedDateTime dateValidation;

    @Column(name = "total_ht", precision = 14, scale = 2)
    private BigDecimal totalHt = BigDecimal.ZERO;

    @Column(name = "total_ttc", precision = 14, scale = 2)
    private BigDecimal totalTtc = BigDecimal.ZERO;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Column(name = "validated_by", length = 100)
    private String validatedBy;

    @OneToMany(mappedBy = "devis", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("devis-lignes")
    private List<DevisLigne> lignes = new ArrayList<>();

    @OneToOne(mappedBy = "devis", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("devis-meta")
    private DevisMeta meta;

    @OneToOne(mappedBy = "devis")
    private BonCommandeClient bonCommandeClient;

    @OneToOne(mappedBy = "devis", cascade = CascadeType.ALL, orphanRemoval = true)
    private DevisPdfFile devisPdfFile;

    @PrePersist
    public void prePersist() {
        if (dateCreation == null) {
            dateCreation = ZonedDateTime.now();
        }
        calculerTotals();
    }

    @PreUpdate
    public void preUpdate() {
        calculerTotals();
    }

    public void calculerTotals() {
        this.totalHt = lignes.stream()
                .map(this::calculateLineHt)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalTtc = lignes.stream()
                .map(ligne -> {
                    BigDecimal prixHt = calculateLineHt(ligne);
                    return ligne.getTvaPct() != null && ligne.getTvaPct().compareTo(BigDecimal.ZERO) > 0
                            ? prixHt.add(prixHt.multiply(ligne.getTvaPct()).divide(BigDecimal.valueOf(100)))
                            : prixHt;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateLineHt(DevisLigne ligne) {
        BigDecimal prixBase = ligne.getPrixUnitaireHt().multiply(ligne.getQuantite());
        return ligne.getRistournePct() != null && ligne.getRistournePct().compareTo(BigDecimal.ZERO) > 0
                ? prixBase.subtract(prixBase.multiply(ligne.getRistournePct()).divide(BigDecimal.valueOf(100)))
                : prixBase;
    }
}