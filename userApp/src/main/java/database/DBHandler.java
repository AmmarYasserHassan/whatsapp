package database;


import commands.UpdateUserName;
import commands.UpdateUserStory;
import java.sql.ResultSet;

public class DBHandler {
    DBConnection dbConnection;

    /**
     * DbHandler constructor
     */
    public DBHandler(){

        // Connect to db
        dbConnection = new DBConnection();
        dbConnection.connect();
    }

    public void updateUserName(String userNumber, String updatedName){
        ResultSet result = (new UpdateUserName(dbConnection, userNumber, updatedName)).execute();
        System.out.println("Updated name Result : " + result.toString());
    }

    public void updateUserStatus(String userNumber, String updatedStatus){
        ResultSet result = (new UpdateUserName(dbConnection, userNumber, updatedStatus)).execute();
        System.out.println("Updated status Result : " + result.toString());
    }
}
