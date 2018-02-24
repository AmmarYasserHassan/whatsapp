package commands;

import database.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateUserName {
    DBConnection dbConnection;
    String user_number;
    String display_name;

    /**
     * Constructor
     *
     * @param dbConnection
     * @param user_number
     * @param display_name
     */

    public UpdateUserName(DBConnection dbConnection, String user_number, String display_name) {
        super();
        this.user_number = user_number;
        this.display_name = display_name;
    }


    public ResultSet execute() {

        try {
            // Execute the sql statement
            Statement statement = dbConnection.connect().createStatement();

            String update_name = "SELECT * FROM update_user_name(" + "\"" + user_number + "\"" + ", " + "\"" + display_name + "\"" + ");";
            ResultSet resultSet = statement.executeQuery(update_name);

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
