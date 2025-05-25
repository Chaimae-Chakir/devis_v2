package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.DevisPdfFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DevisPdfFileRepository extends JpaRepository<DevisPdfFile, Long> {
    Optional<DevisPdfFile> findByDevis_Id(Long devisId);
}