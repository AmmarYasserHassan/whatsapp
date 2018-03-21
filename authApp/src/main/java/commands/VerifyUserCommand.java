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

import org.json.JSONException;
import org.json.JSONObject;
import sender.MqttSender;

public class VerifyUserCommand  implements Command, Runnable {

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
        this.verification_code=request.get("verification_code").getAsString();



    }





    public JSONObject execute() {

        try {
            String checkOnVerificationCode = "SELECT * from USERS where verification_code = "+"'"+verification_code+"'";

           String isError;
            isError = (String) this.dbHandler.executeSQLQuery(checkOnVerificationCode).get("error");

            if(isError.equals("false")) {
               String verify_User = "SELECT verify_user(" + "'" + userNumber + "'" + ");";

               return this.dbHandler.executeSQLQuery(verify_User);

           }
        }
        catch (JSONException e) {
            e.printStackTrace();

        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.

        }

        return null;
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
