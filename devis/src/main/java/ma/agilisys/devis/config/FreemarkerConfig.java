package ma.agilisys.devis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FreemarkerConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/templates/");

        Map<String, Object> freemarkerVariables = new HashMap<>();
        freemarkerVariables.put("datetime_format", "dd/MM/yyyy");

        configurer.setFreemarkerVariables(freemarkerVariables);
        configurer.setDefaultEncoding("UTF-8");
        return configurer;
    }
}