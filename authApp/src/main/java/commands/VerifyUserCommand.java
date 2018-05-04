package commands;

import com.google.gson.JsonObject;
import database.DBBroker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerifyUserCommand implements Command {

    DBBroker dbBroker;
    String userNumber;
    String verification_code;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */

    public VerifyUserCommand(DBBroker dbBroker, JsonObject request) {
        super();
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();
        this.verification_code = request.get("verification_code").getAsString();

    }

    @Override
    public JSONObject call() throws Exception {
        JSONObject returned = new JSONObject();

        try {
            String checkOnVerificationCode = "SELECT * from USERS where " +"mobile_number = " + "'" + userNumber + "'"
                    +" and "+ "verification_code = " + "'" + verification_code+ "'";

            JSONObject res = this.dbBroker.executeSQLQuery(checkOnVerificationCode);
            boolean isError = (boolean)res.get("error");
            JSONArray data = (JSONArray) res.get("data");
            if (!isError && data.length() != 0) {
                String verify_User = "SELECT verify_user(" + "'" + userNumber + "'" + ");";
                return this.dbBroker.executeSQLQuery(verify_User);

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        returned.put("error", true);
        return returned;
    }

//begin testing block
/*
    public static void testVerifyUser(){
        DBBroker dbBroker = new DBBroker();
        JsonObject request = new JsonObject();
        request.addProperty("userNumber", "01000000002");
        request.addProperty("verification_code", "231578");

        VerifyUserCommand verify = new VerifyUserCommand(dbBroker, request);
        JSONObject res = verify.execute();
        System.out.println(res);

    }

    public static void main(String[] args) {
        testVerifyUser();
    }
*/
//end testing block

}
