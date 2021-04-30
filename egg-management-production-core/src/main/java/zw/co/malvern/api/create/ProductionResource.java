package zw.co.malvern.api.create;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.malvern.business.create.ProductionService;
import zw.co.malvern.utils.exceptions.ProductionException;
import zw.co.malvern.utils.response.BasicResponse;
import zw.co.malvern.utils.response.ErrorBasicResponse;

@CrossOrigin
@RestController
@RequestMapping("api/production")
public class ProductionResource {

    private final ProductionService productionService;
    private static Logger LOGGER = LoggerFactory.getLogger(ProductionResource.class);
    public ProductionResource(ProductionService productionService) {
        this.productionService = productionService;
    }

    @PostMapping("create")
    @ApiOperation(value = "create/submit production record",response = BasicResponse.class)
    public ResponseEntity<BasicResponse> createProductionRecord(@RequestBody ProductionRequest productionRequest){
     return ResponseEntity.ok(productionService.createProductionRecord(productionRequest));
    }

    @ExceptionHandler
    private ResponseEntity<BasicResponse> handleException(Exception exception){
        LOGGER.debug("error occurred ---> ",exception);
        if(exception instanceof ProductionException){
            return ResponseEntity.badRequest().body(new BasicResponse(exception.getMessage(),false));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorBasicResponse("Failed to create request",
                "Internal Error error while processing",false));
    }
}
