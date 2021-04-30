package zw.co.malvern.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import zw.co.malvern.business.auditing.AuditingRecordService;
import zw.co.malvern.business.create.ProductionService;
import zw.co.malvern.business.create.ProductionServiceImpl;
import zw.co.malvern.business.report.ReportService;
import zw.co.malvern.business.report.ReportServiceImpl;
import zw.co.malvern.domain.DomainMarkerInterface;
import zw.co.malvern.repository.AuditingRecordRepository;
import zw.co.malvern.repository.ProductionRepository;
import zw.co.malvern.repository.RepositoryMarkerInterface;

@Configuration
@Import(SwaggerConfig.class)
@EnableJpaRepositories(basePackageClasses = {RepositoryMarkerInterface.class})
@EntityScan(basePackageClasses = {DomainMarkerInterface.class})
public class BusinessConfiguration {

    @Bean
    public ProductionService productionService(final ProductionRepository productionRepository){
        return new ProductionServiceImpl(productionRepository);
    }

    @Bean
    public ReportService reportService(final ProductionRepository productionRepository){
        return new ReportServiceImpl(productionRepository);
    }

    @Bean
    public AuditingRecordService auditingRecordService(final AuditingRecordRepository auditingRecordRepository){
        return new AuditingRecordService(auditingRecordRepository);
    }
}
