import database.MongoDBConnection;
import database.PostgresConnection;

public class Main {




    public static  void main(String[] args){

        String jdbcUrl = "jdbc:postgresql://localhost:5433/whatsapp";
        String username = "postgres";
        String password = "admin";

        String mongoHost = "localhost";
        String mongoPort =  "27017";
        String mongodbName = "whatsapp";



        MongoDBConnection db = new MongoDBConnection(mongoHost, mongoPort, mongodbName);
        db.connect();

        PostgresConnection sql = new PostgresConnection(jdbcUrl,username,password);
        sql.connect();


        db.disconnect();
    }

}
