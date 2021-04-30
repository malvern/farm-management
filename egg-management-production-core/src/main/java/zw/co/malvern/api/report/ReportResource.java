package zw.co.malvern.api.report;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.malvern.api.create.ProductionResource;
import zw.co.malvern.business.report.ReportService;
import zw.co.malvern.utils.exceptions.ProductionException;
import zw.co.malvern.utils.response.BasicResponse;
import zw.co.malvern.utils.response.ErrorBasicResponse;

@CrossOrigin
@RestController
@RequestMapping("api/production/report")
public class ReportResource {

    private final ReportService reportService;
    private static Logger LOGGER = LoggerFactory.getLogger(ProductionResource.class);


    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value = "daily/start-date/{startDate}/end-date/{endDate}",params = {"page","size"})
    @ApiOperation(value = "retrieve production records",response = ReportResponse.class)
    public ResponseEntity<ReportResponse> retrieveDailyProductionReport(@PathVariable String startDate,
                                                                        @PathVariable String endDate,
                                                                        @RequestParam(value = "page",defaultValue = "0")int page,
                                                                        @RequestParam(value = "size",defaultValue = "10")int size){
       return ResponseEntity.ok(reportService.retrieveDailyProductionReport(startDate,endDate,page,size));
    }

    @ExceptionHandler
    private ResponseEntity<BasicResponse> handleException(Exception exception){
        LOGGER.debug("error occurred ---> ",exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorBasicResponse(exception.getMessage(),
                        "Internal Error error while processing",false));
    }
}
