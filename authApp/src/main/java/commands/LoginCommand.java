package commands;

import java.io.UnsupportedEncodingException;
import java.sql.Statement;
import java.util.Random;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import database.DBHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import sender.MqttSender;

public class LoginCommand implements Command, Runnable{
    DBHandler dbHandler;
    String userNumber;
    String displayName;
    String token ;

    /**
     * Constructor
     *
     * @param dbHandler
     * @param request
     */


    public LoginCommand(DBHandler dbHandler, JsonObject request)  {
        super();
        this.dbHandler = dbHandler;
        this.userNumber = request.get("userNumber").getAsString();





    }


    public JSONObject execute() {
        JSONObject  returned= new JSONObject();
        try {



            // Start create JWT
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withClaim("userNumber", userNumber)
                    .withClaim("scope", "USER")
                    .withIssuer("USER:"+userNumber)
                    .sign(algorithm);
            // End create JWT


            // Execute the sql statement
            String checkOnIsUserFound = "SELECT * from USERS where mobile_number = "+"'"+userNumber+"'";



            //end of reg




            JSONObject checkIfUserFound=   this.dbHandler.executeSQLQuery(checkOnIsUserFound);
         String isError =(String)checkIfUserFound.get("error");

         if(isError.equals("false")){
             returned.put("jwt",token);


         }else {

             return checkIfUserFound;
         }

        }
        catch (JSONException e) {
            e.printStackTrace();

        } catch (UnsupportedEncodingException exception) {
            //UTF-8 encoding not supported

        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.

        }

        return returned;
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
