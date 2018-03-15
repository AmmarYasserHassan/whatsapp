package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.*;
import com.mongodb.util.JSON;

import org.json.*;

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

	public static JSONObject insertMongoDocument(String jsonDocument, String collectionName) {
		DBCollection collection = mongoDB.getCollection(collectionName);
		DBObject dbObject = (DBObject) JSON.parse(jsonDocument);
		JSONObject result = new JSONObject();
		try {
			collection.insert(dbObject);
			result.put("error",false);
		}
		// Couldn't insert document for any reason
		catch (MongoException mongoException) {
			result.put("error",true);
			result.put("error_message", mongoException.getMessage());
		}

		return result;
	}
	
	public static JSONObject insertAllMongoDocuments(ArrayList<String> jsonDocuments, String collectionName) {
		DBCollection collection = mongoDB.getCollection(collectionName);
		ArrayList<DBObject> dbObjects = new ArrayList<DBObject>();
		for (String document : jsonDocuments)
			dbObjects.add((DBObject) JSON.parse(document));
		JSONObject result = new JSONObject();
		try {
			collection.insert(dbObjects);
			result.put("error",false);
		}
		// Couldn't insert document for any reason
		catch (MongoException mongoException) {
			result.put("error",true);
			result.put("error_message", mongoException.getMessage());
		}
		return result;

	}

	public static JSONObject findMongoDocument(String jsonDocument, String collectionName) throws MongoException {

		DBCollection collection = mongoDB.getCollection(collectionName);
		DBObject dbObject = (DBObject) JSON.parse(jsonDocument);
		DBObject mongoDocument = collection.findOne(dbObject);
		JSONObject result = new JSONObject();

		if (mongoDocument == null) {
			result.put("error",true);
			result.put("error_message", "Document does not exit");
		}
		else {
			result.put("error",false);
			result.put("data", JSON.parse(mongoDocument.toString()));
		}
		return result;
	}

	public static JSONObject findAllMongoDocuments(String jsonDocument, String collectionName) throws MongoException {
		DBCollection collection = mongoDB.getCollection(collectionName);
		DBObject dbObject = (DBObject) JSON.parse(jsonDocument);
		DBCursor mongoDocuments = collection.find(dbObject);
		JSONObject result = new JSONObject();

		if (mongoDocuments.size() == 0) {
			result.put("error",true);
			result.put("error_message", "Documents do not exit");
		}
		else {
			result.put("error",false);
			result.put("data", JSON.parse(((ArrayList<DBObject>) mongoDocuments.toArray()).toString()));
		}

		return result;
	}

	public static void main(String[] args) throws SQLException {

		//ResultSet resultSet = executeSQLQuery("SELECT * FROM playground");

		 //System.out.println(findAllMongoDocuments("{}", "mycollection"));
		  //insertMongoDocument("{'name' : 'ammar' }", "mycollection");
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
