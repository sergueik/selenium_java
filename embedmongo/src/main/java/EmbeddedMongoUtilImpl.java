import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;

import java.io.IOException;

public class EmbeddedMongoUtilImpl implements EmbeddedMongoUtil {

    private final MongoDBRuntime runtime;
    private final MongodConfig mongoConfig;
    private MongodExecutable mongodExe;
    private MongodProcess mongod;

    public EmbeddedMongoUtilImpl(MongoDBRuntime runtime, MongodConfig mongodConfig) {
        this.runtime = runtime;
        this.mongoConfig = mongodConfig;
    }

    @Override
    public void start() {
        this.mongodExe = runtime.prepare(mongoConfig);
        try {
            this.mongod = mongodExe.start();
        } catch (IOException e) {
            throw new RuntimeException("Something is wrong with your test mongodb database");
        }
    }

    @Override
    public void stop(){
        mongod.stop();
        mongodExe.cleanup();
    }
}
