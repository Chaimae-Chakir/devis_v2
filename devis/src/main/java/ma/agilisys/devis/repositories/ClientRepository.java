package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByIce(String ice);
    
    Optional<Client> findByIce(String ice);
    
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.contacts WHERE c.id = :id")
    Optional<Client> findByIdWithContacts(Long id);

    @EntityGraph(attributePaths = "contacts")
    Page<Client> findAll(Pageable pageable);
}
