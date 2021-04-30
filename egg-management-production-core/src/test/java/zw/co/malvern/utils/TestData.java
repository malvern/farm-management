package zw.co.malvern.utils;

import zw.co.malvern.api.create.ProductionRequest;
import zw.co.malvern.api.report.EggDto;
import zw.co.malvern.domain.Egg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestData {
    public final static String startDate = "2021-04-01T00:00:00";
    public final static  String endDate = "2021-04-01T23:59:59";

    public static ProductionRequest createSampleProductionRequest(){
        final ProductionRequest productionRequest = new ProductionRequest();
        productionRequest.setAttendant("malvin");
        productionRequest.setBroken(10);
        productionRequest.setGood(30);
        productionRequest.setComment("Eggs were broken during the handling process");
        return productionRequest;
    }

    public static Egg savedEgg(){
        final Egg egg = new Egg();
        egg.setAttendance("malvin");
        egg.setBroken(10);
        egg.setGood(30);
        egg.setComment("Eggs were broken during the handling process");
        egg.setRecordCreationTime(LocalDateTime.of(2021,4,28,10,0,0).toString());
        return egg;
    }

    public static EggDto getEggDto(){
        final EggDto eggDto = new EggDto();
        eggDto.setAttendance("malvin");
        eggDto.setProductionDate(LocalDateTime.of(2021,4,28,10,0,0).toString());
        eggDto.setBad(10);
        eggDto.setGood(30);
        eggDto.setComment("Eggs were broken during the handling process");
        return eggDto;
    }
}
