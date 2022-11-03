package repository;

import com.mongodb.*;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import mgd.ClientMgd;
import repository.codec.UniqueIdCodecProvider;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

public abstract class AbstractRepository implements AutoCloseable {

    private ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    private MongoCredential credential = MongoCredential.createCredential("nbd", "admin", "nbdpassword".toCharArray());
    private CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());

    private static MongoClient mongoClient;
    private MongoDatabase db;

    public AbstractRepository() {
        initConnection();
    }

    private void initConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        CodecRegistries.fromProviders(new UniqueIdCodecProvider()),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry
                ))
                .build();
        mongoClient = MongoClients.create(settings);
        db = mongoClient.getDatabase("db1");
        if(firstInstance)
            initDatabase();
        firstInstance = false;
    }

    private static boolean firstInstance = true;
    private void initDatabase() {
        db.createCollection("clients");
        db.createCollection("equipment");
        db.createCollection("rents");
    }

    public MongoDatabase getDb() {
        return db;
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }


    public static ClientSession getNewSession() {
        return getMongoClient().startSession();
    }

    public static void stopSession() {
        getMongoClient().close();
    }

    public static TransactionOptions getTransactionOptions() {
        return TransactionOptions.builder()
                .readPreference(ReadPreference.primary())
                .readConcern(ReadConcern.LOCAL)
                .writeConcern(WriteConcern.MAJORITY)
                .build();
    }


    @Override
    public void close() throws Exception {
        mongoClient.close();
    }

}
