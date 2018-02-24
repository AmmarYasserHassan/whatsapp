package commands;


import database.DBHandler;

public abstract class BaseCommand {

    /**
     * BaseCommand constructor
     */
    DBHandler handler;

    public BaseCommand(DBHandler handler) {
        this.handler = handler;
    }

    /**
     * Execute a command
     *
     * @return output of the command
     */
    public abstract String execute();
}
