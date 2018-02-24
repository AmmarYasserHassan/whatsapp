package database;

import commands.BlockCommand;
import commands.UnBlockCommand;

public class DBHandler {
    PostgresConnection dbConnection;

    /**
     * DbHandler constructor
     */
    public DBHandler(){
        // DB info
        String jdbcUrl = "jdbc:postgresql://localhost:5432/whatsapp";
        String username = "postgres";
        String password = "Gladiator2222";

        // Connect to db
        dbConnection = new PostgresConnection(jdbcUrl, username, password);
        dbConnection.connect();
    }

    public void block(String blockerNumber, String blockedNumber){
        (new BlockCommand(dbConnection, blockerNumber, blockedNumber)).execute();
    }

    public void unblock(String blockerNumber, String blockedNumber){
        (new UnBlockCommand(dbConnection, blockerNumber, blockedNumber)).execute();
    }

    public static void main(String [] args){
        DBHandler handler = new DBHandler();
        handler.block("01000000003", "01000000001");
        handler.unblock("01000000003", "01000000001");
    }

}
