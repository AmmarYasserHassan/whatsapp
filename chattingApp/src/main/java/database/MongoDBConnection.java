package database;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import config.ApplicationProperties;

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
        basicURI = ApplicationProperties.getMongoHost();
        databaseName = ApplicationProperties.getProperty("mongoDatabaseName");
//        username = ApplicationProperties.getProperty("mongoUsername");
//        password = ApplicationProperties.getProperty("mongoPassword");
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
        try {
            mongoClient = new MongoClient(mongoClientURI);
            System.out.println(databaseName);
            System.out.println(basicURI);
            database = mongoClient.getDB(databaseName);
        } catch (UnknownHostException e) {
            System.err.println("Cannot connect to mongoDB !");
        }
        return database;
    }

}
