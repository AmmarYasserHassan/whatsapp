package commands;


import database.DBBroker;
import com.google.gson.JsonObject;

import java.sql.SQLException;


import org.json.JSONObject;

public class AddAdminsToAGroupChatCommand implements Command {

    DBBroker dbBroker;
    String adminUserNumber;
    String numberOfMemberToBeMadeAdmin;
    int groupChatId;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */

    public AddAdminsToAGroupChatCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
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
        return this.dbBroker.executeSQLQuery(add_admin_to_a_group_chat);
    }

}
