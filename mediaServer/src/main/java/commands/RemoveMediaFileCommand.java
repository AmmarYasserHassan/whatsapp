package commands;

import database.DBHandler;

import java.util.HashMap;

public class RemoveMediaFileCommand {
    DBHandler dbHandler;
    HashMap<String, String> args;

    /**
     * RemoveMediaFileCommand constructor
     *
     * @param dbHandler
     */
    public RemoveMediaFileCommand(DBHandler dbHandler, HashMap<String, String> args) {
        this.dbHandler = dbHandler;
        this.args = args;
    }

    /**
     * RetrieveMediaFileCommand execution
     *
     * @return Base64 string of the media file
     */
    public void execute() {
        this.dbHandler.delete(this.args.get("userId"), this.args.get("mediaID"));
    }
}
