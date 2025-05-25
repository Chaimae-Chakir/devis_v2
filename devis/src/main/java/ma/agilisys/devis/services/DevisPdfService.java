package ma.agilisys.devis.services;

import ma.agilisys.devis.models.DevisPdfFile;

public interface DevisPdfService {
    DevisPdfFile getDevisPdfFile(Long devisId);
}
