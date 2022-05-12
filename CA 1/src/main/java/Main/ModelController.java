package Main;

import Models.*;
import Utils.AlertBox;
import Utils.CoolLinkedList;
import Utils.Utilities;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;

public class ModelController {
    @FXML
    private Text modelTitle;
    @FXML
    private ListView<String> mainList;
    @FXML
    private TextField firstName,lastName,phoneNum,email,ppsn,patientEircode,centreName,eircode,centreParkingSpaces,boothID,boothLevel,
            appointmentNum,recordNum,recordType,smartBatchNum;
    @FXML
    private DatePicker dob,appointmentDate, recordDate, smartDate;
    @FXML
    private ChoiceBox<String> appointmentTime, appointmentType, appointmentPPSN,smartCentre,smartBooth,smartTime,
    smartType;
    @FXML
    private TextArea address,accessibility,centreAddress,boothInfo,appointmentDetails,smartDetails;
    @FXML
    private AnchorPane patientAdd,centreAdd,boothAdd,appointmentAdd, recordAdd, smartAdd;
    private VaccinationCentre currentCentre;
    private VaccinationBooth currentBooth;
    private Patient currentPatient;
    //Returns to the mainmenu scene
    public void viewMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VaccineApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        VaccineApplication.mainWindow.setTitle("Vaccine Management System");
        VaccineApplication.mainWindow.setScene(scene);
        VaccineApplication.mainWindow.show();
    }
    //Initialises the listview and sets the content of all the choice boxes
    public void initialize(){
        modelTitle.setText(MainController.getCurrentTitle());
        appointmentDate.valueProperty().addListener((
                ov, oldValue, newValue) -> populateTime(currentBooth,appointmentDate.getValue(),appointmentTime));
        setSmartDate();
        appointmentType.getItems().addAll("Pfizer","Moderna","AstraZeneca","Johnson & Johnson");
        smartType.getItems().addAll("Pfizer","Moderna","AstraZeneca","Johnson & Johnson");
        if(MainController.getCurrentTitle().equals("Patients")) {
            for(Patient patient : VaccineApplication.getDriver().getPatients())
                mainList.getItems().add(patient.toString());
            patientAdd.setVisible(true);
        }else{
            for(VaccinationCentre centre : VaccineApplication.getDriver().getCentres())
                mainList.getItems().add(centre.toString());
            centreAdd.setVisible(true);

        }
    }
    // Populates the add pane with the relevant info from the item selected in the listview
    public void viewEdit() {
        int index = mainList.getSelectionModel().getSelectedIndex();

        if (index != -1) {
            if (MainController.getCurrentTitle().equals("Vaccination Centres")) {
                VaccinationCentre currentCentre = VaccineApplication.getDriver().getCentres().get(index);
            centreName.setText(currentCentre.getName());
            centreAddress.setText(currentCentre.getAddress());
            eircode.setText(currentCentre.getEircode());
            centreParkingSpaces.setText(String.valueOf(currentCentre.getParkingSpaces()));

            }else if(MainController.getCurrentTitle().equals("Vaccination Booths")){
                VaccinationBooth currentBooth = currentCentre.getBooth(index);
                boothLevel.setText(String.valueOf(currentBooth.getFloorLevel()));
                boothInfo.setText(currentBooth.getAccessibilityInfo());
                boothID.setText(currentBooth.getBoothID());
            }
            else if(MainController.getCurrentTitle().equals("Vaccination Appointments")){
                VaccinationAppointment currentAppointment = currentBooth.getAppointment(index);
               appointmentDate.setValue(currentAppointment.getDate());
                appointmentPPSN.setValue(currentAppointment.getPatientPPSN());
                appointmentTime.setValue(currentAppointment.getTime());
                appointmentDetails.setText(currentAppointment.getVaccinatorDetails());
                appointmentType.setValue(currentAppointment.getType());
                appointmentNum.setText(currentAppointment.getBatchNum());
            }else if(MainController.getCurrentTitle().equals("Patients")){
                Patient patient = VaccineApplication.getDriver().getPatients().get(index);
                firstName.setText(patient.getFirstName());
                lastName.setText(patient.getSecondName());
                phoneNum.setText(patient.getPhoneNum());
                email.setText(patient.getEmail());
                address.setText(patient.getAddress());
                patientEircode.setText(patient.getEircode());
                dob.setValue(patient.getDateOfBirth());
                ppsn.setText(patient.getPPSN());
                accessibility.setText(patient.getAccessibilityInfo());
            }else{
                recordNum.setText(currentPatient.getRecord(index).getBatchNum());
                recordType.setText(currentPatient.getRecord(index).getType());
                recordDate.setValue(currentPatient.getRecord(index).getDate());
            }
        }
    }
    public void editCentre(){
        try {
            int index = mainList.getSelectionModel().getSelectedIndex();
            if(!VaccineApplication.getDriver().centreNameExists(centreName.getText())||
                    centreName.getText().equals(VaccineApplication.getDriver().getCentre(index).getName())) {

                if (index != -1) {
                    VaccineApplication.getDriver().editVaccinationCentre(index, centreName.getText(),
                            centreAddress.getText(),
                            eircode.getText(),
                            centreParkingSpaces.getText());
                }
                mainList.getItems().clear();
                for (VaccinationCentre centre : VaccineApplication.getDriver().getCentres())
                    mainList.getItems().add(centre.toString());
            }else
                AlertBox.display("Error","Name must be unique");
        }catch(RuntimeException runtimeException){
            Utils.AlertBox.display("Error!","Invalid Details Entered \n \n"+runtimeException.getMessage());
        }

    }
    public void editBooth(){
        try {
            int index = mainList.getSelectionModel().getSelectedIndex();
            if(!currentCentre.validID(boothID.getText())||boothID.getText().equals(currentCentre.getBooth(index).getBoothID())) {
                if (index != -1) {
                    currentCentre.editBooth(index, new VaccinationBooth(boothID.getText(),
                            boothInfo.getText(), Integer.parseInt(boothLevel.getText())));
                }
                mainList.getItems().clear();
                for (VaccinationBooth booth : currentCentre.getBooths())
                    mainList.getItems().add(booth.toString());
            }else
                AlertBox.display("Error","ID must be unique");
        }catch(RuntimeException runtimeException){
            Utils.AlertBox.display("Error!","Invalid Details Entered \n \n"+runtimeException.getMessage());
        }

    }

    public void editAppointment(){
        try {
            int index = mainList.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                VaccinationAppointment currentAppointment = currentBooth.getAppointment(index);
                if(!currentBooth.alreadyBooked(appointmentDate.getValue(),appointmentTime.getValue())||
                        (appointmentDate.getValue().equals(currentAppointment.getDate()))&&
                        appointmentTime.getValue().equals(currentAppointment.getTime())) {
                    currentBooth.editAppointment(index, new VaccinationAppointment(appointmentDate.getValue(),
                            appointmentTime.getValue(), appointmentType.getValue(),
                            appointmentNum.getText(), appointmentDetails.getText(), appointmentPPSN.getValue()));
                }
            }
            mainList.getItems().clear();
            for (VaccinationAppointment appointment : currentBooth.getAppointments())
                mainList.getItems().add(appointment.toString());
        }catch(RuntimeException runtimeException){
            Utils.AlertBox.display("Error!","Invalid Details Entered \n \n"+runtimeException.getMessage());
        }
    }

    public void editPatient(){
        try {
            int index = mainList.getSelectionModel().getSelectedIndex();
            String PPSN = VaccineApplication.getDriver().getPatients().get(index).getPPSN();


            if (index != -1) {
                if(VaccineApplication.getDriver().validPPSN(PPSN)&&!PPSN.equals(ppsn.getText())) {
                    Utils.AlertBox.display("Error!","PPSN belongs to another patient");
                }else{
                    Patient newPatient = new Patient(ppsn.getText(),
                            firstName.getText(),
                            lastName.getText(),
                            dob.getValue(),
                            phoneNum.getText(),
                            email.getText(),
                            address.getText(),
                            patientEircode.getText(),
                            accessibility.getText());
                    VaccineApplication.getDriver().editPatient(index, newPatient);
                    mainList.getItems().clear();
                    for (Patient patient : VaccineApplication.getDriver().getPatients())
                        mainList.getItems().add(patient.toString());
                }
            }
        }catch(RuntimeException runtimeException){
            Utils.AlertBox.display("Error!","Invalid Details Entered \n \n"+runtimeException.getMessage());
        }
    }
    //Depending on the pane displayed, Appropriate items are deleted from their respective linked lists
    public void delete(){

        VaccineApplication.mainWindow.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.DELETE) {
                int index = mainList.getSelectionModel().getSelectedIndex();
                if(!boothAdd.isVisible())
                    mainList.getItems().remove(index);
                if (patientAdd.isVisible())
                    VaccineApplication.getDriver().deletePatient(index);
                else if (centreAdd.isVisible())
                    VaccineApplication.getDriver().deleteVaccinationCentre(index);
                else if (boothAdd.isVisible()) {
                    CoolLinkedList<VaccinationAppointment> appointments = currentCentre.getBooth(index).getAppointments();
                    if(currentCentre.boothAmount()==1){
                        if(AlertBox.displayConfirmation("Error","No other booths available","This will permanently delete all appointments in this booth")) {
                            currentCentre.deleteBooth(index);
                            mainList.getItems().remove(index);
                        }
                    }else {
                        currentCentre.deleteBooth(index);
                        mainList.getItems().remove(index);
                            for (int i = 0; i < appointments.size(); ++i)
                                currentCentre.getBooth(i%currentCentre.boothAmount()).getAppointments().add(appointments.get(i));

                    }

                }
                else if(appointmentAdd.isVisible())
                    currentBooth.deleteAppointment(index);
            else
                currentPatient.deleteRecord(index);
            }
        });
    }
    // Depending on visible pane, The appropriate items are displayed that would lead the user back
    public void back() throws IOException {
        mainList.getItems().clear();
        if (patientAdd.isVisible())
        viewMainMenu();
        else if (centreAdd.isVisible())
        viewMainMenu();
        else if (boothAdd.isVisible()) {
            mainList.getItems().clear();
            for(VaccinationCentre centre : VaccineApplication.getDriver().getCentres())
                mainList.getItems().add(centre.toString());
            centreAdd.setVisible(true);
            boothAdd.setVisible(false);
            MainController.setCurrentTitle("Vaccination Centres");
            modelTitle.setText("Vaccination Centres");
            VaccineApplication.mainWindow.setTitle("Vaccination Centres");

        }
        else if(appointmentAdd.isVisible()) {
            for (VaccinationBooth booth : currentCentre.getBooths())
                mainList.getItems().add(booth.toString());
            appointmentAdd.setVisible(false);
            boothAdd.setVisible(true);
            MainController.setCurrentTitle("Vaccination Booths");
            modelTitle.setText(currentCentre.getName()+" - Booths");
            VaccineApplication.mainWindow.setTitle(currentCentre.getName()+" - Booths");
        }
        else{
            for(Patient patient : VaccineApplication.getDriver().getPatients())
                mainList.getItems().add(patient.toString());
            patientAdd.setVisible(true);
            recordAdd.setVisible(false);
            smartAdd.setVisible(false);
            smartCentre.getItems().clear();
            smartBooth.getItems().clear();
            smartDate.getEditor().clear();
            smartTime.getItems().clear();
            smartType.getItems().clear();
            smartBatchNum.clear();
            smartDetails.clear();
            MainController.setCurrentTitle("Patients");
            modelTitle.setText("Patients");
            VaccineApplication.mainWindow.setTitle("Patients");

    }
    }

    public void addPatient(){
        try {
            if(Utilities.validPPS(ppsn.getText())) {
                if (!VaccineApplication.getDriver().validPPSN(ppsn.getText())) {
                    VaccineApplication.getDriver().addPatient(ppsn.getText(),
                            firstName.getText(),
                            lastName.getText(),
                            dob.getValue(),
                            phoneNum.getText(),
                            email.getText(),
                            address.getText(),
                            patientEircode.getText(),
                            accessibility.getText());


                    mainList.getItems().add(new Patient(ppsn.getText(),
                            firstName.getText(),
                            lastName.getText(),
                            dob.getValue(),
                            phoneNum.getText(),
                            email.getText(),
                            address.getText(),
                            patientEircode.getText(),
                            accessibility.getText()).toString());
                    firstName.clear();
                    lastName.clear();
                    dob.getEditor().clear();
                    phoneNum.clear();
                    email.clear();
                    address.clear();
                    patientEircode.clear();
                    ppsn.clear();
                    accessibility.clear();
                } else
                    Utils.AlertBox.display("Error!", "PPSN already in the system");
            }else
                Utils.AlertBox.display("Error!","PPSN Must be XXXXXXXYY \n Where X is a numeric " +
                        "character and Y is an alphabetic character");
        }catch(RuntimeException runtimeException){
            Utils.AlertBox.display("Error!","Invalid Details Entered \n \n"+runtimeException.getMessage());
        }
    }

    public void addCentre(){
        try {
            if(!VaccineApplication.getDriver().centreNameExists(centreName.getText())){
            VaccineApplication.getDriver().addVaccinationCentre(
                    centreName.getText(),
                    centreAddress.getText(),
                    eircode.getText(),
                    centreParkingSpaces.getText());

            mainList.getItems().add(new VaccinationCentre(centreName.getText(),
                    centreAddress.getText(),
                    eircode.getText(),
                    Integer.parseInt(centreParkingSpaces.getText())).toString());
            centreName.clear();
            centreAddress.clear();
            eircode.clear();
            centreParkingSpaces.clear();}else
                AlertBox.display("Error","Name must be unique");
        }catch(RuntimeException runtimeException){
            Utils.AlertBox.display("Error!","Invalid Details Entered \n \n"+runtimeException.getMessage());
        }

    }
    public void addBooth(){
        try {
            if(Utilities.validBoothID(boothID.getText())) {
                if (!currentCentre.validID(boothID.getText())) {
                    currentCentre.addBooth(boothID.getText(), boothInfo.getText(), Integer.parseInt(boothLevel.getText()));
                    mainList.getItems().add(new VaccinationBooth(boothID.getText(),
                            boothInfo.getText(), Integer.parseInt(boothLevel.getText())).toString());
                    boothID.clear();
                    boothInfo.clear();
                    boothLevel.clear();
                } else
                    AlertBox.display("Error", "ID already used");
            }else
                AlertBox.display("Error!","Id must take the form XY \n " +
                        "Where X is a alphabetic character and Y is a numeric one.");


        }catch(RuntimeException runtimeException){
            Utils.AlertBox.display("Error!","Invalid Details Entered \n \n"+runtimeException.getMessage());
        }
    }
    public void addAppointment(){
        try {
            if(!currentBooth.alreadyBooked(appointmentDate.getValue(),appointmentTime.getValue())){
            if(VaccineApplication.getDriver().validPPSN( appointmentPPSN.getValue())) {
                currentBooth.addAppointment(appointmentDate.getValue(),  appointmentTime.getValue(),  appointmentType.getValue(),
                        appointmentNum.getText(), appointmentDetails.getText(),  appointmentPPSN.getValue());
                mainList.getItems().add(new VaccinationAppointment(appointmentDate.getValue(),
                         appointmentTime.getValue(),  appointmentType.getValue(),
                        appointmentNum.getText(), appointmentDetails.getText(),  appointmentPPSN.getValue()).toString());
                appointmentDate.getEditor().clear();
                appointmentType.getSelectionModel().clearSelection();
                appointmentNum.clear();
                appointmentDetails.clear();
                appointmentPPSN.getSelectionModel().clearSelection();
                appointmentTime.getSelectionModel().clearSelection();
            }else{
                Utils.AlertBox.display("Error!","Patient not in the system");
            }}else
                AlertBox.display("Error!","Date already booked");
        }catch(RuntimeException runtimeException){
            Utils.AlertBox.display("Error!","Invalid Details Entered \n \n"+runtimeException.getMessage());
        }
    }
    public void sortPatients(){
        VaccineApplication.getDriver().getPatients().shellSort(Comparator.comparing(Patient::getSecondName));
        mainList.getItems().clear();
        for(Patient patient : VaccineApplication.getDriver().getPatients())
            mainList.getItems().add(patient.toString());

    }
    public void editRecord(){
        try {
            int index = mainList.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                currentBooth.editAppointment(index, new VaccinationAppointment(appointmentDate.getValue(),
                         appointmentTime.getValue(),  appointmentType.getValue(),
                        appointmentNum.getText(), appointmentDetails.getText(),  appointmentPPSN.getValue()));
            mainList.getItems().clear();
            for (VaccinationAppointment appointment : currentBooth.getAppointments())
                mainList.getItems().add(appointment.toString());}
        }catch(RuntimeException runtimeException){
            Utils.AlertBox.display("Error!","Invalid Details Entered \n \n"+runtimeException.getMessage());
        }
    }
    //Converts the selected appointment to a record and adds it to the respective patient
    public void completeAppointment(){
        try {
            int index = mainList.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                VaccinationAppointment appointment = currentBooth.getAppointment(index);
                for (Patient patient : VaccineApplication.getDriver().getPatients()) {
                    if (patient.getPPSN().equals(appointment.getPatientPPSN())){
                        mainList.getItems().remove(index);
                        currentBooth.deleteAppointment(index);
                        patient.addRecord(appointment.getDate(),appointment.getType(),appointment.getBatchNum());
                        break;
                    }

                }
            }
        }catch(RuntimeException runtimeException){
            Utils.AlertBox.display("Error!","Invalid Details Entered \n \n"+runtimeException.getMessage());
        }
    }
    //Shows the smart add pane and populates centre choice box
    //Also finds a centre with a similiar eircode and makes it the default, if none are available, sets the
    //default to the centre with the lowest amount of appointments.
    public void showSmartAdd(){
        int index = mainList.getSelectionModel().getSelectedIndex();
        if(index>=0) {
            currentPatient = VaccineApplication.getDriver().getPatients().get(index);
            modelTitle.setText("Selected Patient: "+currentPatient.getFirstName()+" "+currentPatient.getSecondName());
            smartAdd.setVisible(true);
            patientAdd.setVisible(false);
            for (VaccinationCentre centre : VaccineApplication.getDriver().getCentres())
                smartCentre.getItems().add(centre.getName());

        smartCentre.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> smartPopulateBooths());
        if(VaccineApplication.getDriver().centreAmount()!=0) {
            VaccinationCentre defaultCentre = null;
            for(VaccinationCentre centre:VaccineApplication.getDriver().getCentres()){
                if(centre.getEircode().toLowerCase().contains(currentPatient.getEircode().strip().substring(0,3).toLowerCase())){
                    defaultCentre = centre;
                    break;}
            }
            if(defaultCentre==null) {
                defaultCentre = VaccineApplication.getDriver().getCentre(smartCentre.getItems().get(0));
                for (VaccinationCentre centre : VaccineApplication.getDriver().getCentres()) {
                    if (defaultCentre.appointmentAmount()> centre.appointmentAmount() && centre.boothAmount()>=1)
                        defaultCentre = centre;
                }
            }

            smartCentre.setValue(defaultCentre.getName());
            smartPopulateBooths();
        }
        }
    }
    //Triggers on a change in the centre choice box or the load of the smart view
    //This method adds in the booths of the chosen centre to the choice box
    //Also makes the default one the one with the least appointments
    public void smartPopulateBooths() {
        if (smartCentre.getValue() != null) {
            currentCentre = VaccineApplication.getDriver().getCentre(smartCentre.getValue());
            smartBooth.getItems().clear();
            for (VaccinationBooth booth : currentCentre.getBooths()) {
                smartBooth.getItems().add(booth.getBoothID());
            }
        }
        if (currentCentre.boothAmount() != 0) {
            VaccinationBooth defaultBooth = currentCentre.getBooth(smartBooth.getItems().get(0));
            for (VaccinationBooth booth : currentCentre.getBooths()) {
                if (defaultBooth.appointmentAmount() > booth.appointmentAmount())
                    defaultBooth = booth;
            }
            smartBooth.setValue(defaultBooth.getBoothID());
        }
    }
            public void setSmartDate(){
        //https://stackoverflow.com/questions/14522680/javafx-choicebox-events
                smartDate.valueProperty().addListener((ov, oldValue, newValue) -> populateTime(currentCentre.getBooth(smartBooth.getValue())
                        ,smartDate.getValue(),smartTime));
            }
    //Books an appointment for the patient in the specified booth with the specified details
    public void smartAdd(){
        try {
            VaccinationCentre centre = VaccineApplication.getDriver().getCentre(smartCentre.getValue());
            VaccinationBooth booth = centre.getBooth(smartBooth.getValue());
            booth.addAppointment(smartDate.getValue(), smartTime.getValue(), smartType.getValue(), smartBatchNum.getText(), smartDetails.getText(), currentPatient.getPPSN());
            modelTitle.setText("Patients");
            smartAdd.setVisible(false);
            patientAdd.setVisible(true);
            AlertBox.display("Appointment Booked","Appointment Booked in: "+centre.getName()+" \nBooth: "+
                    booth.getBoothID());
            smartDate.getEditor().clear();
            smartCentre.getItems().clear();
            smartTime.getSelectionModel().clearSelection();
        }catch(RuntimeException runtimeException){
            AlertBox.display("Error!",runtimeException.getMessage());
        }
    }
    //Helper method to populate time based on a booth and a specific date.
    public void populateTime(VaccinationBooth booth ,LocalDate date,ChoiceBox<String> timeBox){
        timeBox.getItems().clear();
        for(int i = 9; i <18;++i){
            String time = i+":00";
            if(!booth.alreadyBooked(date,time))
                timeBox.getItems().add(time);
        }
    }
    //Method to move user to relevant view when they double click on an item in the listview
    public void view(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            int index = mainList.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                switch (MainController.getCurrentTitle()) {
                    case "Vaccination Centres" -> {
                        currentCentre = VaccineApplication.getDriver().getCentres().get(index);
                        mainList.getItems().clear();
                        for (VaccinationBooth booth : currentCentre.getBooths()) {
                            mainList.getItems().add(booth.toString());
                        }
                        centreAdd.setVisible(false);
                        boothAdd.setVisible(true);
                        MainController.setCurrentTitle("Vaccination Booths");
                        modelTitle.setText(currentCentre.getName() + " - Booths");
                        VaccineApplication.mainWindow.setTitle(currentCentre.getName() + " - Booths");
                    }
                    case "Vaccination Booths" -> {
                        currentBooth = currentCentre.getBooth(index);
                        mainList.getItems().clear();
                        for (VaccinationAppointment appointment : currentBooth.getAppointments()) {
                            mainList.getItems().add(appointment.toString());
                        }
                        boothAdd.setVisible(false);
                        appointmentAdd.setVisible(true);
                        for (Patient patient : VaccineApplication.getDriver().getPatients())
                            appointmentPPSN.getItems().add(patient.getPPSN());
                        MainController.setCurrentTitle("Vaccination Appointments");
                        modelTitle.setText("Booth " + currentBooth.getBoothID() + " - Appointments");
                        VaccineApplication.mainWindow.setTitle("Booth " + currentBooth.getBoothID() + " - Appointments");
                    }
                    case "Patients" -> {
                        mainList.getItems().clear();
                        currentPatient = VaccineApplication.getDriver().getPatients().get(index);
                        for (VaccinationRecord record : currentPatient.getRecords())
                            mainList.getItems().add(record.toString());
                        patientAdd.setVisible(false);
                        recordAdd.setVisible(true);
                        MainController.setCurrentTitle(currentPatient.getFirstName() + " - Records");
                        modelTitle.setText(currentPatient.getFirstName() + " - Records");
                        VaccineApplication.mainWindow.setTitle(currentPatient.getFirstName() + " - Records");
                    }
                }
            }
        }
    }

}
