package commands;


import com.google.gson.JsonObject;
import database.DBBroker;
import org.json.JSONObject;

public class
DeleteAStoryCommand implements Command {
    DBBroker dbBroker;
    JsonObject request;

    public DeleteAStoryCommand(DBBroker dbBroker, JsonObject request) {
        this.dbBroker = dbBroker;
        this.request = request;
    }

    @Override
    public Object call() throws Exception {
        JSONObject res = this.dbBroker.deleteAStory(request);
        return res;
    }
}
