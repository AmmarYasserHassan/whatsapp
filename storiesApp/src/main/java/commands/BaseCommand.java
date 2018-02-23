package commands;


public abstract class BaseCommand {

    /**
     * BaseCommand constructor
     */
    public BaseCommand() {

    }

    /**
     * Execute a command
     *
     * @return output of the command
     */
    public abstract String execute();
}
