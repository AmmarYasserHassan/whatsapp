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

public class RegisterUserCommand implements Command, Runnable{

    DBHandler dbHandler;
    String userNumber;
    String displayName;
    String display_picture;
    String user_status;
    String verification_code;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */


    public RegisterUserCommand(DBHandler dbHandler, JsonObject request) {
        super();
        this.dbHandler = dbHandler;
        this.userNumber = request.get("userNumber").getAsString();
        this.displayName = request.get("displayName").getAsString();
        this.display_picture = request.get("display_picture").getAsString();
        this.user_status = request.get("user_status").getAsString();
        this.verification_code =generateRandom();



    }


    protected String generateRandom() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    /**
     * Execute the update command
     *
     * @return Result Set
     * @throws SQLException
     */



    public JSONObject execute() {

         try {


            // Start create JWT
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withClaim("userNumber", userNumber)
                    .withIssuer(displayName)
                    .sign(algorithm);
            // End create JWT


            // Execute the sql statement
            String insert_name = "SELECT insert_user(" + "'" + userNumber + "'" + ", " + "'" + displayName + "'" +
                    "'" + display_picture + "'" + ", " + "'" + user_status + "'" + ", " + "'" + verification_code + "'" + ");";



            //end of reg




            return this.dbHandler.executeSQLQuery(insert_name);
        }
        catch (JSONException e) {
            e.printStackTrace();

        } catch (UnsupportedEncodingException exception) {
            //UTF-8 encoding not supported

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
