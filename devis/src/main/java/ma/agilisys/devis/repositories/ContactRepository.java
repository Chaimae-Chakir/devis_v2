package ma.agilisys.devis.repositories;

import ma.agilisys.devis.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Set<Contact> findByClientId(Long clientId);
}
