import commands.RetrieveMediaFileCommand;
import commands.UploadMediaFileCommand;
import database.DBHandler;
import database.MongoDBConnection;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
//        String mongoHost = "localhost";
//        String mongoPort = "27017";
//        String mongodbName = "whatsapp";
//
//        MongoDBConnection db = new MongoDBConnection(mongoHost, mongoPort, mongodbName);
//        DBHandler dbHandler = new DBHandler(db.connect());
//
//        byte[] imageBytes = IOUtils.toByteArray(new FileInputStream("C:\\Users\\welcome\\Desktop\\mediaServer\\download.jpg"));
//        String base64 = Base64.getEncoder().encodeToString(imageBytes);
//
//        HashMap<String, String> arguments = new HashMap<String, String>();
//        arguments.put("userID", "3");
//        arguments.put("data", base64);
//
//        UploadMediaFileCommand cmd = new UploadMediaFileCommand(dbHandler, arguments);
//        String mediaID = cmd.execute();
//
//        HashMap<String, String> arguments2 = new HashMap<String, String>();
//        arguments2.put("userID", "3");
//        arguments2.put("mediaID", mediaID);
//
//        RetrieveMediaFileCommand cmd2 = new RetrieveMediaFileCommand(dbHandler, arguments2);
//        System.out.println(cmd2.execute());


    }
}
