package commands;

import org.json.JSONObject;

import com.google.gson.JsonObject;

import database.DBBroker;

public class UpdateMessageStatusCommand implements Command {
    DBBroker dbBroker;
    String userNumber;
    String messageId;
    String statusUpdate;
    int chatId;
    boolean isGroupChat;

    /**
     * Constructor
     *
     * @param dbBroker
     * @param request
     */
    public UpdateMessageStatusCommand(DBBroker dbBroker, JsonObject request) {
        super();
        this.dbBroker = dbBroker;
        this.userNumber = request.get("userNumber").getAsString();
        this.messageId = request.get("messageId").getAsString();
        this.statusUpdate = request.get("statusUpdate").getAsString();
        this.chatId = request.get("chatId").getAsInt();
        this.isGroupChat = request.get("isGroupChat").getAsBoolean();
    }

    @Override
    public Object call() throws Exception {
        if (isGroupChat) {
            String jsonFindDocument = "\"{_id:ObjectId(" + messageId + "),"
                    + "'participants_status':{$elemMatch:{'mobile_number':" + userNumber + "}}";
            String jsonUpdateDocument = "\"{$set:{'participants_status.$.status':\"" + statusUpdate + "\"}}\"";

            return this.dbBroker.updateMongoDocument(jsonFindDocument, jsonUpdateDocument, "group_chats");

        } else {
            String jsonFindDocument = "\"{_id:ObjectId(" + messageId + ")\"";
            String jsonUpdateDocument = "\"{$set: {'status':\"" + statusUpdate + "\"}}\"";
            System.out.println(jsonUpdateDocument);
            return this.dbBroker.updateMongoDocument(jsonFindDocument, jsonUpdateDocument, "chats");
        }
    }
}

