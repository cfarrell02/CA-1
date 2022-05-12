package Main;

import Models.Patient;
import Models.VaccinationCentre;
import Utils.CoolLinkedList;

import java.time.LocalDate;

public class Driver {
    private  CoolLinkedList<VaccinationCentre> centres;
    private  CoolLinkedList<Patient> patients;

    public Driver() {
        this.patients =  new CoolLinkedList<>();
        this.centres =  new CoolLinkedList<>();
    }


    public boolean validPPSN(String PPSN){
        for(Patient patient: patients){
            if(patient.getPPSN().equalsIgnoreCase(PPSN))
                return true;
        }
        return false;
    }


    public boolean centreNameExists(String name){
        for(VaccinationCentre centre:centres){
            if(name.equalsIgnoreCase(centre.getName()))
                return true;
        }
        return false;
    }

    public void addVaccinationCentre(String name,String address,String eircode,String parkingSpaces){
        centres.add(new VaccinationCentre(
                name, address, eircode, Integer.parseInt(parkingSpaces)));
    }
    public void deleteVaccinationCentre(int index){
        centres.remove(index);
    }
    public void editVaccinationCentre(int oldIndex, String name, String address, String eircode, String parkingSpaces){
        VaccinationCentre newCentre = new VaccinationCentre(
                name, address, eircode, Integer.parseInt(parkingSpaces));
        newCentre.setBooths(centres.get(oldIndex).getBooths());
        centres.set(oldIndex,newCentre);
    }
    public VaccinationCentre getCentre(int index){
        return centres.get(index);
    }
    public VaccinationCentre getCentre(String name){
        for(VaccinationCentre centre: centres){
            if(centre.getName().equals(name))
                return centre;
        }
        return null;
    }
    public void addPatient(String PPSN, String firstName, String secondName, LocalDate dob, String phoneNum,
                                  String email, String address,String eircode, String info){
        patients.add(new Patient(PPSN,firstName,secondName,dob,phoneNum,email,address,eircode,info));
    }

    public void editPatient(int oldIndex,Patient patient){
        patient.setRecords(patients.get(oldIndex).getRecords());
        patients.set(oldIndex,patient);
    }
    public void deletePatient(int index){
        patients.remove(index);
    }

    public CoolLinkedList<VaccinationCentre> getCentres() {
        return centres;
    }

    public void setCentres(CoolLinkedList<VaccinationCentre> centres) {
        this.centres = centres;
    }

    public CoolLinkedList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(CoolLinkedList<Patient> patients) {
        this.patients = patients;
    }
    public int centreAmount(){
        return centres.size();
    }
    public void resetSystem(){
        patients.clear();
        centres.clear();
    }
}
