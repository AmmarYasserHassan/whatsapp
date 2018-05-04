package invoker;

import com.google.gson.JsonObject;
import com.mongodb.DB;
import commands.Command;
import config.ApplicationProperties;
import database.DBBroker;
import org.json.JSONObject;
import database.MongoDBConnection;
import database.PostgreSqlDBConnection;
import org.postgresql.ds.PGPoolingDataSource;
import singletons.DbThreadPool;
import singletons.ThreadPool;

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
import java.io.*;
import java.util.Base64;
import redis.clients.jedis.Jedis;

public class Invoker {
    protected Hashtable htblCommands;
    protected PGPoolingDataSource postgresqlDBConnectionsPool;
    protected DB mongoDBConnection;
    protected Jedis jedis;


    public Invoker() throws Exception {
        this.init();
    }

    public String invoke(String cmdName, JsonObject request) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ExecutionException, InterruptedException, SQLException {
        Command cmd;
        RedisEntry key = new RedisEntry(cmdName, request);
        if(cmdName.equals("getAllStoriesCommand")){
            //check if in redis
            String res = jedis.get(key.serialize());
            if(res != null)
                return res;
        }
        Class<?> cmdClass = (Class<?>) htblCommands.get(cmdName);
        Constructor constructor = cmdClass.getConstructor(DBBroker.class, JsonObject.class);
        Object cmdInstance = constructor.newInstance(new DBBroker(getPostgresConnection(), mongoDBConnection), request);
        cmd = (Command) cmdInstance;
        Future<JSONObject> result = ThreadPool.getInstance().getThreadPoolCmds().submit(cmd);

        //cache in redis
        jedis.set(key.serialize(), result.get().toString());
        return result.get().toString();
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
            Class<?> innerClass = Class.forName("commands." + strClassName);
            htblCommands.put(strActionName, innerClass);
        }
    }


    protected Connection getPostgresConnection() throws SQLException {
        return (Connection) DbThreadPool.getInstance().getConnection();
    }

    public void init() throws Exception {
        loadCommands();
        jedis = new Jedis(ApplicationProperties.getProperty("redisBasicUrl"));
        this.mongoDBConnection = new MongoDBConnection().connect();

    }
}
