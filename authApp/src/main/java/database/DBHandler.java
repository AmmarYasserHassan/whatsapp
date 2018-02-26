package database;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler {

    PostgresConnection postgresConnection;

    /**
     * DbHandler constructor
     */
    public DBHandler(PostgresConnection postgresConnection) {
        this.postgresConnection = postgresConnection;
    }

    /**
     * Update user anme query
     *
     * @param userNumber
     * @param displayName
     * @return ResultSet containing the new user details
     * @throws SQLException
     */


    public String RegisterUser(String userNumber, String displayName
            , String display_picture, String user_status, String verification_code) throws SQLException {
        try {


            // Start create JWT
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withClaim("userNumber", userNumber)
                    .withIssuer(displayName)
                    .sign(algorithm);
            // End create JWT


            // Execute the sql statement
            Statement statement = this.postgresConnection.getConn().createStatement();

            String insert_name = "SELECT insert_user(" + "'" + userNumber + "'" + ", " + "'" + displayName + "'" +
                    "'" + display_picture + "'" + ", " + "'" + user_status + "'" + ", " + "'" + verification_code + "'" + ");";
            ResultSet resultSet = statement.executeQuery(insert_name);

            // Close the connection
            statement.close();
            resultSet.close();
            postgresConnection.disconnect();


            return token;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } catch (UnsupportedEncodingException exception) {
            //UTF-8 encoding not supported
            return null;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            return null;
        }


    }


}