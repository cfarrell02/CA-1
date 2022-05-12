package Main;

import Models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainController {

    public static String currentTitle;
    @FXML
    public ListView<String> frontList;
    @FXML
    public TextField searchBar;
    @FXML
    public Text stats;

    public void viewVaccinationCentreMenu() throws IOException {
        currentTitle="Vaccination Centres";
        FXMLLoader fxmlLoader = new FXMLLoader(VaccineApplication.class.getResource("model-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        VaccineApplication.mainWindow.setTitle(currentTitle);
        VaccineApplication.mainWindow.setScene(scene);
        VaccineApplication.mainWindow.show();
    }
    public void viewPatientsMenu() throws IOException {
        currentTitle="Patients";
        FXMLLoader fxmlLoader = new FXMLLoader(VaccineApplication.class.getResource("model-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        VaccineApplication.mainWindow.setTitle(currentTitle);
        VaccineApplication.mainWindow.setScene(scene);
        VaccineApplication.mainWindow.show();

    }

//Populates the mainlistview with all the appointments and records in the system
    public void initialize(){
        int centreCount=0,boothCount=0,appointmentCount=0,patientCount=0,recordCount=0;
        frontList.getItems().add("--Pending Appointments--");
        for(VaccinationCentre centre: VaccineApplication.getDriver().getCentres()){
            ++centreCount;
            for(VaccinationBooth booth:centre.getBooths()){
                ++boothCount;
                for(VaccinationAppointment appointment:booth.getAppointments()) {
                    ++appointmentCount;
                    frontList.getItems().add(centre.getName() + " -> " + booth.getBoothID() + " -> " + appointment);
                }
            }
        }
        frontList.getItems().add("--Records--");
        for(Patient patient:VaccineApplication.getDriver().getPatients()){
            ++patientCount;
            for(VaccinationRecord record:patient.getRecords()) {
                ++recordCount;
                frontList.getItems().add(record + "   :" + patient.getPPSN() + "   " + patient.getFirstName() + " " + patient.getSecondName());
            }
        }
        stats.setText("Centres: "+centreCount+" Booths: "+boothCount+" Appointments: "+appointmentCount+
                " Patients: "+patientCount+" Records: "+recordCount);
    }
    //Linearly searches all appointments and records to allow for the user to search them
    public void search() {
        frontList.getItems().clear();
        String query = searchBar.getText().toLowerCase().strip();
        frontList.getItems().add("--Pending Appointments--");
        for (VaccinationCentre centre : VaccineApplication.getDriver().getCentres()) {
            for (VaccinationBooth booth : centre.getBooths()) {
                for (VaccinationAppointment appointment : booth.getAppointments()) {
                    if (appointment.getBatchNum().toLowerCase().contains(query)||appointment.getType().toLowerCase().contains(query))
                        frontList.getItems().add(centre.getName()+" -> "+booth.getBoothID()+" -> "+appointment);
                }
            }
        }
        frontList.getItems().add("--Records--");
        for(Patient patient:VaccineApplication.getDriver().getPatients()){
            for(VaccinationRecord record:patient.getRecords()) {
                if(record.getBatchNum().toLowerCase().contains(query)||record.getType().toLowerCase().contains(query)
                ||patient.getFirstName().toLowerCase().contains(query)||patient.getSecondName().toLowerCase().contains(query)
                ||patient.getPPSN().toLowerCase().contains(query))
                frontList.getItems().add(record+"   :"+patient.getPPSN()+"   "+patient.getFirstName()+" "+patient.getSecondName());
            }
        }
    }

    public static String getCurrentTitle() {
        return currentTitle;
    }

    public static void setCurrentTitle(String currentTitle) {
        MainController.currentTitle = currentTitle;
    }
    public void load() throws Exception {
        VaccineApplication.load();
        frontList.getItems().clear();
        initialize();
    }
    public void save() throws Exception {
        VaccineApplication.save();
        System.out.println("Saved!");
    }
    //Resets the system
    public void resetSystem(){
        VaccineApplication.getDriver().resetSystem();
        stats.setText("Centres: 0"+" Booths: 0"+" Appointments: 0"+
                " Patients: 0"+" Records: 0");
        frontList.getItems().clear();
    }

}