package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;


public class RegisterUserController {
    @FXML TextField firstName;
    @FXML TextField lastName;
    @FXML TextField userField;
    @FXML TextField passwordField;
    @FXML TextField passWordField2;

    public void register() throws Exception {
        DatabaseManager databaseManager = new DatabaseManager();
        if (firstName.getText().isEmpty()){
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
        else if(databaseManager.verifyName(firstName.getText(), lastName.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "User Already Exists");
            alert.showAndWait();
            return;
        }
        String[] values = new String[]{
          userField.getText(),
          passwordField.getText(),
          firstName.getText(),
          lastName.getText()
        };
        if (databaseManager.executeStatement("INSERT INTO Users ([username], [password], [firstname], [lastname]) VALUES (?, ?, ?, ?) ", values)){
            Alert alert = new Alert(Alert.AlertType.WARNING, "User Added");
            alert.showAndWait().ifPresent(response ->{
                if (response == ButtonType.OK){
                    try {
                        Main.loginFormController.mainPane.getChildren().set(1, FXMLLoader.load(getClass().getResource("loginPanel.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        userField.clear();
        passwordField.clear();
        passWordField2.clear();
        firstName.clear();
        lastName.clear();
    }
}
