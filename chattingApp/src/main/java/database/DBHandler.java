package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler {
	
	static PostgreSqlDBConnection postgresqlDBConnection = new PostgreSqlDBConnection();
	static MongoDBConnection mongoDBConnection = new MongoDBConnection();
	
	/**
	 * Execute sql query by the postgreSQL Database.
	 * 
	 * @param query
	 * @return date ResultSet
	 * @see {@link ResultSet}
	 */
    public static ResultSet executeSQLQuery(String query){
    	Connection connection  = postgresqlDBConnection.connect();
    	try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(query);
		} catch (SQLException e) {
			System.err.println("Cannot execute query !");
			return null;
		} finally {
			postgresqlDBConnection.disconnct();
		}
    }
    
    //TODO mongo handlers
    
}
