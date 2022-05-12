package Models;

import java.time.LocalDate;

public class VaccinationRecord {
    private LocalDate date;
private String type,batchNum;

    public VaccinationRecord(LocalDate date, String type, String batchNum) {
        this.date = date;
        this.type = type;
        this.batchNum = batchNum;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String toString(){
        return date+"   "+
                type +"    "
              +  batchNum;
    }
}
