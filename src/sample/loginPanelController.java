package sample;

import com.healthmarketscience.jackcess.expr.ParseException;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class loginPanelController {
    @FXML TextField user;
    @FXML TextField password;
    @FXML Text signInText;
    Boolean admin = false;


    public void logIn(String tempUsername) throws IOException {
        user.getScene().getWindow().hide();

        Stage actualStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();
        dashboardController controller = loader.getController();
        Scene newScene = new Scene(root);
        actualStage.setScene(newScene);
        actualStage.setOnShowing(windowEvent -> {
            try {
                controller.switchToDashBoard();
                if (admin){
                    controller.userImage.setImage(new Image("img/key.png"));
                }
                else{
                    controller.userImage.setImage(new Image("img/user.png"));
                    controller.dataEntry.setDisable(true);
                }
            } catch (IOException | SQLException | ParseException | java.text.ParseException e) {
                e.printStackTrace();
            }
            user.clear();
            password.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Welcome " + tempUsername + "!");
            alert.showAndWait();
        });
        actualStage.setTitle("Caloocan City North Budget Tracker");
        actualStage.show();
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

    public void setAdmin() throws Exception {
        admin = true;
        verify();
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
