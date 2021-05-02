package sample;

import java.sql.*;

public class DatabaseManager {

    final private Connection connection;

    public DatabaseManager() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:ucanaccess://src/db.accdb;");
    }
    public ResultSet executeQuery(String qry) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(qry);
        return preparedStatement.executeQuery();
    }
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
}
