package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class historyPaneController {
    @FXML VBox container;
    @FXML TextField searchField;
    @FXML ChoiceBox choices;
    @FXML ScrollPane scrollPane;

    public void init(){
        container.prefWidthProperty().bind(scrollPane.prefWidthProperty());
        container.prefWidthProperty().bind(scrollPane.widthProperty());
        choices.getItems().addAll("Projects", "Yearly Budget");
        choices.setOnAction(v->{
            if (choices.getSelectionModel().getSelectedIndex() == 0)
                showProjects();
            else
                showBudget();
        });
        choices.getSelectionModel().select("Projects");

    }

    public void showProjects(){
        container.getChildren().clear();
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Projects");
            while (resultSet.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("titledPaneFormat.fxml"));
                Parent root = loader.load();
                container.getChildren().add(root);

                titledPaneFormatController controller = loader.getController();
                controller.setValues(
                        resultSet.getString("ProjectName"),
                        resultSet.getString("ProjectManager"),
                        resultSet.getString("ProjectType"),
                        resultSet.getString("DateStarted"),
                        resultSet.getString("ActualCost")
                );
                controller.titledPane.prefWidthProperty().bind(container.prefWidthProperty());
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void showBudget(){
        container.getChildren().clear();
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Balance");
            while (resultSet.next()) {
                TitledPane titledPane = new TitledPane();
                titledPane.setText(resultSet.getString("EntryDate"));
                titledPane.setContent(new Text(fixNumber(resultSet.getString("Balance"))));
                container.getChildren().add(titledPane);
                titledPane.prefWidthProperty().bind(container.prefWidthProperty());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeList(){
        container.getChildren().clear();
        try {
            if (choices.getSelectionModel().getSelectedIndex() == 0){
                ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Projects;");
                while (resultSet.next()){
                    if (
                            resultSet.getString("ProjectName").contains(searchField.getText())
                                    || resultSet.getString("ProjectManager").contains(searchField.getText())
                                    || resultSet.getString("ProjectType").contains(searchField.getText())
                                    || resultSet.getString("DateStarted").contains(searchField.getText())
                                    || resultSet.getString("DateStarted").contains(searchField.getText())
                                    || resultSet.getString("DateEnded").contains(searchField.getText())
                                    || resultSet.getString("EstimatedEndDate").contains(searchField.getText())
                                    || resultSet.getString("Status").contains(searchField.getText())
                    ){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("titledPaneFormat.fxml"));
                        container.getChildren().add(loader.load());

                        titledPaneFormatController controller = loader.getController();
                        controller.setValues(
                                resultSet.getString("ProjectName"),
                                resultSet.getString("ProjectManager"),
                                resultSet.getString("ProjectType"),
                                resultSet.getString("DateStarted"),
                                resultSet.getString("ActualCost")
                        );
                        controller.titledPane.prefWidthProperty().bind(container.prefWidthProperty());
                    }
                }
            }
            else{
                ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Balance;");
                while (resultSet.next()){
                    if (
                            resultSet.getString("EntryDate").contains(searchField.getText())
                            || resultSet.getString("Balance").contains(searchField.getText())
                    ){
                        TitledPane titledPane = new TitledPane();
                        titledPane.setText(resultSet.getString("EntryDate"));
                        titledPane.setContent(new Text(fixNumber(resultSet.getString("Balance"))));
                        container.getChildren().add(titledPane);
                        titledPane.prefWidthProperty().bind(container.prefWidthProperty());
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        container.getChildren().add(new Text("End of Results"));
    }


    public String fixNumber(String number){
        String newString = "";
        int i = number.length() - 1, flag = 3;
        while (i >= 0){
            if (flag == 0){
                flag = 3;
                newString += ",";
            }
            newString += number.charAt(i);
            i--;flag--;
        }
        String finalString = "";
        for(int a = newString.length() - 1; a >= 0; a--){
            finalString += newString.charAt(a);
        }
        return finalString;
    }
}
