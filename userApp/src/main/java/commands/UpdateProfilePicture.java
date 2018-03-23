package commands;

import com.google.gson.JsonObject;
import database.DBBroker;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateProfilePicture {
    DBBroker dbBroker;
    String userNumber;
    String profilePic;


    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */
    public UpdateProfilePicture(DBBroker dbBroker,JsonObject request) {
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();
        this.profilePic = request.get("profilePic").getAsString();
    }
    /**
     * Execute the update command
     * @return JSONObject query result
     */
    public JSONObject execute(){
        String query = "UPDATE users SET display_picture ="+"'"+profilePic+"'"+"WHERE mobile_number LIKE"+"'"+userNumber+"'";
        try {
            return this.dbBroker.executeSQLQuery(query);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

