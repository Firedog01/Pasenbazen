//package pl.lodz.p.edu.cassandra.repository;
//
//import com.datastax.oss.driver.api.core.CqlIdentifier;
//import com.datastax.oss.driver.api.core.CqlSession;
//import com.datastax.oss.driver.api.core.cql.SimpleStatement;
//import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
//import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
//import pl.lodz.p.edu.cassandra.repository.Schemas.SchemaConst;
//
//import java.net.InetSocketAddress;
//
//
//
//public abstract class AbstractRepository<T> implements AutoCloseable, Repository<T> {
//
//    protected static CqlSession session;
//
//    public AbstractRepository() {
//        // here?
//        CreateKeyspace keyspace = SchemaBuilder.createKeyspace(SchemaConst.JUST_RENT_NAMESPACE)
//                .ifNotExists()
//                .withSimpleStrategy(2)
//                .withDurableWrites(true);
//        SimpleStatement createKeySpace = keyspace.build();
//        session.execute(createKeySpace);
//    }
//
//
//
//    public CqlSession getSession() {
//        return session;
//    }
//
//}