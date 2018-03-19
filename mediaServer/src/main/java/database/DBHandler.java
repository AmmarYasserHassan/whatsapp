package database;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DBHandler {

    public Bucket bucket;

    /**
     * DbHandler constructor
     */
    public DBHandler() {
        Cluster cluster = CouchbaseCluster.create("localhost");
        cluster.authenticate("hossam", "123456");
        this.bucket = cluster.openBucket("media1");
    }

    /**
     * Store a media file in Couchbase bucket
     *
     * @param userID
     * @param base64
     * @return uuid of the media file
     */
    public synchronized String storeMedia(String userID, String base64) {
        try {
            UUID mediaID = UUID.randomUUID();
            String newFileName = userID + "/" + mediaID;
            byte imageBinary[] = Base64.getDecoder().decode(base64);

            JsonObject data = JsonObject.create()
                    .put("type", "image")
                    .put("name", newFileName)
                    .put("fileContent", Base64.getEncoder().encodeToString(imageBinary));
            JsonDocument doc = JsonDocument.create(newFileName, data);
            bucket.upsert(doc);

            return mediaID.toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve a media file from Couchbase bucket
     *
     * @param userID
     * @param mediaID
     * @return Base64 of the stored media file
     */
    public synchronized String retrieve(String userID, String mediaID) {
        JsonDocument doc = bucket.get(userID + "/" + mediaID, 20, TimeUnit.SECONDS);
        JsonObject content = doc.content();
        return content.get("fileContent").toString();
    }

    /**
     * Remove a media file
     *
     * @param userID
     * @param mediaID
     */
    public synchronized void delete(String userID, String mediaID) {
        try {
            bucket.remove(userID + "/" + mediaID);
        } catch (IllegalArgumentException e) {
            System.err.println("MEDIA NOT FOUND");
        }
    }

}
