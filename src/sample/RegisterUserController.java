package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.Hashtable;

public class RegisterUserController {

    private Connection connection;

    @FXML
    Button regButton;
    @FXML
    TextField userField;
    @FXML TextField passwordField;
    @FXML TextField passWordField2;

    public void register() throws SQLException {
        dbInitilize();
        try {
            ResultSet resultSet = executeQuery("SELECT * FROM Users");
            while (resultSet.next()){
                if (resultSet.getString("username").equals(userField.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "User Already Exists");
                    alert.show();
                    return;
                }
            }
            if (executeStatement("INSERT INTO Users ([username], [password]) VALUES ('"+ userField.getText() +"', '" + passwordField.getText() + "');")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "User Added");

                alert.showAndWait().ifPresent(response ->{
                    if (response == ButtonType.OK){
                        userField.getScene().getWindow().hide();
                    }
                });
            };
        }catch (Exception e){

        }

    }
    public int dbInitilize(){
        try {
            String url="jdbc:ucanaccess://src/db.accdb;";
            connection = DriverManager.getConnection(url);
            return 0;
        }catch (Exception e){
            System.out.println(e);
            return -1;
        }
    }

    public boolean executeStatement(String sqlstatement) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(sqlstatement)) {
            statement.execute();
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    public  ResultSet executeQuery(String statement) throws SQLException{
        try {
            PreparedStatement ps = connection.prepareStatement(statement);
            return ps.executeQuery();
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
