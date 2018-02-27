package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

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
		basicURL = "localhost:5432";
		databaseName = "postgres";
		username = "postgres";
		password = "abosalah";
	}

	/**
     * Connect to the database.
     *
     * @return sql connection object.
     * @see {@link Connection}
     */
    Connection connect(){
    	String postgresqlJdbcUrl = "jdbc:postgresql://"+basicURL+"/"+databaseName+"/";
        try {
			connection = DriverManager.getConnection(postgresqlJdbcUrl, username, password);
        } catch (SQLException e) {
			System.err.println("Cannot connect to PostgreSql Database !");
		}
        return connection;
    }

    /**
     * Disconnect from the database.
     * 
     * @return true if connection found and disconnected successfully from if, false otherwise.
     */
    boolean disconnct(){
    	if(connection != null){
    		try {
				connection.close();
				connection = null;
				return true;
			} catch (SQLException e) {
				System.err.println("Cannot disconnect from PostgreSql Database !");
				return false;
			}
    	}else{
    		return false;
    	}
    }	
}
