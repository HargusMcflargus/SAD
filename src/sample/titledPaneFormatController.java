package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class titledPaneFormatController{
    @FXML TitledPane titledPane;
    @FXML TextField contractor;
    @FXML TextField dateStart;
    @FXML TextField cost;
    @FXML TextField duration;
    @FXML TextField type;

    public void setValues(String title, String contractorName,  String projectType, String dateStartString, String costString){
        titledPane.setText(title);
        contractor.setText(contractorName);

        dateStart.setText(dateStartString);

        cost.setText(fixNumber(costString));

        String actualDuration = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        actualDuration = find(dateStartString, sdf.format(new Date()));

        duration.setText(actualDuration);

        type.setText(projectType);
    }

    public String find(String join_date, String leave_date)
    {
        // Create an instance of the SimpleDateFormat class
        SimpleDateFormat obj = new SimpleDateFormat("dd/mm/yyyy");
        // In the try block, we will try to find the difference
        try {
            // Use parse method to get date object of both dates
            Date date1 = obj.parse(join_date);
            Date date2 = obj.parse(leave_date);
            // Calucalte time difference in milliseconds
            long time_difference = date2.getTime() - date1.getTime();
            // Calucalte time difference in days
            long days_difference = (time_difference / (1000*60*60*24)) % 365;
            // Calucalte time difference in years
            long years_difference = (time_difference / (1000l*60*60*24*365));
            return days_difference + "D " + years_difference + "Y";
        }
        // Catch parse exception
        catch (ParseException excep) {
            excep.printStackTrace();
        }
        return null;
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
