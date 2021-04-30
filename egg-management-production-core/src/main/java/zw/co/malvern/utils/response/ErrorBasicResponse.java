package zw.co.malvern.utils.response;

public class ErrorBasicResponse extends BasicResponse{
    private String errorNarrative;

    public ErrorBasicResponse(String narrative, String errorNarrative,boolean success) {
        super(narrative, success);
        this.errorNarrative = errorNarrative;
    }

    public ErrorBasicResponse() {
    }

    public String getErrorNarrative() {
        return errorNarrative;
    }

    public void setErrorNarrative(String errorNarrative) {
        this.errorNarrative = errorNarrative;
    }
}
