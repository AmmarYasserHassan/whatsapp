package commands;

import java.io.UnsupportedEncodingException;
import java.sql.Statement;
import java.util.Random;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sender.MqttSender;

public class VerifyUserCommand implements Command, Runnable {

    DBHandler dbHandler;
    String userNumber;
    String verification_code;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */

    public VerifyUserCommand(DBHandler dbHandler, JsonObject request) {
        super();
        this.dbHandler = dbHandler;
        this.userNumber = request.get("userNumber").getAsString();
        this.verification_code = request.get("verification_code").getAsString();

    }

    public JSONObject execute() {
        JSONObject returned = new JSONObject();

        try {
            String checkOnVerificationCode = "SELECT * from USERS where " +"mobile_number = " + "'" + userNumber + "'" 
            +" and "+ "verification_code = " + "'" + verification_code+ "'";

            JSONObject res = this.dbHandler.executeSQLQuery(checkOnVerificationCode);
            boolean isError = (boolean)res.get("error");
            JSONArray data = (JSONArray) res.get("data");
            if (!isError && data.length() != 0) {
                String verify_User = "SELECT verify_user(" + "'" + userNumber + "'" + ");";
                return this.dbHandler.executeSQLQuery(verify_User);

            }
           
        } catch (JSONException e) {
            e.printStackTrace();

        }
        returned.put("error", true);
        return returned;
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

//begin testing block
/*
    public static void testVerifyUser(){
        DBHandler dbHandler = new DBHandler();
        JsonObject request = new JsonObject();
        request.addProperty("userNumber", "01000000002");
        request.addProperty("verification_code", "231578");

        VerifyUserCommand verify = new VerifyUserCommand(dbHandler, request);
        JSONObject res = verify.execute();
        System.out.println(res);

    }

    public static void main(String[] args) {
        testVerifyUser();
    }
*/
//end testing block

}
