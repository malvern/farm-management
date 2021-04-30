package zw.co.malvern.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Egg {
    @Id
    @GeneratedValue
    private Long id;

    private Integer good;
    private Integer broken;
    private String comment;
    private String attendance;
    private String recordCreationTime;
    private String recordUpdateTime;

    @PrePersist
    private void initialiseRecordParameters(){
        if(recordCreationTime == null)
            recordCreationTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }


    @PreUpdate
    private void updateRecordParameters(){
        if(recordCreationTime == null)
            recordCreationTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

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

    public String getRecordCreationTime() {
        return recordCreationTime;
    }

    public void setRecordCreationTime(String recordCreationTime) {
        this.recordCreationTime = recordCreationTime;
    }

    public String getRecordUpdateTime() {
        return recordUpdateTime;
    }

    public void setRecordUpdateTime(String recordUpdateTime) {
        this.recordUpdateTime = recordUpdateTime;
    }
}
