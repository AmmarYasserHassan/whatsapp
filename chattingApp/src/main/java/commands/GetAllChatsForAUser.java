package commands;

import database.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetAllChatsForAUser {

        DBHandler dbHandler;
        String userNumber;

        /**
         * Constructor
         *
         * @param dbHandler
         * @param userNumber
         */

        /**
         * Update User name constructor
         * @param dbHandler
         * @param userNumber
         */
        public GetAllChatsForAUser(DBHandler dbHandler, String userNumber) {
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
        public ResultSet execute() throws SQLException {
            String get_chats = "SELECT get_chats(" + "'" + userNumber + "'"+ ");";
            return this.dbHandler.executeSQLQuery(get_chats);
        }
    }

