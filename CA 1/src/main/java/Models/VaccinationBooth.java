package Models;

import Utils.Utilities;
import Utils.CoolLinkedList;

import java.time.LocalDate;

public class VaccinationBooth {
    String boothID,accessibilityInfo;
    int floorLevel;
    public CoolLinkedList<VaccinationAppointment> appointments;

    public VaccinationBooth(String boothID, String accessibilityInfo, int floorLevel) {
        if(Utilities.validBoothID(boothID))
            this.boothID = boothID;
        else
            this.boothID = "N/A";
        this.accessibilityInfo = accessibilityInfo;
        this.floorLevel = floorLevel;
        this.appointments =  new CoolLinkedList<>();
    }

    public String getBoothID() {
        return boothID;
    }

    public void setBoothID(String boothID) {
        if(Utilities.validBoothID(boothID))
            this.boothID = boothID;
    }

    public CoolLinkedList<VaccinationAppointment> getAppointments() {
        return appointments;
    }



    public void setAppointments(CoolLinkedList<VaccinationAppointment> appointments) {
        this.appointments = appointments;
    }

    public String getAccessibilityInfo() {
        return accessibilityInfo;
    }

    public void setAccessibilityInfo(String accessibilityInfo) {
        this.accessibilityInfo = accessibilityInfo;
    }

    public int getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(int floorLevel) {
        this.floorLevel = floorLevel;
    }
    public void addAppointment(LocalDate date, String time, String type, String batchNum, String vaccinatorDetails, String patientPPSN){
        appointments.add(new VaccinationAppointment(date,time,type,batchNum,vaccinatorDetails,patientPPSN));
    }
    public boolean deleteAppointment(int index){
        if(Utilities.validIndex(index,appointments)) {
            appointments.remove(index);
            return true;
        }else
            return false;

    }
    public boolean alreadyBooked(LocalDate date,String time){
        for(VaccinationAppointment appointment:appointments){
            if(appointment.getDate().equals(date)&&appointment.getTime().equals(time))
                return true;
        }
        return false;
    }
    public VaccinationAppointment getAppointment(int index){
        return (VaccinationAppointment) appointments.get(index);
    }
    public void editAppointment(int oldIndex, VaccinationAppointment newAppointment){
        appointments.set(oldIndex,newAppointment);
    }
    public boolean equals(VaccinationBooth otherBooth){
        return this.boothID.equals(otherBooth.boothID);
    }

    public void clearAppointments(){
        appointments.clear();
    }

    public int appointmentAmount(){
        return appointments.size();
    }

    public String toString(){
        return boothID +
            //    "      " +accessibilityInfo+
                "      "+floorLevel;
    }

}
