package database;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class DBConnection {

    /**
     * DBConnection constructor
     */
    private MongoDatabase mongodb;
    private MongoClient mongoClient;
    private  String mongoHost;
    private String mongoPort;
    private String dbName;

    public DBConnection(){
        Properties properties = new Properties();
        try {
            InputStream in = new FileInputStream("src/main/resources/config/application.properties");
            properties.load(in);

            mongoHost = properties.getProperty("mongodb_host");
            mongoPort = properties.getProperty("mongodb_port");
            dbName = properties.getProperty("mongodb_name");
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Connect to the database
     *
     * @return DBConnection
     */
    public DBConnection connect(){

        if (mongoClient == null && mongoPort != null && mongoHost !=null) {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://" + mongoHost + ":" + mongoPort));
            this.mongodb = mongoClient.getDatabase(dbName);
        }
        return this;
    }

    /**
     * Disconnect from the database
     */
    public void disconnct(){
        if(mongoClient!=null)
            mongoClient.close();
    }

    public MongoDatabase getMongodb() {
        return mongodb;
    }

}
