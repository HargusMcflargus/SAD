package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class dashboardController {

    @FXML AnchorPane mainPane;

    @FXML JFXButton dashboardButton;

    @FXML JFXButton historyButton;

    @FXML JFXButton dataEntry;

    @FXML JFXButton logOUt;

    @FXML ImageView userImage;

    @FXML AnchorPane adminControls;

    @FXML Text title;

    public int selection = 1;
    //TODO Image ng contractor,
    //TODO Pagination (COlor ng pornhub)
    //TODO logo ng city hall (for change pa to kasi papaalam pa)

    //Change right panel to dashboard class (FORM)
    public void switchToDashBoard() throws IOException, SQLException, ParseException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashBoardPane.fxml"));
        Parent root = loader.load();
        mainPane.getChildren().set(2, root);
        selection = 1;
        dashBoardPaneContoller dashBoardPaneContoller = loader.getController();
        dashBoardPaneContoller.initializeEverything();
        dashBoardHoverIn();
        historyHoverOut();
        dataEntryHoverOut();
        title.setText("DASHBOARD");
    }
    public void switchToHistory() throws IOException{
        FXMLLoader loader  = new FXMLLoader(getClass().getResource("historyPane.fxml"));
        mainPane.getChildren().set(2, loader.load());
        historyPaneController controller = loader.getController();
        controller.init();
        selection = 2;
        dashBoardHoverOut();
        dataEntryHoverOut();
        title.setText("History");
    }
    public void switchToSetting() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settingPane.fxml"));
        mainPane.getChildren().set(2, loader.load());
        settingPaneController controller = loader.getController();
        controller.init();
        selection = 3;
        historyHoverOut();
        dashBoardHoverOut();
        title.setText("Admin Controls");
    }

    public void logOUt(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK){
                logOUt.getScene().getWindow().hide();
                dataEntry.setDisable(false);
                Main.logInStage.show();
            }
        });
    }

    //Nav Button Hover in and out
    public void dashBoardHoverIn(){
        if (!(selection == 1)){
            dashboardButton.setButtonType(JFXButton.ButtonType.RAISED);
            dashboardButton.setStyle("-fx-background-color: linear-gradient(to right, #1b62cd 30%, transparent); ");
        }
    }
    public void dashBoardHoverOut(){
        dashboardButton.setButtonType(JFXButton.ButtonType.FLAT);
        if (selection == 1){
            dashboardButton.setStyle("-fx-background-color: linear-gradient(to right, #1b62cd 30%, transparent); ");
        }
        else{
            dashboardButton.setStyle("-fx-background-color: #EB8921;");
        }
    }
    public void historyHoverIn(){
        if (!(selection == 2)){
            historyButton.setButtonType(JFXButton.ButtonType.RAISED);
            historyButton.setStyle("-fx-background-color: linear-gradient(to right, #1b62cd 30%, transparent); ");
        }
    }
    public void historyHoverOut(){
        historyButton.setButtonType(JFXButton.ButtonType.FLAT);
        if (selection == 2){
            historyButton.setStyle("-fx-background-color: linear-gradient(to right, #1b62cd 30%, transparent); ");
        }
        else{
            historyButton.setStyle("-fx-background-color: #EB8921;");
        }
    }
    public  void dataEntryHoverIn(){
        if (!(selection == 3)){
            dataEntry.setButtonType(JFXButton.ButtonType.RAISED);
            dataEntry.setStyle("-fx-background-color: linear-gradient(to right, #1b62cd 30%, transparent); ");
        }
    }
    public void dataEntryHoverOut(){
        dataEntry.setButtonType(JFXButton.ButtonType.FLAT);
        if (selection == 3){
            dataEntry.setStyle("-fx-background-color: linear-gradient(to right, #1b62cd 30%, transparent); ");
        }
        else{
            dataEntry.setStyle("-fx-background-color: #EB8921;");
        }
    }

    public  void logOutHoverIn(){
        if (!(selection == 4)){
            logOUt.setButtonType(JFXButton.ButtonType.RAISED);
            logOUt.setStyle("-fx-background-color: linear-gradient(to right, #1b62cd 30%, transparent); ");
        }
    }
    public void logOutHoverOut(){
        logOUt.setButtonType(JFXButton.ButtonType.FLAT);
        if (selection == 4){
            logOUt.setStyle("-fx-background-color: linear-gradient(to right, #1b62cd 30%, transparent); ");
        }
        else{
            logOUt.setStyle("-fx-background-color: #EB8921;");
        }
    }
}
