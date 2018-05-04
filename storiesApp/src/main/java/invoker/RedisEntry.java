package invoker;

import java.io.Serializable;
import java.io.*;
import java.util.Base64;

public class RedisEntry implements Serializable {

    JsonObject request;
    String cmdName;

    public  RedisEntry(String cmd, JsonObject req){

        this.request = req;
        this.cmdName = cmd;
    }


    public String serialize(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                bos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
