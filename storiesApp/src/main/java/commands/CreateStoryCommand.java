package commands;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DBBroker;
import models.Story;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class CreateStoryCommand implements Command {

    DBBroker dbBroker;
    private JsonObject request;
    private Date expirationDate;

    public CreateStoryCommand(DBBroker dbBroker, JsonObject request) {
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 24); // adds one hour
        this.expirationDate = cal.getTime();
        this.request = request;
        this.dbBroker = dbBroker;
    }

    @Override
    public JSONObject call() throws Exception {
        Story res = this.dbBroker.createNewStory(this.request, expirationDate);
        String json = new Gson().toJson(res);
        return new JSONObject(json);
    }
}
