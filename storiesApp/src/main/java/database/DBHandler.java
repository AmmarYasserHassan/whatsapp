package database;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Story;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DBHandler {

    /**
     * DbHandler constructor
     */

    private MongoDatabase mongodb;
    private MongoCollection stories;
    private Connection sqldb;

    public DBHandler(MongoDBConnection db, PostgresConnection sql){

        this.mongodb = db.getMongodb();
        this.stories = mongodb.getCollection("stories");
        this.sqldb = sql.getConn();
        if(stories==null){
            System.out.println("Collection:stories not found");
        }

    }

    /**Get a Story
     *
     * @param id
     * @return Story
     */

    public Story getStory(String id){


        Document document = (Document) stories.find(eq("_id", new ObjectId(id))).first();

        Story story = null;

        if(document!=null){

            int duration =  document.getInteger("duration");
            Date expirationDate = document.getDate("expiration_date");
            String mobile = document.getString("owner_mobile_number");
            String type = document.getString("type");
            String source = document.getString("link");
            ArrayList viewedBy = (ArrayList) document.get("viewed_by");
            story = new Story(mobile,type,source, duration,expirationDate,viewedBy);
        }

        return story;
    }

    /**Get All Friends Stories
     *
     * @param ownerMobileNumber
     * @return
     */

    public ArrayList<Story> getAllStroies(String ownerMobileNumber){

        try {
            Statement statement = sqldb.createStatement();
            String sql = "SELECT * FROM FRIENDS WHERE first_number ="+ownerMobileNumber+"OR second_number ="+ownerMobileNumber;

            ResultSet rs = statement.executeQuery(sql);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


        return null;
    }


}
