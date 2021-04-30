package zw.co.malvern.utils.response;

public class BasicResponse {
    private String narrative;
    private boolean success;

    public BasicResponse(String narrative, boolean success) {
        this.narrative = narrative;
        this.success = success;
    }

    public BasicResponse() {
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
