package commands;

import java.io.UnsupportedEncodingException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import database.DBBroker;
import org.json.JSONException;
import org.json.JSONObject;

public class ValidateTokenCommand implements Command {

    String userNumber;
    DBBroker dbBroker;
    String token;

    /**
     * Constructor
     * @param request
     */

    public ValidateTokenCommand(DBBroker dbBroker, JsonObject request) {
        super();
        this.userNumber = request.get("userNumber").getAsString();
        this.token = request.get("jwt").getAsString();
        this.dbBroker = dbBroker;
    }


    @Override
    public JSONObject call() throws Exception {
        JSONObject res = new JSONObject();
        boolean valid = true;
        boolean error = false;
        try {

            // Start validate JWT
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("USER:" + userNumber).withClaim("scope", "USER")
                    .withClaim("userNumber", userNumber).build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            // End validate JWT

        } catch (JSONException e) {
            error = true;
            e.printStackTrace();

        } catch (UnsupportedEncodingException exception) {
            error = true;
            //UTF-8 encoding not supported
            exception.printStackTrace();

        } catch (JWTVerificationException exception) {
            valid = false;
        }

        res.put("valid", valid);
        res.put("error", error);
        return res;
    }


    //begin testing block
    /*
    public static void validateToken() {
        JsonObject request = new JsonObject();
        //for the fail test case change the user number or the token 
        request.addProperty("userNumber", "01000000001");
        request.addProperty("jwt",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzY29wZSI6IlVTRVIiLCJpc3MiOiJVU0VSOjAxMDAwMDAwMDAxIiwidXNlck51bWJlciI6IjAxMDAwMDAwMDAxIn0.IWpLuHkcUfPCW2dckGm2fYkxatrYgwRm0ybjFC_fxbo");
        ValidateTokenCommand validate = new ValidateTokenCommand(new DBBroker,request);
        JSONObject res = validate.execute();
        System.out.println(res);
    }

    public static void main(String[] args) {
        validateToken();
    }
    */
    //end testing block

}
