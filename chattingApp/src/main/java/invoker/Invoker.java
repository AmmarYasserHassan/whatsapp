package invoker;

import com.google.gson.JsonObject;
import commands.Command;
import config.ApplicationProperties;
import database.DBBroker;
import org.json.JSONObject;
import database.MongoDBConnection;
import database.PostgreSqlDBConnection;
import org.postgresql.ds.PGPoolingDataSource;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Invoker {
    protected Hashtable htblCommands;
    protected ExecutorService threadPoolCmds;
    protected PGPoolingDataSource postgresqlDBConnectionsPool;
    protected MongoDBConnection mongoDBConnection;

    public Invoker() throws Exception {
        this.init();
    }

    public String invoke(String cmdName, JsonObject request) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException{
        Command cmd;
        Class<?> cmdClass = (Class<?>) htblCommands.get(cmdName);
        Constructor constructor = cmdClass.getConstructor(DBBroker.class, JsonObject.class);
        Object cmdInstance = constructor.newInstance(new DBBroker(getPostgresConnection(), mongoDBConnection), request);
        cmd = (Command) cmdInstance;
        JSONObject result = cmd.execute();
        return result.toString();
//        threadPoolCmds.execute((Runnable) cmd);
    }

    protected void loadCommands() throws Exception {
        htblCommands = new Hashtable();
        Properties prop = new Properties();
        InputStream in = ApplicationProperties.class.getResourceAsStream("commands.properties");
        prop.load(in);
        in.close();
        Enumeration enumKeys = prop.propertyNames();
        String strActionName, strClassName;

        while (enumKeys.hasMoreElements()) {
            strActionName = (String) enumKeys.nextElement();
            strClassName = (String) prop.get(strActionName);
//            C:\Users\welcome\Desktop\whatsapp\chattingApp\src\main\java\commands\AddAdminsToAGroupChatCommand.java
            Class<?> innerClass = Class.forName("commands." + strClassName);
            htblCommands.put(strActionName, innerClass);
        }
    }

    protected void loadThreadPool() {
        threadPoolCmds = Executors.newFixedThreadPool(Integer.parseInt(ApplicationProperties.getProperty("threadPoolMaxSize")));
    }

    protected void createPostgresDataSource() {
        String host = ApplicationProperties.getPostgresHost();
        String username = ApplicationProperties.getProperty("postgresUsername");
        String password = ApplicationProperties.getProperty("postgresPassword");
        String database = ApplicationProperties.getProperty("postgresDatabaseName");

        PGPoolingDataSource ds = new PGPoolingDataSource();
        ds.setUser(username);
        ds.setServerName(host);
        ds.setPassword(password);
        ds.setDatabaseName(database);
        ds.setInitialConnections(0);
        Integer maxConnections = Integer.parseInt(ApplicationProperties.getProperty("postgresConnectionsPoolMaxSize"));
        ds.setMaxConnections(maxConnections);
        this.postgresqlDBConnectionsPool = ds;
    }

    protected PostgreSqlDBConnection getPostgresConnection() throws SQLException
    {
            return (PostgreSqlDBConnection)postgresqlDBConnectionsPool.getConnection();

    }

    public void init() throws Exception {
        loadThreadPool();
        loadCommands();
        createPostgresDataSource();
        mongoDBConnection = new MongoDBConnection();
    }
}
