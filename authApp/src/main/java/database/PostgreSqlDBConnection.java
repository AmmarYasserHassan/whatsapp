package database;

import config.ApplicationProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlDBConnection {

    private Connection connection;
    private String basicURL;
    private String databaseName;
    private String username;
    private String password;

    /**
     * DBConnection constructor.
     * This constructor uses environment variables to for the database options.
     */
    public PostgreSqlDBConnection() {
        connection = null;
        basicURL = ApplicationProperties.getPostgresHost() + ":5432";
        databaseName = "whatsapp";
        username = "default";
        password = "secret";
    }

    /**
     * Connect to the database.
     *
     * @return sql connection object.
     * @see {@link Connection}
     */
    public Connection connect() {
        String postgresqlJdbcUrl = "jdbc:postgresql://" + basicURL + "/" + databaseName + "/";
        try {
            connection = DriverManager.getConnection(postgresqlJdbcUrl, username, password);
        } catch (SQLException e) {
            System.err.println("Cannot connect to PostgreSql Database !: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Disconnect from the database.
     *
     * @return true if connection found and disconnected successfully from if, false otherwise.
     */
    boolean disconnct() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                return true;
            } catch (SQLException e) {
                System.err.println("Cannot disconnect from PostgreSql Database !: " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }
}
