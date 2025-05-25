package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT DISTINCT c FROM Client c LEFT JOIN FETCH c.contacts WHERE c.id = :id")
    Optional<Client> findByIdWithContacts(@Param("id") Long id);

    boolean existsByIce(String ice);

    @EntityGraph(attributePaths = "contacts")
    Page<Client> findAll(Pageable pageable);
}
