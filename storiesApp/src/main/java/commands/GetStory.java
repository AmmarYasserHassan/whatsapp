package commands;

import database.DBHandler;
import models.Story;

import java.util.ArrayList;

public class GetStory extends BaseCommand {

    private String id;

    public GetStory(DBHandler handler, String id) {
        super(handler);
        this.id = id;
    }

    public Story execute() {
        Story s = this.handler.getStory(id);
        return s;
    }
}
