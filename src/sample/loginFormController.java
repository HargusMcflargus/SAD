package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class loginFormController {
    @FXML AnchorPane mainPane;
    @FXML ImageView logo;

    public void initElements() throws IOException {
        mainPane.getChildren().set(1, FXMLLoader.load(getClass().getResource("loginPanel.fxml")));
    }
}
