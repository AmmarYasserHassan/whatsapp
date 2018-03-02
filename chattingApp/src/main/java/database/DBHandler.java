package database;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.mongodb.*;
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

    
    public static boolean insertMongoDocument(String jsonDocument, String collectionName)
    {
        DBCollection collection = mongoDB.getCollection(collectionName);
        DBObject dbObject = (DBObject)JSON.parse(jsonDocument);
        try {
			collection.insert(dbObject);
		}
		//Couldn't insert document for any reason
		catch(MongoException mongoException)
		{
			return false;
		}
        return true;
    }

//	public static boolean updateMongoDocument(String attributesToFindDocumentWith,String attributesToUpdateDocumentWith, String collectionName)
//	{
//		DBCollection collection = mongoDB.getCollection(collectionName);
//		DBObject dbObjectToFind = (DBObject)JSON.parse(attributesToFindDocumentWith);
//
//		try{
//
//		}
//		catch
//		try {
//			collection.update(attributesToFindDocumentWith,attributesToFindDocumentWith);
//		}
//		//Couldn't insert document for any reason
//		catch(MongoException mongoException)
//		{
//			return false;
//		}
//		return true;
//	}

    public static boolean insertAllMongoDocuments(ArrayList<String> jsonDocuments, String collectionName)
    {
        DBCollection collection = mongoDB.getCollection(collectionName);
        ArrayList<DBObject> dbObjects =  new ArrayList<DBObject>();
        for(String document:jsonDocuments)
        dbObjects.add((DBObject)JSON.parse(document));

		try {
			collection.insert(dbObjects);
		}
		//Couldn't insert document for any reason
		catch(MongoException mongoException)
		{
			return false;
		}
		return true;

    }


    public static DBObject findMongoDocument(String jsonDocument, String collectionName) throws MongoException
    {
    	 DBCollection collection = mongoDB.getCollection(collectionName);
         DBObject dbObject = (DBObject)JSON.parse(jsonDocument);
         DBObject mongoDocument = collection.findOne(dbObject);

         if(mongoDocument==null)
         	throw new MongoException("Document does not exit");

         return mongoDocument;


    }
    public static ArrayList<DBObject> findAllMongoDocuments(String jsonDocument, String collectionName) throws MongoException
    {
    	//ArrayList<DBOject>
    	 DBCollection collection = mongoDB.getCollection(collectionName);
         DBObject dbObject = (DBObject)JSON.parse(jsonDocument);
         DBCursor mongoDocuments = collection.find(dbObject);

		 if(mongoDocuments.size()==0)
			throw new MongoException("Document does not exit");

         return (ArrayList<DBObject>) mongoDocuments.toArray();
    }
    
    
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
   System.out.println(findAllMongoDocuments("{}", "mycollection"));
		//insertMongoDocument("{'name' : 'tutorialspoint' }", "mycollection");
//        System.out.println("Collection myCollection selected successfully   " + collection.findOne());
    	//insertMongoDocument("{'name': 'kiran', 'age': '20'}", "mycollection");
//		ArrayList<String> docs = new ArrayList<String>();
//		docs.add("{'name': 'misho1'}");
//		docs.add("{'name': 'misho2'}");
//		docs.add("{'name': 'misho3'}");
	//	insertAllMongoDocuments(docs,"mycollection");
        
	}
}
