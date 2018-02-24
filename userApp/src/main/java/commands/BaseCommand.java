package commands;

import database.PostgresConnection;
import java.sql.ResultSet;

public abstract class BaseCommand {
    PostgresConnection dbConnection;

    /**
     * BaseCommand constructor
     */
    public BaseCommand(PostgresConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Execute a command
     *
     * @return output of the command
     */
    public abstract ResultSet execute();
}
