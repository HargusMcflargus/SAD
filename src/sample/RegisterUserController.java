package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;


public class RegisterUserController {
    @FXML TextField firstName;
    @FXML TextField lastName;
    @FXML TextField userField;
    @FXML TextField passwordField;
    @FXML TextField passWordField2;

    public void register() throws Exception {
        DatabaseManager databaseManager = new DatabaseManager();
        if (firstName.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, "First Name Field Empty");
            alert.showAndWait();
            return;
        }
        else if(lastName.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Last Name Field Empty");
            alert.showAndWait();
            return;
        }
        else if(passwordField.getText().isEmpty() || passWordField2.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty password field");
            alert.showAndWait();
            return;
        }
        else if(userField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Empty Username field");
            alert.showAndWait();
            return;
        }
        else if(!(passwordField.getText().equals(passWordField2.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Retyped password do not match");
            alert.showAndWait();
            return;
        }
        else if (databaseManager.verifyUser(userField.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "User Already Exists");
            alert.showAndWait();
            return;
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
