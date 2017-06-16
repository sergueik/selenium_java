import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;
import org.bson.Document;
import org.junit.Test;

public class UserDaoIntegrationTest {
    private static final String DB_NAME = "testDB";
    private static final String LOCALHOST = "localhost";
    private static final String TABLE_NAME = "testTable";
    private static final int testPort = 12345;
    private MongoClient mongo;
    private MongoDatabase db;
    private MongoCollection<Document> collection;
    private EmbeddedMongoUtilImpl embeddedMongoUtil;
    private EmbeddedMongoConfig embeddedMongoConfig;

    public void setup() {
        MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();
        this.embeddedMongoConfig = new EmbeddedMongoConfig();
        boolean ipv6 = embeddedMongoConfig.localhostIsIPv6WithOutException();
        MongodConfig mongodConfig = new MongodConfig(Version.V2_1_2, 12345, ipv6);
        this.embeddedMongoUtil = new EmbeddedMongoUtilImpl(runtime, mongodConfig);
        mongo = embeddedMongoConfig.getMongoInstanceWithoutException(LOCALHOST, testPort);
        embeddedMongoUtil.start();
        db = mongo.getDatabase(DB_NAME);
        collection = db.getCollection(TABLE_NAME);
    }

    public void tearDown() {
        embeddedMongoUtil.stop();
    }

    @Test
    public void InsertANameAndEmailIntheDatabase() {
        setup();
        UserDao userDao = new UserDao(db);
        String name = "TestUser";
        String email = "TestUser@test.com";
        userDao.insertUser(name, email);
        tearDown();
    }
}
