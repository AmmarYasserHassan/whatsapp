package commands;


import com.google.gson.JsonObject;
import database.DBHandler;
import org.json.JSONObject;

public class DeleteAStoryCommand implements Command, Runnable {
    DBHandler dbHandler;
    JsonObject request;

    public DeleteAStoryCommand(DBHandler dbHandler, JsonObject request) {
        this.dbHandler = dbHandler;
        this.request = request;
    }

    @Override
    public JSONObject execute() {
        JSONObject res = this.dbHandler.deleteAStory(request);
        return res;
    }

    @Override
    public void run() {

    }
}
