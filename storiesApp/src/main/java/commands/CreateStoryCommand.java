package commands;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DBHandler;
import models.Story;
import org.json.JSONObject;
import sender.MqttSender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateStoryCommand implements Command, Runnable {

    DBHandler dbHandler;
    private JsonObject request;
    private Date expirationDate;

    public CreateStoryCommand(DBHandler dbHandler, JsonObject request) {
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 24); // adds one hour
        this.expirationDate = cal.getTime();
        this.request = request;
        this.dbHandler = dbHandler;
    }

    @Override
    public JSONObject execute() {
        Story res = this.dbHandler.createNewStory(this.request, expirationDate);
        String json = new Gson().toJson(res);
        return new JSONObject(json);
    }

    @Override
    public void run() {
        JSONObject res = this.execute();
        try {
            MqttSender sender = new MqttSender();
            sender.send(res);
            sender.close();
        } catch (Exception e) {

        }
    }
}
