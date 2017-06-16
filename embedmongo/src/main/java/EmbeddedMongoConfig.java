import com.mongodb.MongoClient;
import de.flapdoodle.embedmongo.runtime.Network;

import java.net.UnknownHostException;

public class EmbeddedMongoConfig {
    public EmbeddedMongoConfig() {
    }

    MongoClient getMongoInstanceWithoutException(String localhost, int testPort) {
        return new MongoClient(localhost, testPort);
    }

    boolean localhostIsIPv6WithOutException() {
        try {
            return Network.localhostIsIPv6();
        } catch (UnknownHostException e) {
            throw new RuntimeException("There is something wrong with the network and I am unable to tell you if the local host ip is ipv6");
        }
    }

}
