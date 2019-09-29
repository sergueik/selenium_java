package example;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCommandException;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import example.models.Cart;
import example.models.Product;

import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;

import static com.mongodb.client.model.Updates.inc;

import static java.util.logging.Logger.getLogger;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Transactions {

	private static MongoClient client;
	private static MongoCollection<Cart> cartCollection;
	private static MongoCollection<Product> productCollection;

	private final BigDecimal BEER_PRICE = BigDecimal.valueOf(3);
	private final String BEER_ID = "beer";

	private final Bson stockUpdate = inc("stock", -2);
	private final Bson filterId = eq("_id", BEER_ID);
	private final Bson filterAlice = eq("_id", "Alice");
	private final Bson matchBeer = elemMatch("items", eq("productId", "beer"));
	private final Bson incrementBeers = inc("items.$.quantity", 2);

	public static void main(String[] args) {
		initMongoDB(args[0]);
		new Transactions().demo();
	}

	private static void initMongoDB(String mongodbURI) {
		getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
		CodecRegistry codecRegistry = fromRegistries(
				MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider
						.builder().register("com.mongodb.models").build()));
		MongoClientOptions.Builder options = new MongoClientOptions.Builder()
				.codecRegistry(codecRegistry);
		MongoClientURI uri = new MongoClientURI(mongodbURI, options);
		client = new MongoClient(uri);
		MongoDatabase db = client.getDatabase("test");
		cartCollection = db.getCollection("cart", Cart.class);
		productCollection = db.getCollection("product", Product.class);
	}

	private void demo() {
		clearCollections();
		insertProductBeer();
		printDatabaseState();
		System.out.println("#########  NO  TRANSACTION #########");
		System.out.println("Alice wants 2 beers.");
		System.out.println(
				"We have to create a cart in the 'cart' collection and update the stock in the 'product' collection.");
		System.out.println(
				"The 2 actions are correlated but can not be executed on the same cluster time.");
		System.out.println(
				"Any error blocking one operation could result in stock error or a sale of beer that we can't fulfill as we have no stock.");
		System.out.println(
				"---------------------------------------------------------------------------");
		aliceWantsTwoBeers();
		sleep();
		removingBeersFromStock();
		System.out.println("####################################\n");
		printDatabaseState();
		sleep();
		System.out.println("\n######### WITH TRANSACTION #########");
		System.out.println("Alice wants 2 extra beers.");
		System.out.println("Now we can update the 2 collections simultaneously.");
		System.out.println(
				"The 2 operations only happen when the transaction is committed.");
		System.out.println(
				"---------------------------------------------------------------------------");
		aliceWantsTwoExtraBeersInTransactionThenCommitOrRollback();
		sleep();
		System.out.println("\n######### WITH TRANSACTION #########");
		System.out.println("Alice wants 2 extra beers.");
		System.out.println(
				"This time we do not have enough beers in stock so the transaction will rollback.");
		System.out.println(
				"---------------------------------------------------------------------------");
		aliceWantsTwoExtraBeersInTransactionThenCommitOrRollback();
		client.close();
	}

	private void aliceWantsTwoExtraBeersInTransactionThenCommitOrRollback() {
		ClientSession session = client.startSession();
		try {
			session.startTransaction(TransactionOptions.builder()
					.writeConcern(WriteConcern.MAJORITY).build());
			aliceWantsTwoExtraBeers(session);
			sleep();
			removingBeerFromStock(session);
			session.commitTransaction();
		} catch (MongoCommandException e) {
			session.abortTransaction();
			System.out.println("####### ROLLBACK TRANSACTION #######");
		} finally {
			session.close();
			System.out.println("####################################\n");
			printDatabaseState();
		}
	}

	private void removingBeersFromStock() {
		System.out.println("Trying to update beer stock : -2 beers.");
		try {
			productCollection.updateOne(filterId, stockUpdate);
		} catch (MongoCommandException e) {
			System.out.println("#####   MongoCommandException  #####");
			System.out.println("##### STOCK CANNOT BE NEGATIVE #####");
			throw e;
		}
	}

	private void removingBeerFromStock(ClientSession session) {
		System.out.println("Trying to update beer stock : -2 beers.");
		try {
			productCollection.updateOne(session, filterId, stockUpdate);
		} catch (MongoCommandException e) {
			System.out.println("#####   MongoCommandException  #####");
			System.out.println("##### STOCK CANNOT BE NEGATIVE #####");
			throw e;
		}
	}

	private void aliceWantsTwoBeers() {
		System.out.println("Alice adds 2 beers in her cart.");
		cartCollection.insertOne(new Cart("Alice",
				Collections.singletonList(new Cart.Item(BEER_ID, 2, BEER_PRICE))));
	}

	private void aliceWantsTwoExtraBeers(ClientSession session) {
		System.out.println("Updating Alice cart : adding 2 beers.");
		cartCollection.updateOne(session, and(filterAlice, matchBeer),
				incrementBeers);
	}

	private void insertProductBeer() {
		productCollection.insertOne(new Product(BEER_ID, 5, BEER_PRICE));
	}

	private void clearCollections() {
		productCollection.deleteMany(new BsonDocument());
		cartCollection.deleteMany(new BsonDocument());
	}

	private void printDatabaseState() {
		System.out.println("Database state:");
		printProducts(productCollection.find().into(new ArrayList<>()));
		printCarts(cartCollection.find().into(new ArrayList<>()));
		System.out.println();
	}

	private void printProducts(List<Product> products) {
		products.forEach(System.out::println);
	}

	private void printCarts(List<Cart> carts) {
		if (carts.isEmpty())
			System.out.println("No carts...");
		else
			carts.forEach(System.out::println);
	}

	private void sleep() {
		System.out.println("Sleeping 3 seconds...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.err.println("Oups...");
			e.printStackTrace();
		}
	}
}
