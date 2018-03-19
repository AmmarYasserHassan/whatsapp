package commands;


import database.DBHandler;

import java.util.HashMap;

public class RetrieveMediaFileCommand {

    DBHandler dbHandler;
    HashMap<String, String> args;

    /**
     * RetrieveMediaFileCommand constructor
     *
     * @param dbHandler
     */
    public RetrieveMediaFileCommand(DBHandler dbHandler, HashMap<String, String> args) {
        this.dbHandler = dbHandler;
        this.args = args;
    }

    /**
     * RetrieveMediaFileCommand execution
     *
     * @return Base64 string of the media file
     */
    public String execute() {
        return this.dbHandler.retrieve(this.args.get("userID"), this.args.get("mediaID"));
    }

}
