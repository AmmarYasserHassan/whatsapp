package commands;


import database.DBHandler;
import com.google.gson.JsonObject;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.json.JSONObject;
import sender.MqttSender;

public class AddAdminsToAGroupChatCommand implements Command, Runnable {

    DBHandler dbHandler;
    String adminUserNumber;
    String numberOfMemberToBeMadeAdmin;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */

    public AddAdminsToAGroupChatCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
        this.adminUserNumber = request.get("adminUserNumber").getAsString();
        this.numberOfMemberToBeMadeAdmin = request.get("numberOfMemberToBeMadeAdmin").getAsString();
        this.groupChatId = request.get("groupChatId").getAsInt();
    }

    /**
     * Execute the add admins to a group chat command
     * Check first that this adminUsernumber is an admin of this group chat and make the numbers admins
     *
     * @return Result Set
     * @throws SQLException
     */
    public JSONObject execute() {
        String add_admin_to_a_group_chat = "SELECT add_admins_to_a_group_chat(" + "'" + adminUserNumber + "'" + ", " + "'" + groupChatId + "'" + ", " + "'" + numberOfMemberToBeMadeAdmin + "'" + ");";
        return this.dbHandler.executeSQLQuery(add_admin_to_a_group_chat);
    }

    public void run() {
        JSONObject res = this.execute();
        try {
            MqttSender sender = new MqttSender();
            sender.send(res);
            sender.close();
        }catch (Exception e){

        }
    }
}
