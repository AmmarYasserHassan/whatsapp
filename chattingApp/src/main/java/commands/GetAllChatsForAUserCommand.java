package commands;

import database.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

public class GetAllChatsForAUserCommand implements Command{

        DBHandler dbHandler;
        String userNumber;

        /**
         * Constructor
         *
         * @param dbHandler
         * @param userNumber
         */

        public GetAllChatsForAUserCommand(DBHandler dbHandler, String userNumber) {
            super();
            this.dbHandler = dbHandler;
            this.userNumber = userNumber;
        }


        /**
         * Execute the get all my chats command
         *
         * @return Result Set
         * @throws SQLException
         */
        public JSONObject execute() {
            String get_chats = "SELECT get_chats(" + "'" + userNumber + "'"+ ");";
            return this.dbHandler.executeSQLQuery(get_chats);
        }
    }

