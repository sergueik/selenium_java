package example;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;

import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;

import static java.util.logging.Logger.getLogger;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import example.models.Cart;
import example.models.Product;

public class ChangeStreams {

	private static final Bson filterUpdate = Filters.eq("operationType",
			"update");
	private static final Bson filterInsertUpdate = Filters.in("operationType",
			"insert", "update");
	private static final String jsonSchema = "{ $jsonSchema: { bsonType: \"object\", required: [ \"_id\", \"price\", \"stock\" ], properties: { _id: { bsonType: \"string\", description: \"must be a string and is required\" }, price: { bsonType: \"decimal\", minimum: 0, description: \"must be a positive decimal and is required\" }, stock: { bsonType: \"int\", minimum: 0, description: \"must be a positive integer and is required\" } } } } ";

	public static void main(String[] args) {
		MongoDatabase db = initMongoDB(args[0]);
		MongoCollection<Cart> cartCollection = db.getCollection("cart", Cart.class);
		MongoCollection<Product> productCollection = db.getCollection("product",
				Product.class);
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(() -> watchChangeStream(productCollection, filterUpdate));
		executor
				.submit(() -> watchChangeStream(cartCollection, filterInsertUpdate));
		ScheduledExecutorService scheduled = Executors
				.newSingleThreadScheduledExecutor();
		scheduled.scheduleWithFixedDelay(System.out::println, 0, 1,
				TimeUnit.SECONDS);
	}

	private static void watchChangeStream(MongoCollection<?> collection,
			Bson filter) {
		System.out.println("Watching " + collection.getNamespace());
		List<Bson> pipeline = Collections.singletonList(Aggregates.match(filter));
		collection.watch(pipeline).fullDocument(FullDocument.UPDATE_LOOKUP)
				.forEach((Consumer<ChangeStreamDocument<?>>) doc -> System.out
						.println(doc.getClusterTime() + " => " + doc.getFullDocument()));
	}

	private static MongoDatabase initMongoDB(String mongodbURI) {
		getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
		CodecRegistry providers = fromProviders(
				PojoCodecProvider.builder().register("com.mongodb.models").build());
		CodecRegistry codecRegistry = fromRegistries(
				MongoClient.getDefaultCodecRegistry(), providers);
		MongoClientOptions.Builder options = new MongoClientOptions.Builder()
				.codecRegistry(codecRegistry);
		MongoClientURI uri = new MongoClientURI(mongodbURI, options);
		MongoClient client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase("test");
		db.drop();
		db.createCollection("cart");
		db.createCollection("product", productJsonSchemaValidator());
		return db;
	}

	private static CreateCollectionOptions productJsonSchemaValidator() {
		return new CreateCollectionOptions().validationOptions(
				new ValidationOptions().validationAction(ValidationAction.ERROR)
						.validator(BsonDocument.parse(jsonSchema)));
	}
}
