package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;


public class RegisterUserController {
    @FXML
    TextField userField;
    @FXML TextField passwordField;
    @FXML TextField passWordField2;

    public void register() throws Exception {
        DatabaseManager databaseManager = new DatabaseManager();
        if (databaseManager.verifyUser(userField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "User Already Exists");
            alert.show();
        }
        if (databaseManager.executeStatement("INSERT INTO Users ([username], [password]) VALUES (?, ?) ", new String[]{userField.getText(), passwordField.getText()})){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User Added");

            alert.showAndWait().ifPresent(response ->{
                if (response == ButtonType.OK){
                    userField.getScene().getWindow().hide();
                }
            });
        }
    }
}
