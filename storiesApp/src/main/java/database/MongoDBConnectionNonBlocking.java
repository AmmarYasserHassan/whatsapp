package database;


import com.mongodb.ServerAddress;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.connection.ClusterSettings;
import config.ApplicationProperties;

import static java.util.Arrays.asList;


public class MongoDBConnectionNonBlocking {
    private static volatile MongoDBConnectionNonBlocking mongoDBConnectionNonBlocking = null;
    public MongoDatabase database;

    private MongoDBConnectionNonBlocking() {

        MongoClient mongoClient = MongoClients.create();

        database = mongoClient.getDatabase("mydb");

    }

    public static MongoDBConnectionNonBlocking getInstance() {

        if (mongoDBConnectionNonBlocking != null) return mongoDBConnectionNonBlocking;

        synchronized (MongoDBConnectionNonBlocking.class) {

            if (mongoDBConnectionNonBlocking == null) {

                mongoDBConnectionNonBlocking = new MongoDBConnectionNonBlocking();
            }
        }

        return mongoDBConnectionNonBlocking;

    }
}
