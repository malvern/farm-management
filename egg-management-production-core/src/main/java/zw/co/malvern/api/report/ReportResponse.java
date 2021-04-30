package zw.co.malvern.api.report;

import zw.co.malvern.utils.response.BasicResponse;

import java.util.List;

public class ReportResponse extends BasicResponse {
    private int totalPages;
    private int page;
    private List<EggDto> eggDtoList;

    public ReportResponse() {
    }

    public ReportResponse(String narrative, boolean success, int totalPages, int page, List<EggDto> eggDtoList) {
        super(narrative, success);
        this.totalPages = totalPages;
        this.page = page;
        this.eggDtoList = eggDtoList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<EggDto> getEggDtoList() {
        return eggDtoList;
    }

    public void setEggDtoList(List<EggDto> eggDtoList) {
        this.eggDtoList = eggDtoList;
    }
}
