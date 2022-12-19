import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.cassandra.repository.impl.ClientRepository;

public class CassandraTest {

    ClientRepository clientRepository = new ClientRepository();
    
    @Disabled
    @Test
    void connectionTest() {
        if (clientRepository.getSession().isClosed()) {
            System.out.println("no connection");
            return;
        }
        System.out.println("yes connection");
    }
}
