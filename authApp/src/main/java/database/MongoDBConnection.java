package database;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;

public class MongoDBConnection {

	private MongoClient mongoClient;
	private DB database;
	private String basicURI;
	private String databaseName;
	private String username;
	private String password;

	/**
	 * DBConnection constructor.
	 * This constructor uses environment variables to for the database options.
	 */
	public MongoDBConnection() {
		mongoClient = null;
		database = null;
		basicURI = System.getenv("MONGO_HOST")+":27017";
		databaseName = "mydb";
		username = "Username";
		password = "Password";
	}

	/**
	 * Connect to the database.
	 *
	 * @return database object.
	 * @see {@link DB}
	 */
	public DB connect() {
		//    	+username+":"+password+"@"
		String uri = "mongodb://" + basicURI;
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		mongoClient = new MongoClient(mongoClientURI);
		database = mongoClient.getDB(databaseName);

		return database;
	}

}
