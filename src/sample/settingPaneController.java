package sample;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class settingPaneController {
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
    @FXML DatePicker temp;

    private int selection = -1;

    public void init(){
        loadFeedBack();
        loadUserControl();
        LocalDate now = LocalDate.now();
        LocalDate maxDate = LocalDate.MAX;
        temp.setDayCellFactory(v -> new DateCell(){
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate) || item.isBefore(now));
            }}
        );
    }

    public void loadFeedBack(){
        hBoxGeneral.prefWidthProperty().bind(scrollPaneGeneral.prefWidthProperty());
        hBoxGeneral.prefWidthProperty().bind(scrollPaneGeneral.widthProperty());
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Feedback;");
            while (resultSet.next()){

                //Date TExt
                Text dateStamp = new Text(resultSet.getString("TimeStamp"));
                dateStamp.setOnMouseEntered(mouseEvent -> {
                    int index = dateStamp.getParent().getChildrenUnmodifiable().indexOf(dateStamp);
                    highLight(index);
                });
                dateStamp.setOnMouseExited(mouseEvent -> {
                    int index = dateStamp.getParent().getChildrenUnmodifiable().indexOf(dateStamp);
                    unHighLight(index);
                });
                dateColumn.getChildren().add(dateStamp);

                //User Text
                Text userText = new Text(resultSet.getString("UserName"));
                userText.setOnMouseEntered(mouseEvent -> {
                    int index = userText.getParent().getChildrenUnmodifiable().indexOf(userText);
                    highLight(index);
                });
                userText.setOnMouseExited(mouseEvent -> {
                    int index = userText.getParent().getChildrenUnmodifiable().indexOf(userText);
                    unHighLight(index);
                });
                userColumn.getChildren().add(userText);

                //Text
                Text commentText = new Text(resultSet.getString("Comment"));
                commentText.setOnMouseEntered(mouseEvent -> {
                    int index = commentText.getParent().getChildrenUnmodifiable().indexOf(commentText);
                    highLight(index);
                });
                commentText.setOnMouseExited(mouseEvent -> {
                    int index = commentText.getParent().getChildrenUnmodifiable().indexOf(commentText);
                    unHighLight(index);
                });
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
                    //Date TExt
                    Text dateStamp = new Text(resultSet.getString("TimeStamp"));
                    dateStamp.setOnMouseEntered(mouseEvent -> {
                        int index = dateStamp.getParent().getChildrenUnmodifiable().indexOf(dateStamp);
                        highLight(index);
                    });
                    dateStamp.setOnMouseExited(mouseEvent -> {
                        int index = dateStamp.getParent().getChildrenUnmodifiable().indexOf(dateStamp);
                        unHighLight(index);
                    });
                    dateColumn.getChildren().add(dateStamp);

                    //User Text
                    Text userText = new Text(resultSet.getString("UserName"));
                    userText.setOnMouseEntered(mouseEvent -> {
                        int index = userText.getParent().getChildrenUnmodifiable().indexOf(userText);
                        highLight(index);
                    });
                    userText.setOnMouseExited(mouseEvent -> {
                        int index = userText.getParent().getChildrenUnmodifiable().indexOf(userText);
                        unHighLight(index);
                    });
                    userColumn.getChildren().add(userText);

                    //Text
                    Text commentText = new Text(resultSet.getString("Comment"));
                    commentText.setOnMouseEntered(mouseEvent -> {
                        int index = commentText.getParent().getChildrenUnmodifiable().indexOf(commentText);
                        highLight(index);
                    });
                    commentText.setOnMouseExited(mouseEvent -> {
                        int index = commentText.getParent().getChildrenUnmodifiable().indexOf(commentText);
                        unHighLight(index);
                    });
                    textColumn.getChildren().add(commentText);
                }
            }
            userColumn.getChildren().add(new Text("End of Results"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void highLight(int i){
        Text text = (Text)dateColumn.getChildren().get(i);
        text.setFill(Color.NAVY);

        text = (Text)userColumn.getChildren().get(i);
        text.setFill(Color.NAVY);

        text = (Text)textColumn.getChildren().get(i);
        text.setFill(Color.NAVY);
    }
    public void unHighLight(int i){
        Text text = (Text)dateColumn.getChildren().get(i);
        text.setFill(Color.BLACK);

        text = (Text)userColumn.getChildren().get(i);
        text.setFill(Color.BLACK);

        text = (Text)textColumn.getChildren().get(i);
        text.setFill(Color.BLACK);
    }

    public void loadUserControl(){
        vBoxUser.prefWidthProperty().bind(scrollPaneUser.prefWidthProperty());
        vBoxUser.prefWidthProperty().bind(scrollPaneUser.widthProperty());
        try {
            ResultSet resultSet = new DatabaseManager().executeQuery("SELECT * FROM Users;");
            while (resultSet.next()){
                if (resultSet.getString("firstname") == null){
                    Text text = new Text(resultSet.getString("username"));
                    vBoxUser.getChildren().add(text);
                    text.setOnMouseEntered(mouseEvent -> {
                        if (!(text.getFill().equals(Color.RED))){
                            text.underlineProperty().set(true);
                        }
                        text.getScene().setCursor(Cursor.HAND);
                    });
                    text.setOnMouseExited(mouseEvent -> {
                        text.getScene().setCursor(Cursor.DEFAULT);
                        if (!(text.getFill().equals(Color.RED))){
                            text.underlineProperty().set(false);
                        }
                    });
                    text.setOnMouseClicked(mouseEvent -> {
                        try {
                            selection = text.getParent().getChildrenUnmodifiable().indexOf(text);
                            text.setFill(Color.RED);
                            text.underlineProperty().set(false);
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
        System.out.println(selection +"");
        Text temp = (Text) vBoxUser.getChildren().get(selection);
        temp.setFill(Color.BLACK);
        selection = -1;
        usernameField.clear();
        passwordField.clear();
        passwordField2.clear();
        regButton.setDisable(false);
        delButton.setDisable(true);
        edtButton.setDisable(true);
    }
}

