package database;


import com.mongodb.client.FindIterable;
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
        return createStory(document);
    }

    public Story createStory(Document document){

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

        ArrayList<Story> friendStories = null;
        try {
            friendStories = new ArrayList<Story>();

            Statement statement = sqldb.createStatement();
            String sql = "SELECT * FROM FRIENDS WHERE first_number ="+ownerMobileNumber+"OR second_number ="+ownerMobileNumber;

            ResultSet rs = statement.executeQuery(sql);
            ArrayList<String> friends = new ArrayList<String>();
            
            while(rs.next()){
                String firstNum = rs.getString("first_number");
                String secondNum = rs.getString("second_number");
                if(firstNum.equals(ownerMobileNumber)){
                    friends.add(secondNum);
                } else {
                    if(secondNum.equals(ownerMobileNumber))
                        friends.add(firstNum);
                }
            }

            for (int i=0; i< friends.size();i++){
                String friend = friends.get(i);
                FindIterable<Document> foundStories = stories.find(eq("owner_mobile_number", friend ));
                for(Document d: foundStories){
                    Story s = createStory(d);
                    friendStories.add(s);
                }

            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


        return friendStories;
    }


}
