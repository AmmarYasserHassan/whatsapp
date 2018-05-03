package commands;

import com.google.gson.JsonObject;
import database.DBBroker;
import org.json.JSONObject;

public class AddViewerToStory implements Command {
    DBBroker dbBroker;
    String id;
    String viewerMobileNumber;

    public AddViewerToStory(DBBroker handler, JsonObject request) {
        this.dbBroker = handler;
        this.id = request.get("storyId").getAsString();
        this.viewerMobileNumber = request.get("ViewerMobileNumber").getAsString();
    }

    @Override
    public JSONObject call() throws Exception {
        JSONObject s = this.dbBroker.update(id, viewerMobileNumber);
        return s;
    }
}

