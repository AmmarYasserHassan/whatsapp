package commands;

import com.google.gson.JsonObject;
import database.DBBroker;

import org.json.JSONObject;

public class StarAMessageCommand implements Command {
    DBBroker dbBroker;
    String userNumber;
    String messageToBeStarred;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */
    public StarAMessageCommand(DBBroker dbBroker, JsonObject request) {
        super();
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();
        this.messageToBeStarred = request.get("messageToBeStarred").getAsString();
    }

    @Override
    public JSONObject call() throws Exception {
        String jsonDocument = "\"{'user_number':'" + userNumber + "','message':'" + messageToBeStarred + "' }\"";
        String collectionName = "starred_messages";
        return this.dbBroker.insertMongoDocument(jsonDocument, collectionName);
    }
}