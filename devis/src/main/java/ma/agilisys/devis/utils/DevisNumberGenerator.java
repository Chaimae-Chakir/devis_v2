package ma.agilisys.devis.utils;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DevisNumberGenerator {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmssSSS");

    public String generateUniqueNumber() {
        String timestamp = ZonedDateTime.now().format(FORMATTER);
        return "DEV-" + timestamp;
    }
}
