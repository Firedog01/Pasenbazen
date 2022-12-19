package pl.lodz.p.edu.cassandra.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import pl.lodz.p.edu.cassandra.NbdAddressTranslator;

import java.net.InetSocketAddress;

public abstract class AbstractRepository<T> implements AutoCloseable, Repository<T> {

    protected static CqlSession session;

    public AbstractRepository() {
        initSession();
    }

    public void initSession() {
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .addContactPoint(new InetSocketAddress("localhost", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandra")
                .build();
    }

    public CqlSession getSession() {
        return session;
    }
}