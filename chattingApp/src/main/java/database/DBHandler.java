package database;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class DBHandler {
	
	static PostgreSqlDBConnection postgresqlDBConnection = new PostgreSqlDBConnection();
	static MongoDBConnection mongoDBConnection = new MongoDBConnection();
	static DB mongoDB = mongoDBConnection.connect();
	/**
	 * Execute sql query by the postgreSQL Database.
	 * 
	 * @param query
	 * @return date ResultSet
	 * @see {@link ResultSet}
	 */
    public static ResultSet executeSQLQuery(String query){
    	Connection connection  = postgresqlDBConnection.connect();
    	try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(query);
		} catch (SQLException e) {
			System.err.println("Cannot execute query !");
			return null;
		} finally {
			postgresqlDBConnection.disconnct();
		}
    }

    
    //TODO return type.
    public static void insertMongoDocument(String jsonDocument, String collectionName)
    {
        DBCollection collection = mongoDB.getCollection(collectionName);
        DBObject dbObject = (DBObject)JSON.parse(jsonDocument);
        collection.insert(dbObject);

    }
    
    
//    //TODO return type.
//    public static void insertAllMongoDocuments(ArrayList<String> jsonDocument, String collectionName)
//    {
//        DBCollection collection = mongoDB.getCollection(collectionName);
//        DBObject dbObject = (DBObject)JSON.parse(jsonDocument);
//        collection.insert(dbObject);
//
//    }
//    
    
    public static DBObject findMongoDocument(String jsonDocument, String collectionName)
    {
    	 DBCollection collection = mongoDB.getCollection(collectionName);
         DBObject dbObject = (DBObject)JSON.parse(jsonDocument);
         DBObject mongoDocument = collection.findOne(dbObject);
       return mongoDocument;
    }
    public static ArrayList<DBObject> findAllMongoDocuments(String jsonDocument, String collectionName)
    {
    	//ArrayList<DBOject>
    	 DBCollection collection = mongoDB.getCollection(collectionName);
         DBObject dbObject = (DBObject)JSON.parse(jsonDocument);
         DBCursor mongoDocuments = collection.find(dbObject);
         return (ArrayList<DBObject>) mongoDocuments.toArray();
    }
    
    
    //TODO mongo handlers
    public static void main(String[] args) throws SQLException {
    	
//    	ResultSet resultSet = executeSQLQuery("SELECT * FROM playground");
//		ResultSetMetaData rsmd = resultSet.getMetaData();
//		int columnsNumber = rsmd.getColumnCount();
//		while (resultSet.next()) {
//		    for (int i = 1; i <= columnsNumber; i++) {
//		        if (i > 1) System.out.print(",  ");
//		        String columnValue = resultSet.getString(i);
//		        System.out.print(columnValue + " " + rsmd.getColumnName(i));
//		    }
//		    System.out.println("");
//		}
//    	findMongoDocument("{'name' : 'tutorialspoint' }", "mycollection");
//        createMongoDocument("{'name' : 'tutorialspoint' }", "mycollection");
//        System.out.println("Collection myCollection selected successfully   " + collection.findOne());
    	insertMongoDocument("[ {'name': 'Kiran', 'age': '20'}, {'name': 'John'} ]", "mycollection");
        
	}
}
