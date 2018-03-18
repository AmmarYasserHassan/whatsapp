package database;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;


public class MongoDBConnection {

    /**
     * MongoDBConnection constructor
     */
    private MongoDatabase mongodb;
    private MongoClient mongoClient;
    private  String mongoHost;
    private String mongoPort;
    private String mongodbName;


    public MongoDBConnection(String mongoHost, String mongoPort, String mongodbName){
        this.mongodbName = mongodbName;
        this.mongoHost = mongoHost;
        this.mongoPort = mongoPort;
    }

    /**
     * Connect to the database
     *
     * @return MongoDBConnection
     */
    public MongoDBConnection connect(){

        if (mongoClient == null && mongoPort != null && mongoHost !=null) {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://" + mongoHost + ":" + mongoPort));
            this.mongodb = mongoClient.getDatabase(mongodbName);
        }

        return this;
    }

    /**
     * Disconnect from the database
     */
    public void disconnect(){

        if(mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }

    }

    public MongoDatabase getMongodb() {
        return mongodb;
    }

}
