package database;

import commands.BlockCommand;
import commands.UnBlockCommand;
import java.sql.ResultSet;

public class DBHandler {
    PostgresConnection dbConnection;

    /**
     * DbHandler constructor
     */
    public DBHandler(){
        // DB info
        String jdbcUrl = "jdbc:postgresql://localhost:5432/whatsapp";
        String username = "postgres";
        String password = "admin";

        // Connect to db
        dbConnection = new PostgresConnection(jdbcUrl, username, password);
        dbConnection.connect();
    }

    public void block(String blockerNumber, String blockedNumber){
        ResultSet result = (new BlockCommand(dbConnection, blockerNumber, blockedNumber)).execute();
        System.out.println("Block Result : " + result.toString());
    }

    public void unblock(String blockerNumber, String blockedNumber){
        ResultSet result = (new UnBlockCommand(dbConnection, blockerNumber, blockedNumber)).execute();
        System.out.println("Unblock Result : " + result.toString());
    }

    public static void main(String [] args){
        DBHandler handler = new DBHandler();
        // handler.block("01000000003", "01000000001");
        // handler.unblock("01000000003", "01000000001");
    }

}
