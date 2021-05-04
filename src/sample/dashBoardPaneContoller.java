package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

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
    @FXML Accordion currentProjectList;

    public ResultSet wholeResultSet;

    public void initializeEverything() throws SQLException, ParseException, IOException {
        DatabaseManager manager = new DatabaseManager();
        this.wholeResultSet = manager.executeQuery("SELECT * FROM Balance;");
        String[] tempArray;
        while(wholeResultSet.next()){
            //Adding to choice box
            String tableValue = wholeResultSet.getString("EntryDate");
            Date actualDate = new SimpleDateFormat("dd/mm/yyyy").parse(tableValue);
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            tempArray = tableValue.split("/", 3);
            if (now.after(actualDate)){
                if (!(yearList.getItems().contains(tempArray[2])))
                    yearList.getItems().add(tempArray[2]);
            }

            //Adding to budget Field
            if (calendar.get(Calendar.YEAR) <= Integer.parseInt(tempArray[2])){
                budgetField.setText(Double.parseDouble(budgetField.getText()) + Double.parseDouble(wholeResultSet.getString("Balance")) + "");
            }
            if (calendar.get(Calendar.YEAR) <= Integer.parseInt(tempArray[2])){
                expensesFIeld.setText(Double.parseDouble(expensesFIeld.getText()) + Double.parseDouble(wholeResultSet.getString("Expenses")) + "");
            }
            budgetField.editableProperty().set(false);
            expensesFIeld.editableProperty().set(false);
        }
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
                currentProjectList.getPanes().add(loader.load());
                titledPaneFormatController controller = loader.getController();
                controller.setValues(
                        resultSet.getString("ProjectName"),
                        resultSet.getString("ProjectManager"),
                        resultSet.getString("DateStarted"),
                        resultSet.getString("EstimatedCost"),
                        resultSet.getString("EstimatedEndDate"),
                        resultSet.getString("Status")
                );
            }
        }
    }
}
