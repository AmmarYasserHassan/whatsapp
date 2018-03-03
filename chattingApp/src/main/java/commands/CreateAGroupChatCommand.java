package commands;

import database.DBHandler;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateAGroupChatCommand {
    DBHandler dbHandler;
    String userNumber;
    String groupChatName;
    String groupDisplayPicture;

    /**
     * Constructor

     *
     * @param dbHandler
     * @param userNumber
     * @param groupChatName
     * @param groupDisplayPicture
     *
     */

    public CreateAGroupChatCommand(DBHandler dbHandler, String userNumber, String groupChatName, String groupDisplayPicture) {
        this.dbHandler = dbHandler;
        this.userNumber = userNumber;
        this.groupChatName = groupChatName;
        this.groupDisplayPicture = groupDisplayPicture;
    }




    /**
     * Execute the create group chat command
     * Create a group chat with the given name and display picture, add the usernumber to it's members and make the number an admin
     * @return Result Set
     * @throws SQLException
     */
    public ResultSet execute() throws SQLException {
        String create_group_chat= "SELECT create_gorup_chat(" + "'" + groupChatName + "'" + ", " + "'" + groupDisplayPicture + "'"+ ", " + "'" + userNumber + "'" + ");";

        return this.dbHandler.executeSQLQuery(create_group_chat);
    }
}
