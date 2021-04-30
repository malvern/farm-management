package zw.co.malvern.utils;

public class TestApi {

    public static String productionUrl = "http://localhost:9099/api/production/create";
    public static String reportingUrl = "http://localhost:9099/api/production/report/daily/start-date/{startDate}/end-date/{endDate}";
    public static String productionControllerUrl = "/api/production/create";
    public static String reportControllerUrl = "/api/production/report/daily/start-date/{startDate}/end-date/{endDate}";
}
