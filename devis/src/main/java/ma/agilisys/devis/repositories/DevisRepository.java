package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.Devis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {
    @Query("SELECT d FROM Devis d WHERE d.statut = :statut AND NOT EXISTS (SELECT p FROM DevisPdfFile p WHERE p.devis = d)")
    List<Devis> findApprovedDevisWithoutPdf(String statut);

    List<Devis> findByClientId(Long id);

    Page<Devis> findAllByClient_Id(Long id, PageRequest of);
}