package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.converter.LocalDateStringConverter;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class settingPaneController {

    String tempMoney = "";

    //General Tab
    @FXML VBox dateColumn;
    @FXML VBox userColumn;
    @FXML VBox textColumn;
    @FXML ScrollPane scrollPaneGeneral;
    @FXML HBox hBoxGeneral;
    @FXML TextField textFieldGeneral;
    @FXML ScrollPane scrollPaneUser;
    @FXML VBox vBoxUser;
    @FXML TextField usernameField;
    @FXML TextField passwordField;
    @FXML TextField passwordField2;
    @FXML Button regButton;
    @FXML Button delButton;
    @FXML Button edtButton;
    private int selection = -1;

    //Project Tab
    @FXML VBox projectListContainer;
    @FXML ScrollPane projectListScrollpane;
    @FXML Button addProjectButton;
    @FXML Button editeProjectButton;
    @FXML Button removeProjectButton;
    @FXML TextField projectNameField;
    @FXML TextField projectManField;
    @FXML TextField budgetCostField;
    @FXML DatePicker dateStarted;
    @FXML DatePicker dateEnded;
    @FXML DatePicker EDateEnded;
    @FXML ChoiceBox projectTypeField;
    @FXML ChoiceBox statusField;
    @FXML TextField projectSearchField;
    @FXML ImageView projectImage;
    private int projectSelection = -1;
    private File CurrentFile;

    //BUdget tab
    @FXML ChoiceBox monthList;
    @FXML TextField actualBudget;
    @FXML ScrollPane budgetScroll;
    @FXML VBox budgetVBox;
    @FXML Button addBudgetBtn;
    @FXML Button removeBudgetBtn;
    @FXML Button editBudgetBtn;
    @FXML ChoiceBox yearList;
    @FXML TextField budgetSearch;
    private int budgetSelection = -1;
    private String budgetDate;

    //IMPORTANT
    /*
        LocalDate now = LocalDate.now();
        LocalDate maxDate = LocalDate.MAX;
        temp.setDayCellFactory(v -> new DateCell(){
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate) || item.isBefore(now));
            }}
        );*/

    public void init(){
        selection = -1;
        budgetSelection = -1;
        projectSelection = -1;
        loadFeedBack();
        loadUserControl();
        loadProjectList();
        loadBudgetTab();
    }

    //General Tab
    public void loadFeedBack(){
        hBoxGeneral.prefWidthProperty().bind(scrollPaneGeneral.prefWidthProperty());
        hBoxGeneral.prefWidthProperty().bind(scrollPaneGeneral.widthProperty());
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Feedback;");
            while (resultSet.next()){
                //Date
                TextField dateStamp = new TextField(resultSet.getString("TimeStamp"));
                dateStamp.setAlignment(Pos.CENTER);
                dateStamp.prefWidthProperty().bind(dateColumn.prefWidthProperty());
                dateColumn.getChildren().add(dateStamp);

                //User Text
                TextField userText = new TextField(resultSet.getString("Username"));
                userText.setAlignment(Pos.CENTER);
                userText.prefWidthProperty().bind(userColumn.prefWidthProperty());
                userColumn.getChildren().add(userText);

                //Text
                TextField commentText = new TextField(resultSet.getString("Comment"));
                commentText.setAlignment(Pos.CENTER);
                commentText.prefWidthProperty().bind(textColumn.prefWidthProperty());
                textColumn.getChildren().add(commentText);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void searchGeneral(){
        userColumn.getChildren().clear();
        dateColumn.getChildren().clear();
        textColumn.getChildren().clear();
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Feedback;");
            while (resultSet.next()){
                if (resultSet.getString("TimeStamp").contains(textFieldGeneral.getText())
                    || resultSet.getString("UserName").contains(textFieldGeneral.getText())
                    || resultSet.getString("Comment").contains(textFieldGeneral.getText()))
                {
                    //Date
                    TextField dateStamp = new TextField(resultSet.getString("TimeStamp"));
                    dateStamp.setAlignment(Pos.CENTER);
                    dateStamp.prefWidthProperty().bind(dateColumn.prefWidthProperty());
                    dateColumn.getChildren().add(dateStamp);

                    //User Text
                    TextField userText = new TextField(resultSet.getString("Username"));
                    userText.setAlignment(Pos.CENTER);
                    userText.prefWidthProperty().bind(userColumn.prefWidthProperty());
                    userColumn.getChildren().add(userText);

                    //Text
                    TextField commentText = new TextField(resultSet.getString("Comment"));
                    commentText.setAlignment(Pos.CENTER);
                    commentText.prefWidthProperty().bind(textColumn.prefWidthProperty());
                    textColumn.getChildren().add(commentText);
                }
            }
            userColumn.getChildren().add(new TextField("End of Results"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void loadUserControl(){
        vBoxUser.prefWidthProperty().bind(scrollPaneUser.prefWidthProperty());
        vBoxUser.prefWidthProperty().bind(scrollPaneUser.widthProperty());
        vBoxUser.getChildren().clear();
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Users;");
            while (resultSet.next()){
                if (resultSet.getString("firstname") == null){
                    TextField text = new TextField(resultSet.getString("username"));
                    vBoxUser.getChildren().add(text);
                    text.setAlignment(Pos.CENTER);
                    text.editableProperty().set(false);
                    text.prefWidthProperty().bind(vBoxUser.prefWidthProperty());
                    text.setOnMouseClicked(mouseEvent -> {
                        try {
                            selection = text.getParent().getChildrenUnmodifiable().indexOf(text);
                            text.setStyle("-fx-control-inner-background: #EB8921");
                            delButton.setDisable(false);
                            edtButton.setDisable(false);
                            regButton.setDisable(true);
                            ResultSet newResultSet = new DatabaseManager().executeQuery("SELECT * FROM Users WHERE [username]='" + text.getText() + "'");
                            newResultSet.next();
                            usernameField.setText(newResultSet.getString("username"));
                            passwordField.setText(newResultSet.getString("password"));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void clearSelection(){
        if (selection >= 0){
            TextField temp = (TextField) vBoxUser.getChildren().get(selection);
            temp.setStyle("-fx-control-inner-background: #000000");
        }
        selection = -1;
        usernameField.clear();
        passwordField.clear();
        passwordField2.clear();
        regButton.setDisable(false);
        delButton.setDisable(true);
        edtButton.setDisable(true);
    }
    public void register(){
        try {
            if (new DatabaseManager().verifyUser(usernameField.getText()) || !passwordField.getText().equals(passwordField2.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR, "User " + usernameField.getText() + " already exist \\ Password do not match");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        DatabaseManager manager = new DatabaseManager();
                        if (manager.executeStatement("INSERT INTO Users ([username], [password]) VALUES (?, ?);", new String[]{usernameField.getText(), passwordField.getText()})){
                            Alert newAlert = new Alert(Alert.AlertType.INFORMATION, "User Added");
                            newAlert.showAndWait();
                            loadUserControl();
                            clearSelection();
                            return;
                        }
                    } catch (Exception throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
    public void deleteUser(){
        Text text = (Text)vBoxUser.getChildren().get(selection);
        String name = text.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete user [" +  name + "] ?");
        alert.showAndWait().ifPresent(response ->{
            if (response == ButtonType.OK){
                try{
                    if (new DatabaseManager().executeStatement("DELETE FROM Users WHERE username=?;", new String[]{name})){
                        Alert newAlert = new Alert(Alert.AlertType.INFORMATION, "User [" + name + "] deleted!");
                        newAlert.showAndWait();
                        clearSelection();
                        loadUserControl();
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
    public void editUser(){
        Text text = (Text)vBoxUser.getChildren().get(selection);
        String name = text.getText();
        try{
            if (new DatabaseManager().verifyUser(usernameField.getText(), passwordField.getText()) || !passwordField.getText().equals(passwordField2.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR, "No changes were made");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to update user [" +  name + "] ?");
            alert.showAndWait().ifPresent(response ->{
                if (response == ButtonType.OK){
                    try {
                        if (new DatabaseManager().executeStatement("UPDATE Users SET username=?, password=? WHERE username=?;", new String[]{usernameField.getText(), passwordField.getText(), name})){
                            Alert newAlert = new Alert(Alert.AlertType.INFORMATION, "User [" + name + "] updated");
                            newAlert.showAndWait();
                            clearSelection();
                            loadUserControl();
                            return;
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }}
            );

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Project Tab
    public void loadProjectList(){
        projectListContainer.getChildren().clear();
        projectTypeField.getItems().clear();
        projectTypeField.getItems().addAll(
                "Infrastracture",
                "Health",
                "Education",
                "Calamity",
                "Security"
        );
        statusField.getItems().clear();;
        statusField.getItems().addAll(
                "Ongoing",
                "Finished",
                "Scrapped"
        );
        projectListContainer.prefWidthProperty().bind(projectListScrollpane.prefWidthProperty());
        projectListContainer.prefWidthProperty().bind(projectListScrollpane.widthProperty());
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Projects");
            while (resultSet.next()){
                TextField textField = new TextField(resultSet.getString("ProjectName"));
                projectListContainer.getChildren().add(textField);
                textField.setAlignment(Pos.CENTER);
                textField.prefWidthProperty().bind(projectListContainer.prefWidthProperty());
                textField.prefWidthProperty().bind(projectListContainer.widthProperty());
                textField.setEditable(false);
                textField.setOnMouseClicked(mouseEvent -> {
                    clearOthersProjects();
                    textField.setStyle("-fx-control-inner-background: #EB8921");
                    projectSelection = textField.getParent().getChildrenUnmodifiable().indexOf(textField);
                    try {
                        ResultSet resultSett = new DatabaseManager().executeQuery("SELECT * FROM Projects WHERE ProjectName='" + textField.getText() + "';");
                        resultSett.next();
                        projectNameField.setText(textField.getText());
                        projectManField.setText(resultSett.getString("ProjectManager"));
                        projectTypeField.getSelectionModel().select(resultSett.getString("ProjectType"));
                        dateStarted.setValue(LocalDate.parse(resultSett.getString("DateStarted"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        EDateEnded.setValue(LocalDate.parse(resultSett.getString("EstimatedEndDate"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        budgetCostField.setText("Php " +  fixNumber(resultSett.getString("ActualCost")));
                        tempMoney = resultSett.getString("ActualCost");
                        statusField.getSelectionModel().select(resultSett.getString("Status"));
                        projectImage.setImage(new Image(resultSett.getString("ImagePath")));
                        addProjectButton.setDisable(true);
                        removeProjectButton.setDisable(false);
                        editeProjectButton.setDisable(false);
                    } catch (Exception throwables) {
                        throwables.printStackTrace();
                    }
                });
                textField.setOnMouseEntered(mouseEvent -> {
                    textField.getScene().setCursor(Cursor.HAND);
                });
                textField.setOnMouseExited(mouseEvent -> {
                    textField.getScene().setCursor(Cursor.DEFAULT);
                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public void clearProjects(){
        if (projectSelection >= 0){
            TextField textField = (TextField) projectListContainer.getChildren().get(projectSelection);
            textField.setStyle("-fx-control-inner-background: #FFFFFF");
        }
        projectSelection = -1;
        projectNameField.clear();
        projectTypeField.getSelectionModel().clearSelection();
        projectManField.clear();
        EDateEnded.setValue(null);
        dateStarted.setValue(null);
        dateEnded.setValue(null);
        statusField.getSelectionModel().clearSelection();
        budgetCostField.clear();
        addProjectButton.setDisable(false);
        removeProjectButton.setDisable(true);
        editeProjectButton.setDisable(true);
    }
    public boolean verifyProject(){
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Projects WHERE ProjectName='" + projectNameField.getText() + "'");
            if (resultSet.next())
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (!CurrentFile.exists() || CurrentFile.equals(null)){
            return false;
        }
        if (projectNameField.getText().isEmpty())
            return false;
        if (projectManField.getText().isEmpty())
            return false;
        if (projectTypeField.getSelectionModel().isEmpty())
            return false;
        if (dateStarted.getEditor().getText().isEmpty())
            return false;
        if (EDateEnded.getEditor().getText().isEmpty())
            return false;
        if (budgetCostField.getText().isEmpty())
            return false;
        return true;
    }
    public void addProject(){
        try{
            if (!verifyProject()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Missing Values // Match Found");
                alert.showAndWait();
                return;
            }
            if (CurrentFile.renameTo(new File("src/img/resource/" + projectNameField.getText() + CurrentFile.getName().split(".", 2)[0]))){
                System.out.println(CurrentFile.getPath());
            }
            File file = new File("src/img/resource/" + projectNameField.getText() + CurrentFile.getName().split(".", 2)[0]);
            String[] strings = new String[]{
                    projectNameField.getText(),
                    projectManField.getText(),
                    projectTypeField.getSelectionModel().getSelectedItem().toString(),
                    dateStarted.getEditor().getText(),
                    "",
                    EDateEnded.getEditor().getText(),
                    budgetCostField.getText(),
                    "Ongoing",
                    file.toURI().toString()

            };
            if (new DatabaseManager().executeStatement("INSERT INTO Projects (ProjectName, ProjectManager, ProjectType, DateStarted, DateEnded, EstimatedEndDate, ActualCost, Status, ImagePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);", strings)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Project + [" + projectNameField.getText() +"] added!");
                alert.showAndWait();
                clearProjects();
                loadProjectList();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void clearOthersProjects(){
        for(Node node1: projectListContainer.getChildren()){
            node1.setStyle("-fx-control-inner-background: #FFFFFF");;
        }
    }
    public void editProject(){
        try{
            TextField textField = (TextField) projectListContainer.getChildren().get(projectSelection);
            String name = textField.getText();

            if (!verifyProject()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Missing // Repetitive Values");
                alert.showAndWait();
                return;
            }
            String temp = budgetCostField.getText();
            if (budgetCostField.getText().charAt(0) == 'P')
                temp = tempMoney;
            String[] strings = new String[]{
                    projectNameField.getText(),
                    projectManField.getText(),
                    projectTypeField.getSelectionModel().getSelectedItem().toString(),
                    dateStarted.getEditor().getText(),
                    dateEnded.getEditor().getText(),
                    EDateEnded.getEditor().getText(),
                    temp,
                    statusField.getSelectionModel().getSelectedItem().toString(),
                    name,
                    projectImage.getImage().getUrl().toString()
            };
            if (new DatabaseManager().executeStatement("UPDATE Projects SET ProjectName=?, ProjectManager=?, ProjectType=?, DateStarted=?, DateEnded=?, EstimatedEndDate=?, ActualCost=?, Status=? ImagePath=? WHERE ProjectName=?",strings)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Project added");
                alert.showAndWait();
                loadProjectList();
                clearProjects();
                clearSelection();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void deleteProject(){
        TextField textField = (TextField) projectListContainer.getChildren().get(projectSelection);
        String name = textField.getText();
        if (projectSelection < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No selection found");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure?");
        alert.showAndWait().ifPresent(response ->{
            if (response == ButtonType.OK){
                try{
                    if (new DatabaseManager().executeStatement("DELETE FROM Projects WHERE ProjectName=?", new String[]{name})){
                        Alert newAlert = new Alert(Alert.AlertType.INFORMATION, "Entry Removed");
                        newAlert.showAndWait();
                        loadProjectList();
                        clearProjects();
                        clearSelection();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void setFile(){
        FileChooser chooser = new FileChooser();
        CurrentFile = chooser.showOpenDialog(scrollPaneUser.getScene().getWindow());
        System.out.println(CurrentFile.toURI().toString());
        projectImage.setImage(new Image(CurrentFile.toURI().toString()));
    }
    //TODO FIX THIS
    public void searchProject(){
        projectListContainer.getChildren().clear();
        if (projectSearchField.getText().isEmpty()){
            loadProjectList();
            return;
        }
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Projects");
            while (resultSet.next()){
                TextField textField = new TextField(resultSet.getString("ProjectName"));
                textField.setAlignment(Pos.CENTER);
                projectListContainer.getChildren().add(textField);
                textField.prefWidthProperty().bind(projectListContainer.prefWidthProperty());
                textField.setOnMouseClicked(mouseEvent -> {
                    clearOthersProjects();
                    textField.setStyle("-fx-control-inner-background: #EB8921");
                    projectSelection = textField.getParent().getChildrenUnmodifiable().indexOf(textField);
                    try {
                        ResultSet resultSett = new DatabaseManager().executeQuery("SELECT * FROM Projects WHERE ProjectName='" + textField.getText() + "';");
                        resultSett.next();
                        projectNameField.setText(textField.getText());
                        projectManField.setText(resultSett.getString("ProjectManager"));
                        projectTypeField.getSelectionModel().select(resultSett.getString("ProjectType"));
                        dateStarted.setValue(LocalDate.parse(resultSett.getString("DateStarted"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        EDateEnded.setValue(LocalDate.parse(resultSett.getString("EstimatedEndDate"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        budgetCostField.setText("Php " +  fixNumber(resultSett.getString("ActualCost")));
                        tempMoney = resultSett.getString("ActualCost");
                        statusField.getSelectionModel().select(resultSett.getString("Status"));
                        projectImage.setImage(new Image(resultSett.getString("ImagePath")));
                        addProjectButton.setDisable(true);
                        removeProjectButton.setDisable(false);
                        editeProjectButton.setDisable(false);
                    } catch (Exception throwables) {
                        throwables.printStackTrace();
                    }
                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Budget Tab
    public void loadBudgetTab(){
        Calendar calendar1 = Calendar.getInstance();
        for (int i = calendar1.get(Calendar.YEAR); i < calendar1.get(Calendar.YEAR) + 5; i++){
            yearList.getItems().add(i + "");
        }

        monthList.getItems().addAll(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        );
        budgetVBox.getChildren().clear();
        clearBudget();
        budgetVBox.prefWidthProperty().bind(budgetScroll.prefWidthProperty());
        budgetVBox.prefWidthProperty().bind(budgetScroll.widthProperty());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Balance;");
            while (resultSet.next()){
                String[] dates = resultSet.getString("EntryDate").split("/",3);
                int year = Integer.parseInt(dates[2]);
                if (currentYear <= year){
                    TextField textField = new TextField(getMonth(dates[1]) + "-" + year);
                    budgetVBox.getChildren().add(textField);
                    textField.prefWidthProperty().bind(budgetVBox.prefWidthProperty());
                    textField.editableProperty().set(false);
                    textField.setAlignment(Pos.CENTER);
                    textField.setOnMouseClicked(mouseEvent -> {
                        for(Node node: budgetVBox.getChildren()){
                            node.setStyle("-fx-control-inner-background: #FFFFFF");
                        }
                        budgetSelection = textField.getParent().getChildrenUnmodifiable().indexOf(textField);
                        textField.setStyle("-fx-control-inner-background: #EB8921");
                        addBudgetBtn.setDisable(true);
                        editBudgetBtn.setDisable(false);
                        removeBudgetBtn.setDisable(false);
                        try {
                            ResultSet resultSett = new DatabaseManager().executeQuery("SELECT * FROM Balance");
                            while (resultSett.next()){
                                if (resultSett.getString("EntryDate").contains(getMonth(textField.getText(), true))){
                                    budgetDate = resultSett.getString("EntryDate");
                                    monthList.getSelectionModel().select(textField.getText().split("-", 2)[0]);
                                    yearList.getSelectionModel().select(textField.getText().split("-", 2)[1]);
                                    actualBudget.setText("Php " +  fixNumber(resultSett.getString("Balance")));
                                    tempMoney = resultSett.getString("Balance");
                                    break;
                                }
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    });
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void clearBudget(){
        budgetSelection = -1;
        budgetDate = "";
        actualBudget.clear();
        for(Node node: budgetVBox.getChildren()){
            node.setStyle("-fx-control-inner-background: #FFFFFF");
        }
        monthList.getSelectionModel().clearSelection();
        yearList.getSelectionModel().clearSelection();
        addBudgetBtn.setDisable(false);
        editBudgetBtn.setDisable(true);
        removeBudgetBtn.setDisable(true);
    }
    public String getMonth(String thing){
        switch (Integer.parseInt(thing)){
            case 1:
                return "January";
            case 2:
                return  "February";
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
                return "November";
            case 12:
                return "December";
        }
        return "A";
    }
    public String getMonth(String thing, boolean tree){
        String[] temp = thing.split("-", 2);
        String finalString = "";
        if (temp[0].equals("January"))
            finalString += "01/";
        if (temp[0].equals("February"))
            finalString += "02/";
        if (temp[0].equals("March"))
            finalString += "03/";
        if (temp[0].equals("April"))
            finalString += "04/";
        if (temp[0].equals("May"))
            finalString += "05/";
        if (temp[0].equals("June"))
            finalString += "06/";
        if (temp[0].equals("July"))
            finalString += "07/";
        if (temp[0].equals("August"))
            finalString += "08/";
        if (temp[0].equals("September"))
            finalString += "09/";
        if (temp[0].equals("October"))
            finalString += "10/";
        if (temp[0].equals("November"))
            finalString += "11/";
        if (temp[0].equals("December"))
            finalString += "12/";
        if (temp.length == 1){
            return finalString;
        }
        return finalString + temp[1];
    }
    public void addBudget(){
        try{
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Balance;");
            while(resultSet.next()) {
                if (resultSet.getString("EntryDate").contains(monthList.getSelectionModel().getSelectedItem().toString() + "/" + yearList.getSelectionModel().getSelectedItem().toString())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Entry Already Exists");
                    alert.showAndWait();
                    return;
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you?");
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK){
                    String[] values = new String[]{
                            actualBudget.getText(),
                            "00/" + getMonth(monthList.getSelectionModel().getSelectedItem().toString(), true) +  yearList.getSelectionModel().getSelectedItem().toString()
                    };
                    try {
                        if (new DatabaseManager().executeStatement("INSERT INTO Balance VALUES (?, ?);", values)){
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Entry Added");
                            alert1.showAndWait();
                            loadBudgetTab();
                            clearBudget();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void editBudget(){
        TextField textField = (TextField) budgetVBox.getChildren().get(budgetSelection);
        String monthThing = getMonth(textField.getText(), true);
        System.out.println(monthThing);
        try{
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Balance;");
            while(resultSet.next()) {
                if (resultSet.getString("EntryDate").contains(getMonth(monthList.getSelectionModel().getSelectedItem().toString(), true) + yearList.getSelectionModel().getSelectedItem().toString())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Entry Already Exists");
                    alert.showAndWait();
                    return;
                }
            }
            String temp = actualBudget.getText().charAt(0) == 'P' ? tempMoney : actualBudget.getText();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK){
                    String[]  values = new String[]{
                            "00/" + getMonth(monthList.getSelectionModel().getSelectedItem().toString(), true) + yearList.getSelectionModel().getSelectedItem().toString(),
                            temp,
                            budgetDate
                    };
                    try {
                        if (new DatabaseManager().executeStatement("UPDATE Balance SET EntryDate=?, Balance=? WHERE EntryDate=?;", values)){
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Changes Saved");
                            alert1.showAndWait();
                            loadBudgetTab();
                            clearBudget();
                            return;
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removeBudget(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
        alert.showAndWait().ifPresent(buttonType -> {
            try{
                if (buttonType == ButtonType.OK){
                    if (new DatabaseManager().executeStatement("DELETE FROM Balance WHERE EntryDate=?;", new String[]{budgetDate})){
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Entry Removed");
                        alert1.showAndWait();
                        clearBudget();
                        loadBudgetTab();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    public void searchBudget(){
        if (budgetSearch.getText().isEmpty()){
            loadBudgetTab();
            return;
        }
        try{
            budgetVBox.getChildren().clear();
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Balance;");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int currentYear = calendar.get(Calendar.YEAR);
            while (resultSet.next()){
                if (resultSet.getString("EntryDate").contains(budgetSearch.getText())){
                    String[] dates = resultSet.getString("EntryDate").split("/",3);
                    int year = Integer.parseInt(dates[2]);
                    if (currentYear <= year){
                        TextField textField = new TextField(getMonth(dates[1]) + "-" + (dates[2]));
                        budgetVBox.getChildren().add(textField);
                        textField.prefWidthProperty().bind(budgetVBox.prefWidthProperty());
                        textField.editableProperty().set(false);
                        textField.setAlignment(Pos.CENTER);
                        textField.setOnMouseClicked(mouseEvent -> {
                            for(Node node: budgetVBox.getChildren()){
                                node.setStyle("-fx-control-inner-background: #FFFFFF");
                            }
                            budgetSelection = textField.getParent().getChildrenUnmodifiable().indexOf(textField);
                            textField.setStyle("-fx-control-inner-background: #EB8921");
                            addBudgetBtn.setDisable(true);
                            editBudgetBtn.setDisable(false);
                            removeBudgetBtn.setDisable(false);
                            try {
                                ResultSet resultSett = new DatabaseManager().executeQuery("SELECT * FROM Balance");
                                while (resultSett.next()){
                                    if (resultSett.getString("EntryDate").contains(getMonth(textField.getText(), true))){
                                        budgetDate = resultSett.getString("EntryDate");
                                        monthList.getSelectionModel().select(textField.getText().split("-", 2)[0]);
                                        yearList.getSelectionModel().select(textField.getText().split("-", 2)[1]);
                                        actualBudget.setText("Php " +  fixNumber(resultSett.getString("Balance")));
                                        tempMoney = resultSett.getString("Balance");
                                        break;
                                    }
                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        });
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

