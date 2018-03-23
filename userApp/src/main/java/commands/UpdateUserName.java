package commands;

import com.google.gson.JsonObject;
import database.DBBroker;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateUserName {
    DBBroker dbBroker;
    String userNumber;
    String displayName;


    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */
    public UpdateUserName(DBBroker dbBroker,JsonObject request) {
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();
        this.displayName = request.get("displayName").getAsString();
    }
        /**
         * Execute the update command
         * @return JSONObject query result
         */
        public JSONObject execute(){
            String query = "UPDATE users SET (display_name) ="+"("+"'"+displayName+"'"+")"+"WHERE mobile_number LIKE"+"'"+userNumber+"'";
            try {
                return this.dbBroker.executeSQLQuery(query);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

