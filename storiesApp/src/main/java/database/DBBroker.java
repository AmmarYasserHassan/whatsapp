package database;


import com.mongodb.*;

import com.google.gson.JsonObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import config.ApplicationProperties;
import models.Story;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import static com.mongodb.client.model.Filters.eq;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DBBroker {

    /**
     * DbHandler constructor
     */
    static String mongoHost = ApplicationProperties.getMongoHost();
    static String mongoPort = "27017";
    static String mongoDbName = "whatsapp";
    protected Connection postgresqlDBConnection;
    protected DB mongoDBConnection ;
    private MongoDatabase mongodb;
    private DBCollection stories;

    public DBBroker(Connection postgresqlDBConnection,DB mongoDBConnection) {
        this.mongoDBConnection = mongoDBConnection;
        this.postgresqlDBConnection = postgresqlDBConnection;
        this.stories = mongoDBConnection.getCollection("stories");
        if (stories == null) {
            System.out.println("Collection:stories not found");
        }

    }

    /**
     * Get a Story
     *
     * @param id
     * @return Story
     */

    public Story getStory(String id) {
        DBObject ob = new BasicDBObject();
        ob.put("_id", new ObjectId(id));
        System.out.println(ob);
        DBObject document =  stories.findOne(ob);
        return createStory(document);
    }

    public Story createStory(DBObject document) {

        Story story = null;

        if (document != null) {

            int duration = (Integer) document.get("duration");
            Date expirationDate = (Date) document.get("expiration_date");
            String mobile = (String)document.get("owner_mobile_number");
            String type = (String)document.get("type");
            String source = (String)document.get("link");
            ArrayList viewedBy = (ArrayList) document.get("viewed_by");
            story = new Story(mobile, type, source, duration, expirationDate, viewedBy);
        }

        return story;
    }

    /**
     * Get All Friends Stories
     *
     * @param ownerMobileNumber
     * @return
     */

    public ArrayList<Story> getAllStroies(String ownerMobileNumber) throws SQLException {

        Connection sqldb = postgresqlDBConnection;

        ArrayList<Story> friendStories = null;
        try {
            friendStories = new ArrayList<Story>();

            Statement statement = sqldb.createStatement();
            String sql = "SELECT * FROM FRIENDS WHERE first_mobile_number LIKE '" + ownerMobileNumber + "' OR second_mobile_number LIKE '" + ownerMobileNumber + "'";

            ResultSet rs = statement.executeQuery(sql);
            ArrayList<String> friends = new ArrayList<String>();

            while (rs.next()) {
                String firstNum = rs.getString("first_mobile_number");
                String secondNum = rs.getString("second_mobile_number");
                if (firstNum.equals(ownerMobileNumber)) {
                    friends.add(secondNum);
                } else {
                    if (secondNum.equals(ownerMobileNumber))
                        friends.add(firstNum);
                }
            }

            for (int i = 0; i < friends.size(); i++) {
                String friend = friends.get(i);
                DBObject ob = new BasicDBObject();
                ob.put("owner_mobile", friend);
                DBCursor foundStories = stories.find(ob);
                for (DBObject d : foundStories) {
                    Story s = createStory(d);
                    friendStories.add(s);
                }

            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            postgresqlDBConnection.close();
        }
        return friendStories;
    }

    public JSONObject update(String id, String viewerNum) {
        JSONObject res = new JSONObject();
        stories.update(new BasicDBObject("_id", new ObjectId(id)), new BasicDBObject("$push", new BasicDBObject("viewed_by", viewerNum)));
        res.put("state", "DONE");
        return res;
    }

    /**
     * Create a new Story
     *
     * @param request
     * @param expiryDate
     * @return
     */
    public Story createNewStory(JsonObject request, Date expiryDate) {
        DBObject doc = new BasicDBObject();
        doc.put("owner_mobile", request.get("owner_mobile").getAsString());
        doc.put("type", request.get("type").getAsString());
        doc.put("link", request.get("link").getAsString());
        doc.put("duration", request.get("duration").getAsInt());
        doc.put("expiration_date", expiryDate);
        doc.put("viewed_by", new ArrayList());
        stories.insert(doc);
        return this.createStory(doc);
    }

    /**
     * Delete a story
     *
     * @param request
     * @return
     */
    public JSONObject deleteAStory(JsonObject request) {
        JSONObject result = new JSONObject();
        stories.findAndRemove(new BasicDBObject("_id", new ObjectId(request.get("storyId").getAsString())));
        result.put("state", "DONE");
        return result;
    }


}
