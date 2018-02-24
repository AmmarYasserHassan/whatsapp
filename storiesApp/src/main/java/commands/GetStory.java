package commands;

import database.DBHandler;
import models.Story;

public class GetStory extends BaseCommand {

    private int id;

    public GetStory(DBHandler handler, int id) {
        super(handler);
        this.id = id;
    }

    public String execute() {
        Story s = this.handler.getStory(id);
        return null;
    }
}
