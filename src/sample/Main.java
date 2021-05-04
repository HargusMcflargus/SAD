package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
    @FXML static loginFormController loginFormController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Initiate loginform
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginForm.fxml"));
        Parent root = loader.load();

        //get login FOrm controller and set login pane sa anchor pane on the right
        loginFormController = loader.getController();
        loginFormController.mainPane.getChildren().set(1, FXMLLoader.load(getClass().getResource("loginPanel.fxml")));

        //Name stage
        primaryStage.setTitle("Log In");

        //Set Stage scene (Login Form)
        Scene thing = new Scene(root);
        primaryStage.setScene(thing);

        //Set Icon Image
        primaryStage.getIcons().add(new Image("img/ot12.png"));

        //Set to not sizable
        primaryStage.setResizable(false);

        //set Control buttons
        primaryStage.initStyle(StageStyle.UTILITY);

        //Show actual stage
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
