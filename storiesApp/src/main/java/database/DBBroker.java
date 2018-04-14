package database;


import com.mongodb.BasicDBObject;

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
    static PostgreSqlDBConnection postgresqlDBConnection;
    static MongoDBConnection mongoDBConnection = new MongoDBConnection(mongoHost, mongoPort, mongoDbName);
    private MongoDatabase mongodb;
    private MongoCollection stories;

    public DBBroker() {
        mongoDBConnection.connect();
        this.mongodb = mongoDBConnection.getMongodb();
        this.stories = mongodb.getCollection("stories");
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
        Document document = (Document) stories.find(eq("_id", new ObjectId(id))).first();
        return createStory(document);
    }

    public Story createStory(Document document) {

        Story story = null;

        if (document != null) {

            int duration = document.getInteger("duration");
            Date expirationDate = document.getDate("expiration_date");
            String mobile = document.getString("owner_mobile_number");
            String type = document.getString("type");
            String source = document.getString("link");
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

    public ArrayList<Story> getAllStroies(String ownerMobileNumber) {

        Connection sqldb = postgresqlDBConnection.connect();
        ArrayList<Story> friendStories = null;
        try {
            friendStories = new ArrayList<Story>();

            Statement statement = sqldb.createStatement();
            String sql = "SELECT * FROM FRIENDS WHERE first_number =" + ownerMobileNumber + "OR second_number =" + ownerMobileNumber;

            ResultSet rs = statement.executeQuery(sql);
            ArrayList<String> friends = new ArrayList<String>();

            while (rs.next()) {
                String firstNum = rs.getString("first_number");
                String secondNum = rs.getString("second_number");
                if (firstNum.equals(ownerMobileNumber)) {
                    friends.add(secondNum);
                } else {
                    if (secondNum.equals(ownerMobileNumber))
                        friends.add(firstNum);
                }
            }

            for (int i = 0; i < friends.size(); i++) {
                String friend = friends.get(i);
                FindIterable<Document> foundStories = stories.find(eq("owner_mobile_number", friend));
                for (Document d : foundStories) {
                    Story s = createStory(d);
                    friendStories.add(s);
                }

            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            postgresqlDBConnection.disconnct();
        }


        return friendStories;
    }

    public JSONObject update(String id, String viewerNum) {
        JSONObject res = new JSONObject();
        stories.updateOne(new Document("_id", new ObjectId(id)), new BasicDBObject("$push", new BasicDBObject("viewed_by", viewerNum)));
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
        Document doc = new Document();
        doc.append("owner_mobile", request.get("owner_mobile").getAsString());
        doc.append("type", request.get("type").getAsString());
        doc.append("link", request.get("link").getAsString());
        doc.append("duration", request.get("duration").getAsInt());
        doc.append("expiration_date", expiryDate);
        doc.append("viewed_by", new ArrayList());
        stories.insertOne(doc);
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
        stories.deleteOne(new Document("_id", new ObjectId(request.get("storyId").getAsString())));
        result.put("state", "DONE");
        return result;
    }


}
