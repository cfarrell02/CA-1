module com.example.vaccination_management_system {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires xstream;
    requires json;


    opens Main to javafx.fxml, xstream;
    exports Main;
    exports Utils to xstream;
    exports Models to xstream;
    opens Models to xstream;
    opens Utils to xstream, json;

}