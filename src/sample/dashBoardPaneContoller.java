package sample;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class dashBoardPaneContoller {
    @FXML ChoiceBox yearList;
    @FXML TextField budgetField;
    @FXML TextField expensesFIeld;
    @FXML VBox currentProjectList;
    @FXML ScrollPane scrollPane;
    @FXML CategoryAxis categoryAxis;
    @FXML NumberAxis numberAxis;
    @FXML LineChart<String, Number> chart;

    public ResultSet wholeResultSet;

    //TODO add comments


    public void initializeEverything() throws SQLException, ParseException, IOException {

        currentProjectList.prefWidthProperty().bind(scrollPane.prefWidthProperty());
        currentProjectList.prefWidthProperty().bind(scrollPane.widthProperty());
        DatabaseManager manager = new DatabaseManager();
        this.wholeResultSet = manager.executeQuery("SELECT * FROM Balance;");
        String[] tempArray;
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        while(wholeResultSet.next()){
            //Adding to choice box
            String tableValue = wholeResultSet.getString("EntryDate");
            Date actualDate = new SimpleDateFormat("dd/mm/yyyy").parse(tableValue);
            tempArray = tableValue.split("/", 3);
            if (now.after(actualDate)){
                if (!(yearList.getItems().contains(tempArray[2])))
                    yearList.getItems().add(tempArray[2]);
            }
            //Adding to budget Field
            if (calendar.get(Calendar.YEAR) <= Integer.parseInt(tempArray[2])){
                budgetField.setText(Double.parseDouble(budgetField.getText()) + Double.parseDouble(wholeResultSet.getString("Balance")) + "");
            }
        }
        expensesFIeld.setText(new DatabaseManager().getYearlyExpense() + "");

        //listner for choicebox
        yearList.setOnAction(v->{
            String choice = yearList.getSelectionModel().getSelectedItem().toString();
            try {
                DatabaseManager otherManager = new DatabaseManager();
                ResultSet otherResults = otherManager.executeQuery("SELECT * FROM Balance;");
                XYChart.Series series = new XYChart.Series();
                XYChart.Series otherSeries = new XYChart.Series();
                while (otherResults.next()){
                    String tempValue = otherResults.getString("EntryDate");
                    String[] otherTempArray = tempValue.split("/", 3);
                    if (otherTempArray[2].equals(choice)){
                        series.getData().add(new XYChart.Data(getMonth(otherTempArray[1]), Integer.parseInt(otherResults.getString("Balance"))));
                        otherSeries.getData().add(new XYChart.Data(getMonth(otherTempArray[1]), new DatabaseManager().getMonthlyExpense(otherTempArray[1])));
                    }
                }
                chart.getData().clear();
                chart.getData().add(series);
                chart.getData().add(otherSeries);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        //TO auto set selected on choicebox
        String currentYear = calendar.get(calendar.YEAR) + "";
        while(!yearList.getItems().contains(calendar.get(calendar.YEAR) + "")){
            currentYear = (Integer.parseInt(currentYear) - 1) + "";
        }
        yearList.getSelectionModel().select(currentYear);

        //init current projectthingies
        initCurrentProjects();
    }
    public int getWeek(Date tempDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tempDate);
        return calendar.WEEK_OF_MONTH;
    }

    public void initCurrentProjects() throws SQLException, IOException {
        DatabaseManager manager = new DatabaseManager();
        ResultSet resultSet = manager.executeQuery("SELECT * FROM Projects;");
        while (resultSet.next()){
            if (resultSet.getString("Status").equals("Ongoing")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("titledPaneFormat.fxml"));

                currentProjectList.getChildren().add(loader.load());
                titledPaneFormatController controller = loader.getController();
                controller.setValues(
                        resultSet.getString("ProjectName"),
                        resultSet.getString("ProjectManager"),
                        resultSet.getString("ProjectType"),
                        resultSet.getString("DateStarted"),
                        resultSet.getString("ActualCost")
                );
                controller.titledPane.prefWidthProperty().bind(currentProjectList.prefWidthProperty());
            }
        }
    }


    public String getMonth(String thing){
        switch (Integer.parseInt(thing)){
            case 1:
                return "Januaru";
            case 2:
                return  "Februrary";
            case 3:
                return  "March";
            case 4:
                return  "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "Novermber";
            case 12:
                return "December";
        }
        return "A";
    }
}
