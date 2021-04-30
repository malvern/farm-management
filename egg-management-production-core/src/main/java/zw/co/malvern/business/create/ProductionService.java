package zw.co.malvern.business.create;

import zw.co.malvern.api.create.ProductionRequest;
import zw.co.malvern.utils.response.BasicResponse;

@FunctionalInterface
public interface ProductionService {

    BasicResponse createProductionRecord(ProductionRequest productionRequest);
}
