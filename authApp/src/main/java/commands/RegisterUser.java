package commands;


import database.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;



public class RegisterUser {

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
     * @param userNumber
     * @param displayName
     */

    /**
     * Update User name constructor
     * @param dbHandler
     * @param userNumber
     * @param displayName
     */

    public RegisterUser(DBHandler dbHandler, String userNumber, String displayName
            , String display_picture, String user_status) {
        super();
        this.dbHandler = dbHandler;
        this.userNumber = userNumber;
        this.displayName = displayName;
        this.display_picture = display_picture;
        this.user_status = user_status;
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
    public String execute() throws SQLException {
        return this.dbHandler.RegisterUser(  userNumber,  displayName
                ,  display_picture,  user_status, verification_code);
    }

}
