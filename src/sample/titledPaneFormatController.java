package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class titledPaneFormatController {
    @FXML TitledPane titledPane;
    @FXML TextField contractor;
    @FXML TextField dateStart;
    @FXML TextField cost;
    @FXML TextField end;
    @FXML TextField status;

    public void setValues(String title, String contractorName, String dateStartString, String costString, String endString, String statusString){
        titledPane.setText(title);
        contractor.setText(contractorName);
        contractor.setEditable(false);

        dateStart.setText(dateStartString);
        dateStart.setEditable(false);

        cost.setText(costString);
        cost.setEditable(false);

        end.setText(endString);
        end.setEditable(false);

        status.setText(statusString);
        status.setEditable(false);
    }
}
