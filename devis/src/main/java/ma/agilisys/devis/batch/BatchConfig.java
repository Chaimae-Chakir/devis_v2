package ma.agilisys.devis.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.agilisys.devis.models.Devis;
import ma.agilisys.devis.models.DevisPdfFile;
import ma.agilisys.devis.repositories.DevisPdfFileRepository;
import ma.agilisys.devis.repositories.DevisRepository;
import ma.agilisys.devis.services.impl.DevisPdfGeneratorImpl;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {
    private final DevisPdfGeneratorImpl pdfGenerator;
    private final DevisRepository devisRepository;
    private final DevisPdfFileRepository devisPdfFileRepository;

    @Bean
    public Job generatePdfJob(JobRepository jobRepository, Step generatePdfStep) {
        return new JobBuilder("generatePdfJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(generatePdfStep)
                .end()
                .build();
    }

    @Bean
    public Step generatePdfStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager) {
        return new StepBuilder("generatePdfStep", jobRepository)
                .<Devis, Devis>chunk(10, transactionManager)
                .reader(devisReader())
                .processor(devisProcessor())
                .writer(devisWriter())
                .build();
    }

    @Bean
    public ItemReader<Devis> devisReader() {
        return new DevisItemReader(devisRepository);
    }

    @Bean
    public ItemProcessor<Devis, Devis> devisProcessor() {
        return devis -> {
            try {
                log.info("Génération du PDF pour le devis ID: {}", devis.getId());
                byte[] pdf = pdfGenerator.generateDevisPdf(devis);
                String fileName = "devis_" + devis.getNumero() + "_" +
                        ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";

                // Check if PDF was generated
                if (pdf == null || pdf.length == 0) {
                    throw new RuntimeException("Generated PDF is empty");
                }

                Optional<DevisPdfFile> existingFile = devisPdfFileRepository.findByDevis_Id(devis.getId());
                if (existingFile.isEmpty()) {
                    DevisPdfFile newFile = new DevisPdfFile();
                    newFile.setData(pdf);
                    newFile.setFileName(fileName);
                    newFile.setFileType("application/pdf");
                    newFile.setDevis(devis);
                    devisPdfFileRepository.save(newFile);
                }

                log.info("PDF enregistré avec succès pour le devis ID: {}", devis.getId());
                if (devis.getMeta() != null) {
                    devis.getMeta().setOffrePdfUrl("/api/devis/" + devis.getId() + "/pdf");
                }
                return devis;
            } catch (Exception e) {
                log.error("Erreur lors de la génération du PDF pour le devis ID: {}", devis.getId(), e);
                throw new RuntimeException("Erreur lors de la génération du PDF", e);
            }
        };
    }

    @Bean
    public ItemWriter<Devis> devisWriter() {
        return devisList -> {
            devisRepository.saveAll(devisList);
            log.info("{} devis mis à jour avec les URLs PDF.", devisList.size());
        };
    }
}