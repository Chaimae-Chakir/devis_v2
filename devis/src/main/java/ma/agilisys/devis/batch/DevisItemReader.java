package ma.agilisys.devis.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.agilisys.devis.models.Devis;
import ma.agilisys.devis.repositories.DevisRepository;
import ma.agilisys.devis.utils.Constants;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DevisItemReader implements ItemReader<Devis> {
    private final DevisRepository devisRepository;
    private Iterator<Devis> devisIterator;

    @Override
    public Devis read() {
        if (devisIterator == null || !devisIterator.hasNext()) {
            List<Devis> devisList = devisRepository.findApprovedDevisWithoutPdf(Constants.APPROVED_STATUS);
            log.info("{} devis approuvés sans PDF trouvés.", devisList.size());
            devisIterator = devisList.iterator();
            if (!devisIterator.hasNext()) {
                return null;
            }
        }
        return devisIterator.next();
    }
}