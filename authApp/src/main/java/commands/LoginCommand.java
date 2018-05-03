package commands;

import java.io.UnsupportedEncodingException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.JsonObject;
import database.DBBroker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginCommand implements Command {
    DBBroker dbBroker;
    String userNumber;
    String displayName;
    String token;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */

    public LoginCommand(DBBroker dbBroker, JsonObject request) {
        super();
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();

    }

    @Override
    public JSONObject call() throws Exception {
        JSONObject returned = new JSONObject();
        try {

            // Start create JWT
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create().withClaim("userNumber", userNumber).withClaim("scope", "USER")
                    .withIssuer("USER:" + userNumber).sign(algorithm);
            // End create JWT

            // Execute the sql statement
            String checkOnIsUserFound = "SELECT * from USERS where mobile_number = " + "'" + userNumber + "'";

            //end of reg

            JSONObject checkIfUserFound = this.dbBroker.executeSQLQuery(checkOnIsUserFound);
            boolean isError = (boolean) checkIfUserFound.get("error");
            JSONArray data = (JSONArray) checkIfUserFound.get("data");
            if (!isError && data.length() !=0) {
                returned.put("jwt", token);

            } else {

                return checkIfUserFound;
            }

        } catch (JSONException e) {
            e.printStackTrace();

        } catch (UnsupportedEncodingException exception) {
            //UTF-8 encoding not supported

        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.

        }

        return returned;
    }


//begin testing block
/*
    public static void testLoginSucc(){
        DBBroker dbBroker = new DBBroker();
        JsonObject request = new JsonObject();
        request.addProperty("userNumber", "01000000001");
        LoginCommand login = new LoginCommand(dbBroker, request);
        JSONObject res = login.execute();
        System.out.println(res);

    }
    public static void testLoginFail(){
        DBBroker dbBroker = new DBBroker();
        JsonObject request = new JsonObject();
        request.addProperty("userNumber", "010000000012334");
        LoginCommand login = new LoginCommand(dbBroker, request);
        JSONObject res = login.execute();
        System.out.println(res);

    }

    public static void main(String[] args) {
        testLoginSucc();
        testLoginFail();
    }
*/
//end testing block

}
