package commands;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.JsonObject;
import database.DBBroker;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterUserCommand implements Command{

    DBBroker dbBroker;
    String userNumber;
    String displayName;
    String display_picture;
    String user_status;
    String verification_code;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */


    public RegisterUserCommand(DBBroker dbBroker, JsonObject request) {
        super();
        this.dbBroker = dbBroker;
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
                    .withClaim("scope", "USER")
                    .withIssuer("USER:"+userNumber)
                    .sign(algorithm);
            // End create JWT

            // Execute the sql statement
            String insert_name = "SELECT insert_user(" + "'" + userNumber + "'" + ", " + "'" + displayName + "'" + ", "+
                    "'" + display_picture + "'" + ", " + "'" + user_status + "'" + ", " + "'" + verification_code + "'" + ");";

            //end of reg

            return this.dbBroker.executeSQLQuery(insert_name);
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
    //begin testing block
/*
    public static void testRegister(){
        DBBroker dbBroker = new DBBroker();
        JsonObject request = new JsonObject();
        request.addProperty("userNumber", "0100000000113");
        request.addProperty("displayName", "testUser");
        request.addProperty("display_picture", "familiarFace");
        request.addProperty("user_status", "test case passed");

        RegisterUserCommand register = new RegisterUserCommand(dbBroker, request);
        JSONObject res = register.execute();
        System.out.println(res);

    }

    public static void main(String[] args) {
        testRegister();
    }
*/
//end testing block

}
