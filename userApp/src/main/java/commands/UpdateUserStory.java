package commands;

import database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateUserStory extends BaseCommand{
    DBConnection dbConnection;
    String user_number;
    String user_status;

    /**
     * Constructor
     *
     * @param dbConnection
     * @param user_number
     * @param user_status
     */

    public UpdateUserStory(DBConnection dbConnection, String user_number, String user_status) {
        super();
        this.user_number = user_number;
        this.user_status = user_status;
    }


    public ResultSet execute() {

        try {
            // Execute the sql statement
            Statement statement = dbConnection.connect().createStatement();

            String updated_status = "SELECT * FROM update_user_status(" + "\"" + user_number + "\"" + ", " + "\"" + user_status + "\"" + ");";
            ResultSet resultSet = statement.executeQuery(updated_status);

            // Close the connection
            statement.close();
            resultSet.close();
            dbConnection.disconnct();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
}

