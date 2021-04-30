package zw.co.malvern.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class RecordAuditing {
    @Id
    @GeneratedValue
    private Long id;

    private Integer good;
    private Integer broken;
    private String comment;
    private String attendance;
    private String narrative;
    private String success;
    private String description;
    private String transactionSummary;
    private LocalDateTime creationDate;

    public Long getId() {
        return id;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public Integer getBroken() {
        return broken;
    }

    public void setBroken(Integer broken) {
        this.broken = broken;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }


    public String getNarrative() {
        return narrative;
    }


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionSummary() {
        return transactionSummary;
    }

    public void setTransactionSummary(String transactionSummary) {
        this.transactionSummary = transactionSummary;
    }
}
