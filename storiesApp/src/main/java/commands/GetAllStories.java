package commands;

import com.google.gson.Gson;
import database.DBHandler;
import models.Story;
import org.json.JSONObject;
import sender.MqttSender;

import java.util.ArrayList;

public class GetAllStories implements Command,Runnable {

    DBHandler dbHandler;
    String userNumber;

    public GetAllStories(DBHandler handler, JSONObject request) {
        this.dbHandler = handler;
        this.userNumber = request.getString("userNumber");
    }


    public void run() {
        JSONObject res = this.execute();
        try {
            MqttSender sender = new MqttSender();
            sender.send(res);
            sender.close();
        }catch (Exception e){

        }
    }

    public JSONObject execute() {
        ArrayList<Story> stories = dbHandler.getAllStroies(userNumber);

        String json = new Gson().toJson(stories);

        return new JSONObject(json);

    }
}
