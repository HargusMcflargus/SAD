package sample;

import com.healthmarketscience.jackcess.expr.ParseException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseManager {

    //private connection property
    final private Connection connection;

    //Initialize datebase connection
    public DatabaseManager() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:ucanaccess://src/db.accdb;");
    }

    //Executing non-query statements
    public ResultSet executeQuery(String qry) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(qry);
        return preparedStatement.executeQuery();
    }

    //Executing non-query statements with parameters
    public boolean executeStatement(String statement, String[] values) throws SQLException {
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(statement);
            int i = 1;
            for(String temp: values){
                preparedStatement.setString(i, temp);
                i++;
            }
            preparedStatement.execute();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //verify user existence through username and password
    public boolean verifyUser(String username, String password) throws Exception{
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM Users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            if(username.equals(resultSet.getString("username")) && password.equals(resultSet.getString("password"))){
                return true;
            }
        }
        return false;
    }

    //verify existence of user through username
    public boolean verifyUser(String username) throws Exception{
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM Users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            if(username.equals(resultSet.getString("username"))){
                return true;
            }
        }
        return false;
    }

    //Verify existence of user through firstname and lastname matches
    public boolean verifyName(String firstName, String lastName) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM Users WHERE username like ? AND lastname like ?;");
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return true;
        }
        return false;
    }

    //Verifies for admin
    public boolean isAdmin(String usename, String password){
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM Users;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                if (resultSet.getString("firstname") == null){
                    return true;
                }
            }
        }catch (SQLException e){
        }
        return  false;
    }

    //get current yearly expenses
    public double getYearlyExpense(){
        double answer = 0.0;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM Projects");
            ResultSet resultSet = preparedStatement.executeQuery();
            Date now = new Date();
            while (resultSet.next()){
                Date otherDate = new SimpleDateFormat("dd/mm/yyyy").parse(resultSet.getString("DateStarted"));
                if (now.after(otherDate)){
                    answer += Double.parseDouble(resultSet.getString("ActualCost"));
                }
            }
        } catch (SQLException | java.text.ParseException throwables) {
            throwables.printStackTrace();
        }
        return answer;
    }

    //get expenses per month
    public double getMonthlyExpense(String month){
        double a = 0;
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM Projects");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String date = resultSet.getString("DateStarted").split("/", 3)[1];
                if (date.equals(month)){
                    a += Double.parseDouble(resultSet.getString("ActualCost"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return a;
    }
}
