import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class UserDao {
    private final MongoCollection<Document> user;

    public UserDao(MongoDatabase db) {
        user = db.getCollection("User");
    }

    public void insertUser(String name, String email) {
        Document document = new Document();
        document.append("Name", name);
        document.append("Email", email);
        user.insertOne(document);
    }
}
