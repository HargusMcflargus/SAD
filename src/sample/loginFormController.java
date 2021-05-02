package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class loginFormController {
    @FXML
    public TextField user;
    @FXML
    public TextField password;
    @FXML
    public Text signInText;
    @FXML
    ImageView logo;
    @FXML ImageView loginLogo;
    @FXML ImageView usernameLogo;
    @FXML ImageView passwordLogo;

    public void logIn(String tempUsername) throws IOException {
        user.getScene().getWindow().hide();

        Stage actualStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        dashboardController controller = loader.getController();
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        actualStage.setScene(newScene);
        actualStage.setOnShowing(windowEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Welcome " + tempUsername + "!");
            alert.showAndWait();
        });
        actualStage.setTitle("Caloocan City North Budget Tracker");

        actualStage.show();
    }

    public void clearThings() {
        user.setText("");
        password.setText("");
    }

    public void verify() throws Exception {
        DatabaseManager databaseManager = new DatabaseManager();
        if (databaseManager.verifyUser(user.getText(), password.getText())){
            logIn(user.getText());
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "User and password not found");
        alert.showAndWait();
    }

    public void registration() throws IOException {
        Stage regStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registerUser.fxml"));
        Parent root = loader.load();
        Scene regScene = new Scene(root);
        regStage.setScene(regScene);
        regStage.setResizable(false);
        regStage.setTitle("Registration");
        regStage.show();
    }

    public void signInHover() {
        signInText.setFill(Color.BLUE);
    }

    public void signInHoverOut() {
        signInText.setFill(Color.BLACK);
    }



}
