package ma.agilisys.devis.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devis_pdf_files")
@Data
@NoArgsConstructor
public class DevisPdfFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @OneToOne
    @JoinColumn(name = "devis_id", unique = true)
    private Devis devis;
}