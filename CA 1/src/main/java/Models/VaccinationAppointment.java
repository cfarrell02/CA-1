package Models;

import java.time.LocalDate;

public class VaccinationAppointment {
    LocalDate date;
    private String  time, type, batchNum,vaccinatorDetails,patientPPSN;

    public VaccinationAppointment(LocalDate date, String time, String type, String batchNum, String vaccinatorDetails, String patientPPSN) {
        this.date = date;
        this.time = time;
        this.type = type;
        this.batchNum = batchNum;
        this.vaccinatorDetails = vaccinatorDetails;
        this.patientPPSN = patientPPSN;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getVaccinatorDetails() {
        return vaccinatorDetails;
    }

    public void setVaccinatorDetails(String vaccinatorDetails) {
        this.vaccinatorDetails = vaccinatorDetails;
    }

    public String getPatientPPSN() {
        return patientPPSN;
    }

    public void setPatientPPSN(String patientPPSN) {
        this.patientPPSN = patientPPSN;
    }

    public boolean equals(VaccinationAppointment otherAppointment){
        return this.date.equals(otherAppointment.date)&&this.time.equals(otherAppointment.time)&&
                this.type.equals(otherAppointment.type)&&this.batchNum.equals(otherAppointment.batchNum)&&
                this.vaccinatorDetails.equals(otherAppointment.vaccinatorDetails)&&
                this.patientPPSN.equals(otherAppointment.patientPPSN);
    }
    public String toString(){
        return date + "    " +
                time + "    " +
                type + "    " +
                batchNum + "    " +
//                vaccinatorDetails + "   " +
                patientPPSN + "    ";
    }
}
