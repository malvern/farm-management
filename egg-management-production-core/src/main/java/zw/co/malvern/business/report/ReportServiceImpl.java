package zw.co.malvern.business.report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import zw.co.malvern.api.report.EggDto;
import zw.co.malvern.api.report.ReportResponse;
import zw.co.malvern.domain.Egg;
import zw.co.malvern.repository.ProductionRepository;
import zw.co.malvern.utils.dates.FormatDateTime;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.stream.Collectors;

public class ReportServiceImpl implements ReportService {
    private final ProductionRepository productionRepository;

    public ReportServiceImpl(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @Override
    public ReportResponse retrieveDailyProductionReport(String startDate, String endDate, int page, int size) {
        final Pageable pageable = PageRequest.of(page, size);
         final Page<Egg> productions = productionRepository
                .findAllByRecordCreationTimeBetween(startDate, endDate, pageable);
        return buildResponse(productions);
    }

    private LocalDateTime formatDateTime(String startDate) {
        FormatDateTime formatDateTime = (date) -> LocalDateTime.now();
        return formatDateTime.formatStringToLocalDateTime(startDate);
    }

    private ReportResponse buildResponse(Page<Egg> productions) {
        if(!productions.hasContent()){
            return new ReportResponse("no production records available",false,
                    productions.getTotalPages(),
                    productions.getNumber(), Collections.emptyList());
        }
        return new ReportResponse("production records",true, productions.getTotalPages(),
                productions.getNumber(), productions.get().map(this::convertEggToEggDto).collect(Collectors.toList()));
    }

    private EggDto convertEggToEggDto(Egg egg) {
        final EggDto eggDto = new EggDto();
        eggDto.setBad(egg.getBroken());
        eggDto.setComment(egg.getComment());
        eggDto.setProductionDate(egg.getRecordCreationTime());
        eggDto.setAttendance(egg.getAttendance());
        eggDto.setGood(egg.getGood());
        return eggDto;
    }

}
