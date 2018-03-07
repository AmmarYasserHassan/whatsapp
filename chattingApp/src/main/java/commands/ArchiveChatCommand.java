package commands;


import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

public class ArchiveChatCommand implements Command{

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
    public ArchiveChatCommand(DBHandler dbHandler, String userNumber, int chatId, boolean isGroupChat)
    {
        this.dbHandler = dbHandler;
        this.userNumber = userNumber;
        this.chatId = chatId;
        this.isGroupChat = isGroupChat;
    }

    /**
     * Execute the archive chat ommand
     * check if it's a group chat or a normal chat then enter the table and archive it
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String archive_chat;
        if (isGroupChat)
            archive_chat= "SELECT archive_group_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'"+ ");";
        else
            archive_chat= "SELECT archive_chat(" + "'" + userNumber + "'" + ", " + "'" + chatId + "'"+ ");";

        return this.dbHandler.executeSQLQuery(archive_chat);
    }
}
