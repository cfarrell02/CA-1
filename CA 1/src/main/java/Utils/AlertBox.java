package Utils;

import javafx.scene.control.Alert;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

    public class AlertBox {
        // https://code.makery.ch/blog/javafx-dialogs-official/
        public static void display(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.showAndWait();
        }
        public static boolean displayConfirmation(String title, String header,String content) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);

            Optional<ButtonType> result = alert.showAndWait();
            return result.get() == ButtonType.OK;
        }



}
