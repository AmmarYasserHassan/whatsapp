package commands;

import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;
import sender.MqttSender;

public class GetAllChatsForAUserCommand implements Command, Runnable {

    DBHandler dbHandler;
    String userNumber;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */

    public GetAllChatsForAUserCommand(DBHandler dbHandler, JsonObject request) {
        super();
        this.dbHandler = dbHandler;
        this.userNumber = request.get("isGroupChat").getAsString();
    }


    /**
     * Execute the get all my chats command
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String get_chats = "SELECT get_chats(" + "'" + userNumber + "'" + ");";
        return this.dbHandler.executeSQLQuery(get_chats);
    }

    public void run() {
        JSONObject res = this.execute();
        try {
            MqttSender sender = new MqttSender();
            sender.send(res);
            sender.close();
        } catch (Exception e) {

        }
    }
}

