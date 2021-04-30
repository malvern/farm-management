package zw.co.malvern.business.auditing;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import zw.co.malvern.api.create.ProductionRequest;
import zw.co.malvern.domain.RecordAuditing;
import zw.co.malvern.repository.AuditingRecordRepository;
import zw.co.malvern.utils.response.BasicResponse;

@Aspect
public class AuditingRecordService {

    private final AuditingRecordRepository auditingRecordRepository;

    public AuditingRecordService(AuditingRecordRepository auditingRecordRepository) {
        this.auditingRecordRepository = auditingRecordRepository;
    }

    @Pointcut("execution(* zw.co.malvern.business.create.ProductionServiceImpl.createProductionRecord(..))")
    public void auditRecordCreation() {
    }


    @AfterReturning(value = "auditRecordCreation()&&args(productionRequest)", returning = "response")
    public void auditRecord(ProductionRequest productionRequest, BasicResponse response) {
        populateRecordAuditing(productionRequest, response,
                "normal");
    }


    @AfterThrowing(value = "auditRecordCreation()&&args(productionRequest)", throwing = "exception")
    public void ecocashAuditingException(ProductionRequest productionRequest, Exception exception) {
        final BasicResponse response = new BasicResponse(exception.getMessage(),false);
        populateRecordAuditing(productionRequest, response, "exception thrown");
    }

    private void populateRecordAuditing(ProductionRequest productionRequest, BasicResponse response,
                                        String transactionSummary) {
        final RecordAuditing recordAuditing = new RecordAuditing();
        recordAuditing.setAttendance(productionRequest.getAttendant());
        recordAuditing.setBroken(productionRequest.getBroken());
        recordAuditing.setGood(productionRequest.getGood());
        recordAuditing.setComment(productionRequest.getComment());
        recordAuditing.setComment(productionRequest.getComment());
        recordAuditing.setSuccess(String.valueOf(response.isSuccess()));
        recordAuditing.setDescription(response.getNarrative());
        recordAuditing.setTransactionSummary(transactionSummary);
        auditingRecordRepository.save(recordAuditing);
    }


}
