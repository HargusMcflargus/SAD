package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class loginPanelController {
    @FXML TextField user;
    @FXML TextField password;
    @FXML Text signInText;

    public void logIn(String tempUsername) throws IOException {
        user.getScene().getWindow().hide();

        Stage actualStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();
        dashboardController controller = loader.getController();
        Scene newScene = new Scene(root);
        actualStage.setScene(newScene);
        actualStage.setOnShowing(windowEvent -> {
            controller.dashboardButton.setStyle("-fx-background-color: linear-gradient(to right, #1b62cd 30%, transparent); ");
            controller.dashboardButton.setButtonType(JFXButton.ButtonType.RAISED);
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
        Main.loginFormController.mainPane.getChildren().set(1, FXMLLoader.load(getClass().getResource("registerUser.fxml")));
    }

    public void signInHover() {
        signInText.setFill(Color.BLUE);
    }

    public void signInHoverOut() {
        signInText.setFill(Color.BLACK);
    }

}
