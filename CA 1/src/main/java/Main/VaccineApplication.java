package Main;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import Utils.AlertBox;

import java.io.*;
import java.util.Objects;

public class VaccineApplication extends Application {
    public static Stage mainWindow;
    private static Driver driver;

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        driver = new Driver();
        mainWindow = stage;
        if(AlertBox.displayConfirmation("Load?","Would you like to load from xml?","")){
            load();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(VaccineApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Vaccination Management System");
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {  // https://stackoverflow.com/questions/26619566/javafx-stage-close-handler

            try {
                if(AlertBox.displayConfirmation("Save?","Would you like to save your work?","")){
                    save();
                    System.out.println("Saved!");
                }

            } catch (IOException e) {
                AlertBox.display("Error Saving","Your progress has not been saved... \n"+e.getMessage());
            }
        });
        stage.setScene(scene);
        stage.show();
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/images/icon.png")).toExternalForm());
        stage.getIcons().add(icon); // https://stackoverflow.com/questions/10121991/javafx-application-icon
}


    public static void main(String[] args) {
        launch();
    }

   public static void save() throws IOException {
       XStream xstream = new XStream(new DomDriver());
       ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("Management.xml"));
       out.writeObject(driver);
       out.close();
   }
   public static void load() throws IOException, ClassNotFoundException {
       XStream xstream = new XStream(new DomDriver());
       xstream.addPermission(AnyTypePermission.ANY);
       ObjectInputStream is = xstream.createObjectInputStream(new FileReader("Management.xml"));
           driver = (Driver) is.readObject();
       is.close();
   }

    public static Driver getDriver() {
        return driver;
    }

    public static void setDriver(Driver driver) {
        VaccineApplication.driver = driver;
    }
}