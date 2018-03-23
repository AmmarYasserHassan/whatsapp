package commands;

import database.DBHandler;

import java.util.HashMap;


public class UploadMediaFileCommand {
    public DBHandler dbHandler;
    public HashMap<String, String> args;

    /**
     * UploadMediaFileCommand constructor
     *
     * @param dbHandler
     */
    public UploadMediaFileCommand(DBHandler dbHandler, HashMap<String, String> args) {
        this.dbHandler = dbHandler;
        this.args = args;
    }

    /**
     * RetrieveMediaFileCommand execution
     *
     * @return Base64 string of the media file
     */
    public String execute() {

//        System.out.println(this.args.get("userID"));
        return this.dbHandler.storeMedia(this.args.get("userID"), this.args.get("data"));
    }
}
