package zw.co.malvern.business.create;

import zw.co.malvern.api.create.ProductionRequest;
import zw.co.malvern.domain.Egg;
import zw.co.malvern.repository.ProductionRepository;
import zw.co.malvern.utils.exceptions.ProductionException;
import zw.co.malvern.utils.response.BasicResponse;

public class ProductionServiceImpl implements ProductionService{
    private final ProductionRepository productionRepository;

    public ProductionServiceImpl(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @Override
    public BasicResponse createProductionRecord(ProductionRequest productionRequest) {
        validateString(productionRequest.getAttendant(),"attendance");
        validateString(productionRequest.getComment(),"comment");
        productionRepository.save(convertProductionRequest(productionRequest));
        return new BasicResponse("production record successfully saved",true);
    }

    private Egg convertProductionRequest(ProductionRequest productionRequest) {
        final Egg egg =  new Egg();
        egg.setComment(productionRequest.getComment());
        egg.setGood(productionRequest.getGood());
        egg.setBroken(productionRequest.getBroken());
        egg.setAttendance(productionRequest.getAttendant());
        return egg;
    }

    private void validateString(String request,String fieldName){
        if(request == null || request.isEmpty())
            throw new ProductionException(fieldName+" cannot be empty");
    }
}
