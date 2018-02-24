import database.DBConnection;

public class Main {

    public static  void main(String[] args){
        DBConnection db = new DBConnection();
        db.connect();



        db.disconnct();
    }

}
