package commands;

import database.DBHandler;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteChatCommand {
    DBHandler dbHandler;
    String userNumber;
    int chatId;
    boolean isGroupChat;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param userNumber
     * @param chatId
     * @param isGroupChat
     *
     */
    public DeleteChatCommand(DBHandler dbHandler, String userNumber, int chatId, boolean isGroupChat)
    {
        this.dbHandler = dbHandler;
        this.userNumber = userNumber;
        this.chatId = chatId;
        this.isGroupChat = isGroupChat;
    }

    /**
     * Execute the delete chat ommand
     * check if it's a group chat or a normal chat then enter the table and delete it
     * @return Result Set
     * @throws SQLException
     */
    public ResultSet execute() throws SQLException {
        String delete_chat;
        if (isGroupChat)
            delete_chat= "SELECT delete_group_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'"+ ");";
        else
            delete_chat= "SELECT delete_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'"+ ");";

        return this.dbHandler.executeSQLQuery(delete_chat);
    }
}
