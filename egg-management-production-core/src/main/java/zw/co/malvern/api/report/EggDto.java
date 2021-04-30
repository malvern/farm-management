package zw.co.malvern.api.report;

import java.util.Objects;

public class EggDto {
    private String comment;
    private int good;
    private int bad;
    private String productionDate;
    private String attendance;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "EggDto{" +
                "comment='" + comment + '\'' +
                ", good=" + good +
                ", bad=" + bad +
                ", productionDate='" + productionDate + '\'' +
                ", attendance='" + attendance + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EggDto eggDto = (EggDto) o;
        return good == eggDto.good && bad == eggDto.bad && Objects.equals(comment, eggDto.comment) && Objects.equals(productionDate, eggDto.productionDate) && Objects.equals(attendance, eggDto.attendance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment, good, bad, productionDate, attendance);
    }
}
