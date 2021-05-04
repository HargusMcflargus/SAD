package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class dashboardController {

    @FXML
    AnchorPane mainPane;

    @FXML
    JFXButton dashboardButton;

    @FXML
    JFXButton historyButton;

    @FXML
    JFXButton dataEntry;

    public int selection = 1;

    //TODO Balance,
    //TODO Expenses,
    //TODO Image ng contractor,
    //TODO Image sa ilalim ng menu bar (parang profile),
    //TODO Menu bar
    //TODO Disable admin controls
    //TODO Pagination (COlor ng pornhub)
    //TODO logo ng city hall (for change pa to kasi papaalam pa
    //TODO Laman ng per project Additonal data such as Project manager, Date started, Actual end, Estimated date and cost, actual cost, status

    //Change right panel to dashboard class (FORM)
    public void switchToDashBoard() throws IOException {
        mainPane.getChildren().set(2, FXMLLoader.load(getClass().getResource("dashBoardPane.fxml")));
        selection = 1;
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
}
