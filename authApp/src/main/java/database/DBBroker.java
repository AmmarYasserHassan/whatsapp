package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mongodb.*;
import com.mongodb.util.JSON;

import org.json.*;

public class DBBroker {

	Connection postgresqlDBConnection;
	MongoDBConnection mongoDBConnection;
	DB mongoDB;

	public DBBroker(Connection postgresqlDBConnection, DB mongoDBConnection) {
		this.postgresqlDBConnection = postgresqlDBConnection;
//		this.mongoDBConnection = mongoDBConnection;
		this.mongoDB = mongoDBConnection;
	}


	/**
	 * Execute sql query by the postgreSQL Database.
	 *
	 * @param query
	 * @return JSONObject, if error == false then data is returned successfully, if error == true then further info in error_message
	 *
	 */
	public JSONObject executeSQLQuery(String query) throws SQLException {
		Connection connection = postgresqlDBConnection;
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
			postgresqlDBConnection.close();
		}
		return result;
	}


	public JSONArray convertToJSONArray(ResultSet resultSet) throws SQLException {
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

	/**
	 * inserts a single document in a mongo collection.
	 *
	 * @param jsonDocument in a string format
	 * @param collectionName string name of the collection to be inserted int
	 * @return JSONObject, if error == false then data is returned successsfully, if error == true then further info in error_message
	 *
	 */
	public JSONObject insertMongoDocument(String jsonDocument, String collectionName) {
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
	public JSONObject updateMongoDocument(String jsonQueryDocument, String jsonUpdateDocument, String collectionName) {
		DBCollection collection = mongoDB.getCollection(collectionName);
		DBObject queryDbObject = (DBObject) JSON.parse(jsonQueryDocument);
		DBObject updateDbObject = (DBObject) JSON.parse(jsonUpdateDocument);
		JSONObject result = new JSONObject();
		try {
			collection.update(queryDbObject, updateDbObject);
			result.put("error",false);
		}
		// Couldn't insert document for any reason
		catch (MongoException mongoException) {
			result.put("error",true);
			result.put("error_message", mongoException.getMessage());
		}
		return result;
	}

	/**
	 * insert multiple documents in a mongo collection.
	 *
	 * @param jsonDocuments arraylist of json objects in string format
	 * @param collectionName string name of the collection to be inserted int
	 * @return JSONObject, if error == false then data is returned successsfully, if error == true then further info in error_message
	 *
	 */
	public JSONObject insertAllMongoDocuments(ArrayList<String> jsonDocuments, String collectionName) {
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


	/**
	 * finds a single document in a mongo collection.
	 *
	 * @param jsonDocument  in string format to be matched with other documents
	 * @param collectionName string name of the collection to be inserted int
	 * @return JSONObject, if error == false then data is returned successsfully, if error == true then further info in error_message
	 *
	 */
	public JSONObject findMongoDocument(String jsonDocument, String collectionName) throws MongoException {

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

	/**
	 * find  documents in a mongo collection.
	 *
	 * @param jsonDocument  in string format to be matched with other documents
	 * @param collectionName string name of the collection to be inserted int
	 * @return JSONObject, if error == false then data is returned successsfully, if error == true then further info in error_message
	 *
	 */
	public JSONObject findAllMongoDocuments(String jsonDocument, String collectionName) throws MongoException {
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

//		JSONObject resultSet = executeSQLQuery("SELECT * FROM playground");
//		System.out.println(resultSet);
		//System.out.println(findAllMongoDocuments("{}", "mycollection"));
//		 insertMongoDocument("{'name' : 'ammar' }", "mycollection");
//		 updateMongoDocument("{'name' : 'ammar' }", "{$set:{'gender' : 'fezo'}}", "mycollection");
//		 System.out.println(findMongoDocument("{'name' : 'ammar' }","mycollection"));
		// insertMongoDocument("{'name': 'kiran', 'age': '20'}",
		// "mycollection");
		// ArrayList<String> docs = new ArrayList<String>();
		// docs.add("{'name': 'misho1'}");
		// docs.add("{'name': 'misho2'}");
		// docs.add("{'name': 'misho3'}");
		// insertAllMongoDocuments(docs,"mycollection");


	}
}
