package example;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoCredential;

import java.util.Iterator;

import org.bson.Document;


// origin: https://www.tutorialspoint.com/mongodb/mongodb_java
// TODO: switch to stub mongodb server https://github.com/bwaldvogel/mongo-java-server

public class BasicMongoDB {

	private final static String collectionName = "likes";

	public static void main(String args[]) {

		// Creating a Mongo client
		MongoClient mongo = new MongoClient("localhost", 27017);

		// Creating Credentials
		MongoCredential credential;
		credential = MongoCredential.createCredential("sampleUser", "myDb",
				"password".toCharArray());
		System.out.println("Connected to the database successfully");

		// Accessing the database
		MongoDatabase database = mongo.getDatabase("dummy");
		System.out.println("Credentials ::" + credential);

		// Creating a collection
		try {
			database.createCollection(collectionName);
			System.err.println(String.format("Collection \"%s\" created successfully",
					collectionName));
		} catch (MongoCommandException e) {
			System.err.println(
					String.format("Collection \"%s\" already exists", collectionName));

		} catch (Exception e) {
			System.out.println(
					String.format("Exception creating collection \"%s\":", collectionName)
							+ e.toString());
			throw new RuntimeException(e);
		}

		// Retrieving a collection
		MongoCollection<Document> collection = database
				.getCollection(collectionName);
		System.out.println(String.format("Collection \"%s\" selected successfully",
				collectionName));

		// Listing all collections in the database
		for (String name : database.listCollectionNames()) {
			System.out.println(name);
		}

		Document document = new Document("title", "MongoDB").append("id", 1)
				.append("description", "database").append("likes", 100)
				.append("url", "http://www.tutorialspoint.com/mongodb/")
				.append("by", "tutorials point");
		collection.insertOne(document);
		System.out.println("Document inserted successfully");

		collection.updateOne(Filters.eq("id", 1), Updates.set("likes", 150));
		System.out.println("Document update successfully...");

		// Getting the iterable object
		FindIterable<Document> iterDoc = collection.find();
		int i = 1;

		// Getting the iterator
		Iterator<Document> it = iterDoc.iterator();

		while (it.hasNext()) {
			System.out.println(it.next());
			i++;
		}
		// Deleting the documents
		collection.deleteOne(Filters.eq("id", 1));
		System.out.println("Document deleted successfully...");

		// Dropping a Collection
		collection.drop();
		System.out.println(String.format("Collection \"%s\" dropped successfully",
				collectionName));
	}
}
