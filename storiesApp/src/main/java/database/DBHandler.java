package database;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Story;

import java.util.ArrayList;

public class DBHandler {

    /**
     * DbHandler constructor
     */

    private MongoDatabase mongodb;
    private MongoCollection stories;

    public DBHandler(DBConnection db){

        this.mongodb = db.getMongodb();
        this.stories = mongodb.getCollection("stories");
        if(stories==null){
            System.out.println("Collection:stories not found");
        }

    }

    public Story getStory(int id){
        return null;
    }

    public ArrayList<Story> getAllStroies(){
        return null;
    }


}
