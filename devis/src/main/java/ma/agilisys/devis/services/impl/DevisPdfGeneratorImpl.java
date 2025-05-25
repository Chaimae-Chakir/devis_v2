package ma.agilisys.devis.services.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.agilisys.devis.models.Devis;
import ma.agilisys.devis.repositories.DevisRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class DevisPdfGeneratorImpl {

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public byte[] generateDevisPdf(Devis devis) throws Exception {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("devis_template.ftl");

        Map<String, Object> model = new HashMap<>();
        model.put("devis", devis);

        StringWriter writer = new StringWriter();
        template.process(model, writer);
        String html = writer.toString();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Error generating PDF for devis {}", devis, e);
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}