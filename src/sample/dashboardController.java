package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;

public class dashboardController {

    @FXML
    AnchorPane mainPane;

    @FXML
    JFXButton dashboardButton;

    @FXML
    JFXButton historyButton;

    @FXML
    JFXButton dataEntry;

    public void switchToDashBoard() throws IOException {
        mainPane.getChildren().set(2, FXMLLoader.load(getClass().getResource("dashBoardPane.fxml")));
    }

    public void dashBoardHoverIn(){
        dashboardButton.setButtonType(JFXButton.ButtonType.RAISED);
        dashboardButton.setRipplerFill(Paint.valueOf("#1b62cd"));
    }
    public void dashBoardHoverOut(){
        dashboardButton.setButtonType(JFXButton.ButtonType.FLAT);
        dashboardButton.setRipplerFill(Paint.valueOf("#183A6D"));
    }
    public void historyHoverIn(){
        historyButton.setButtonType(JFXButton.ButtonType.RAISED);
        historyButton.setRipplerFill(Paint.valueOf("#1b62cd"));
    }
    public void historyHoverOut(){
        historyButton.setButtonType(JFXButton.ButtonType.FLAT);
        historyButton.setRipplerFill(Paint.valueOf("#183A6D"));
    }
    public  void dataEntryHoverIn(){
        dataEntry.setButtonType(JFXButton.ButtonType.RAISED);
        dataEntry.setRipplerFill(Paint.valueOf("#1b62cd"));
    }
    public void dataEntryHoverOut(){
        dataEntry.setButtonType(JFXButton.ButtonType.FLAT);
        dataEntry.setRipplerFill(Paint.valueOf("#183A6D"));
    }
}
