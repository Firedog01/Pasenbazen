package repository;

import com.mongodb.*;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.jsr310.LocalDateTimeCodec;
import repository.codec.EquipmentMgdCodecProvider;
import repository.codec.LocalDateTimeCodecProvider;
import repository.codec.UniqueIdCodecProvider;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

public abstract class AbstractRepository<T> implements AutoCloseable, Repository<T> {

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
                        CodecRegistries.fromProviders(new EquipmentMgdCodecProvider()),
                        CodecRegistries.fromProviders(new LocalDateTimeCodecProvider()),
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
        try {
            db.createCollection("clients");
        } catch(MongoCommandException e) {
            // already exist
        }
        try {
            db.createCollection("equipment");
        } catch(MongoCommandException e) {
            // already exist
        }
        try {
            db.createCollection("rents");
        } catch(MongoCommandException e) {
            // already exist
        }

    }

    public MongoDatabase getDb() {
        return db;
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }


    public static ClientSession startNewSession() {
        return getMongoClient().startSession();
    }

    public static TransactionOptions getTransactionOptions() {
        return TransactionOptions.builder()
                .readPreference(ReadPreference.primaryPreferred())
                .readConcern(ReadConcern.LOCAL)
                .writeConcern(WriteConcern.MAJORITY)
                .build();
    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }

}
