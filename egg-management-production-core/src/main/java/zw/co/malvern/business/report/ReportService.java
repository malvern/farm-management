package zw.co.malvern.business.report;

import zw.co.malvern.api.report.ReportResponse;

public interface ReportService {

    ReportResponse retrieveDailyProductionReport(String startDate, String endDate, int page, int size);
}
