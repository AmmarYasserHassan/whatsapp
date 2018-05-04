package invoker;

import com.google.gson.JsonObject;
import commands.Command;
import config.ApplicationProperties;
import database.DBBroker;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.util.Base64;
import redis.clients.jedis.Jedis;

public class Invoker {
    protected Hashtable htblCommands;
    protected ExecutorService threadPoolCmds;
    protected Jedis jedis;

    public Invoker() throws Exception {
        this.init();
    }

    public String invoke(String cmdName, JsonObject request) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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
        Object cmdInstance = constructor.newInstance(new DBBroker(), request);
        cmd = (Command) cmdInstance;
        JSONObject result = cmd.execute();

        //cache in redis
        jedis.set(key.serialize(), result.toString());
        return result.toString();
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
        threadPoolCmds = Executors.newFixedThreadPool(20);
    }

    public void init() throws Exception {
        loadThreadPool();
        loadCommands();
        jedis = new Jedis(ApplicationProperties.getProperty("redisBasicUrl"));
    }
}