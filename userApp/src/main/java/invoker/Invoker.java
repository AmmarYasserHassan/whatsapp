package invoker;

import com.google.gson.JsonObject;
import com.mongodb.DB;
import commands.Command;
import config.ApplicationProperties;
import database.DBBroker;
import database.MongoDBConnection;
import org.json.JSONObject;
import org.postgresql.ds.PGPoolingDataSource;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Invoker {
    protected Hashtable htblCommands;
    protected ExecutorService threadPoolCmds;
    protected PGPoolingDataSource postgresqlDBConnectionsPool;
    protected DB mongoDBConnection;

    public Invoker() throws Exception {
        this.init();
    }

    public String invoke(String cmdName, JsonObject request) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ExecutionException, InterruptedException, SQLException {
        Command cmd;
        Class<?> cmdClass = (Class<?>) htblCommands.get(cmdName);
        Constructor constructor = cmdClass.getConstructor(DBBroker.class, JsonObject.class);
        Object cmdInstance = constructor.newInstance(new DBBroker(getPostgresConnection(), mongoDBConnection), request);
        cmd = (Command) cmdInstance;
        Future<JSONObject> result = threadPoolCmds.submit(cmd);
        return result.get().toString();
    }

    protected void loadCommands() throws Exception {
        htblCommands = new Hashtable();
        Properties prop = new Properties();
//       FileInputStream i = new FileInputStream("config/commands.properties");
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

    protected Connection getPostgresConnection() throws SQLException
    {
        return (Connection)postgresqlDBConnectionsPool.getConnection();

    }
    protected void loadThreadPool() {
        threadPoolCmds = Executors.newFixedThreadPool(40);
    }

    public void init() throws Exception {
        loadThreadPool();
        loadCommands();
        createPostgresDataSource();
        this.mongoDBConnection = new MongoDBConnection().connect();
    }
}
