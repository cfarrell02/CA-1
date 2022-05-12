package Models;

import Utils.CoolLinkedList;
import Utils.Utilities;

import java.time.LocalDate;

public class Patient {
    private LocalDate dateOfBirth;
    private String PPSN,firstName, secondName,phoneNum,email,address,eircode, accessibilityInfo;
    private CoolLinkedList<VaccinationRecord> records;

    public Patient(String PPSN, String firstName, String secondName, LocalDate dateOfBirth, String phoneNum,
                   String email, String address,String eircode, String accessibilityInfo) {
        this.PPSN = PPSN;
        this.firstName = firstName;
        this.secondName = secondName;
        this.dateOfBirth = dateOfBirth;
        if(Utilities.max10Chars(phoneNum))
            this.phoneNum = phoneNum;
        else
            this.phoneNum = phoneNum.substring(0,10);
        this.email = email;
        this.address = address;
        this.eircode = eircode;
        this.accessibilityInfo = accessibilityInfo;
        this.records = new CoolLinkedList<>();
    }

    public CoolLinkedList<VaccinationRecord> getRecords() {
        return records;
    }

    public VaccinationRecord getRecord(int index){
        return records.get(index);
    }

    public void deleteRecord(int index){
        records.remove(index);
    }
    public void editRecord(int oldIndex,VaccinationRecord newRecord){
        records.set(oldIndex,newRecord);
    }
    public void addRecord(LocalDate date,String type,String num){
        records.add(new VaccinationRecord(date,type,num));
    }
    public void setRecords(CoolLinkedList<VaccinationRecord> records) {
        this.records = records;
    }

    public String getPPSN() {
        return PPSN;
    }

    public void setPPSN(String PPSN) {
        this.PPSN = PPSN;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEircode() {
        return eircode;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        if(Utilities.max10Chars(phoneNum))
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccessibilityInfo() {
        return accessibilityInfo;
    }

    public void clearRecords(){
        records.clear();
    }

    public int recordAmount(){
        return records.size();
    }

    public void setAccessibilityInfo(String accessibilityInfo) {
        this.accessibilityInfo = accessibilityInfo;
    }

    public boolean equals(Patient otherpatient){
        return this.PPSN.equals(otherpatient.PPSN);
    }

    @Override
    public String toString() {
        return
                firstName +" "+ secondName +
                "       " + PPSN +
                "       " + dateOfBirth;
//                "       " + phoneNum +
//                "       " + email +
//                "       " + address +
//                "       " + accessibilityInfo
//                ;
    }
}
