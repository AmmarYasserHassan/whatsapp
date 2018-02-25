package database;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.*;
import org.bson.types.ObjectId;

import java.util.Base64;
import java.util.UUID;

public class DBHandler {

    public MongoDatabase mongodb;
    public GridFSBucket bucket;

    /**
     * DbHandler constructor
     */
    public DBHandler(MongoDBConnection db) {
        this.mongodb = db.getMongodb();
        this.bucket = GridFSBuckets.create(this.mongodb);
    }

    /**
     * Store a media file in mongodb GridFS bucket
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

            GridFSUploadStream uploadStream = this.bucket.openUploadStream(newFileName);
            uploadStream.write(imageBinary);
            uploadStream.close();
            return mediaID.toString();
        } catch (MongoException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve a media file from mongodb GridFS bucket
     *
     * @param userID
     * @param mediaID
     * @return Base64 of the stored media file
     */
    public synchronized String retrieve(String userID, String mediaID) {
        try {
            System.out.println("USER " + userID);
            GridFSDownloadStream downloadStream = this.bucket.openDownloadStream(userID + "/" + mediaID);
            int fileLength = (int) downloadStream.getGridFSFile().getLength();
            byte[] imageBinary = new byte[fileLength];
            downloadStream.read(imageBinary);
            downloadStream.close();
            return Base64.getEncoder().encodeToString(imageBinary);
        } catch (MongoException e) {
            e.printStackTrace();
            return null;

        }
    }

    /**
     * Remove a media file
     *
     * @param userID
     * @param mediaID
     */
    public synchronized void delete(String userID, String mediaID) {
        try {
            bucket.delete(findObjectID(userID, mediaID));
        } catch (IllegalArgumentException e) {
            System.err.println("MEDIA NOT FOUND");
        }
    }

    /**
     * Helper function to find the objectID of a media File
     *
     * @param userID
     * @param mediaID
     * @return objectID
     */
    private synchronized ObjectId findObjectID(String userID, String mediaID) {
        try {
            BasicDBObject query = new BasicDBObject("filename", userID + "/" + mediaID);
            GridFSFindIterable files = this.bucket.find(query);
            return files.first().getObjectId();
        } catch (NullPointerException e) {
            return null;
        }
    }

}
