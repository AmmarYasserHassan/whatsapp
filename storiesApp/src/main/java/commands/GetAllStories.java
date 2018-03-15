package commands;

import database.DBHandler;
import models.Story;

import java.util.ArrayList;

public class GetAllStories extends BaseCommand {

    private String mobile;

    public GetAllStories(DBHandler handler, String ownerMobile) {
        super(handler);
        this.mobile = ownerMobile;
    }

    public ArrayList<Story> execute() {
        ArrayList<Story> s = this.handler.getAllStroies(mobile);
        return s;
    }


}
