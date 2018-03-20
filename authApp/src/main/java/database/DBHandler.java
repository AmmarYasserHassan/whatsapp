package database;

import com.mongodb.util.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

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
	public static JSONObject executeSQLQuery(String query) {
		Connection connection = postgresqlDBConnection.connect();
		JSONObject result = new JSONObject();
		try {
			Statement statement = connection.createStatement();
			JSONArray data = convertToJSONArray(statement.executeQuery(query));
			result.put("error",false);
			result.put("data", data);
		} catch (SQLException e) {
			result.put("error",true);
			result.put("error_message", e.getMessage());
		} finally {
			postgresqlDBConnection.disconnct();
		}
		return result;
	}

	public static boolean insertMongoDocument(String jsonDocument, String collectionName) {
		DBCollection collection = mongoDB.getCollection(collectionName);
		DBObject dbObject = (DBObject) JSON.parse(jsonDocument);
		try {
			collection.insert(dbObject);
		}
		// Couldn't insert document for any reason
		catch (MongoException mongoException) {
			return false;
		}
		return true;
	}

	// public static boolean updateMongoDocument(String
	// attributesToFindDocumentWith,String attributesToUpdateDocumentWith,
	// String collectionName)
	// {
	// DBCollection collection = mongoDB.getCollection(collectionName);
	// DBObject dbObjectToFind =
	// (DBObject)JSON.parse(attributesToFindDocumentWith);
	//
	// try{
	//
	// }
	// catch
	// try {
	// collection.update(attributesToFindDocumentWith,attributesToFindDocumentWith);
	// }
	// //Couldn't insert document for any reason
	// catch(MongoException mongoException)
	// {
	// return false;
	// }
	// return true;
	public static boolean insertAllMongoDocuments(ArrayList<String> jsonDocuments, String collectionName) {
		DBCollection collection = mongoDB.getCollection(collectionName);
		ArrayList<DBObject> dbObjects = new ArrayList<DBObject>();
		for (String document : jsonDocuments)
			dbObjects.add((DBObject) JSON.parse(document));

		try {
			collection.insert(dbObjects);
		}
		// Couldn't insert document for any reason
		catch (MongoException mongoException) {
			return false;
		}
		return true;

	}

	public static DBObject findMongoDocument(String jsonDocument, String collectionName) throws MongoException {
		DBCollection collection = mongoDB.getCollection(collectionName);
		DBObject dbObject = (DBObject) JSON.parse(jsonDocument);
		DBObject mongoDocument = collection.findOne(dbObject);

		if (mongoDocument == null)
			throw new MongoException("Document does not exit");

		return mongoDocument;

	}


	public static ArrayList<DBObject> findAllMongoDocuments(String jsonDocument, String collectionName)
			throws MongoException {
		// ArrayList<DBOject>
		DBCollection collection = mongoDB.getCollection(collectionName);
		DBObject dbObject = (DBObject) JSON.parse(jsonDocument);
		DBCursor mongoDocuments = collection.find(dbObject);

		if (mongoDocuments.size() == 0)
			throw new MongoException("Document does not exit");

		return (ArrayList<DBObject>) mongoDocuments.toArray();
	}

	public static JSONArray convertToJSONArray(ResultSet resultSet) throws SQLException {
		JSONArray parsedResult = new JSONArray();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (resultSet.next()) {
			JSONObject jsonObject = new JSONObject();
			for (int i = 1; i <= columnsNumber; i++) {
				String columnName = rsmd.getColumnName(i);
				jsonObject.put(columnName, resultSet.getString(columnName));
			}
			parsedResult.put(jsonObject);
		}
		return parsedResult;
	}

	public static void main(String[] args) throws SQLException {

		//ResultSet resultSet = executeSQLQuery("SELECT * FROM playground");
		
//		JSONArray ja = convertToJSONArray(resultSet);
//		System.out.println(ja);
		
//		ResultSetMetaData rsmd = resultSet.getMetaData();
//		int columnsNumber = rsmd.getColumnCount();
//		while (resultSet.next()) {
//			for (int i = 1; i <= columnsNumber; i++) {
//
//				if (i > 1)
//					System.out.print(",  ");
//				String columnValue = resultSet.getString(i);
//				System.out.print(columnValue + " " + rsmd.getColumnName(i));
//			}
//			System.out.println("");
//		}
		// System.out.println(findAllMongoDocuments("{}", "mycollection"));
		// insertMongoDocument("{'name' : 'tutorialspoint' }", "mycollection");
		// System.out.println("Collection myCollection selected successfully " +
		// collection.findOne());
		// insertMongoDocument("{'name': 'kiran', 'age': '20'}",
		// "mycollection");
		// ArrayList<String> docs = new ArrayList<String>();
		// docs.add("{'name': 'misho1'}");
		// docs.add("{'name': 'misho2'}");
		// docs.add("{'name': 'misho3'}");
		// insertAllMongoDocuments(docs,"mycollection");

	}
}
