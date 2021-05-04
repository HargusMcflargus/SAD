package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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

    public int selection = 1;

    //TODO Image ng contractor,
    //TODO Pagination (COlor ng pornhub)
    //TODO logo ng city hall (for change pa to kasi papaalam pa)

    //Change right panel to dashboard class (FORM)
    public void switchToDashBoard() throws IOException, SQLException, ParseException {
        dashboardButton.setStyle("-fx-background-color: linear-gradient(to right, #1b62cd 30%, transparent); ");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashBoardPane.fxml"));
        Parent root = loader.load();
        mainPane.getChildren().set(2, root);
        selection = 1;
        dashBoardPaneContoller dashBoardPaneContoller = loader.getController();
        dashBoardPaneContoller.initializeEverything();
        historyHoverOut();
        dataEntryHoverOut();
    }
    public void switchToHistory() throws IOException{
        mainPane.getChildren().set(2, FXMLLoader.load(getClass().getResource("historyPane.fxml")));
        selection = 2;
        dashBoardHoverOut();
        dataEntryHoverOut();
    }
    public void switchToSetting() throws IOException{
        mainPane.getChildren().set(2, FXMLLoader.load(getClass().getResource("settingPane.fxml")));
        selection = 3;
        historyHoverOut();
        dashBoardHoverOut();
    }

    public void logOUt(){
       logOUt.getScene().getWindow().hide();
       dataEntry.setDisable(false);
       Main.logInStage.show();
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
