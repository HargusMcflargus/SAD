package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {
    public static Stage MainStage;
    static loginFormController mainController;

    protected Connection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginForm.fxml"));
        Parent root = loader.load();

        mainController = loader.getController();
        mainController.logo.setImage(new Image("img/loonalogo.png"));
        mainController.usernameLogo.setImage(new Image("img/user.png"));
        mainController.passwordLogo.setImage(new Image("img/key.png"));

        primaryStage.setTitle("Log In");
        Scene thing = new Scene(root);
        primaryStage.setScene(thing);
        primaryStage.getIcons().add(new Image("img/ot12.png"));

        primaryStage.setResizable(false);

        dbInitilize();


        primaryStage.show();
    }

    public int dbInitilize(){
        try {
            String url="jdbc:ucanaccess://src/db.accdb;";
            connection = DriverManager.getConnection(url);
            ResultSet resultSet = executeStatement("SELECT * FROM Users;");
            while(resultSet.next()){
                System.out.println(resultSet.getString("username"));
            }
            return 0;
        }catch (Exception e){
            System.out.println(e);
            return -1;
        }
    }

    public ResultSet executeStatement(String sqlstatement) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            return statement.executeQuery(sqlstatement);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
