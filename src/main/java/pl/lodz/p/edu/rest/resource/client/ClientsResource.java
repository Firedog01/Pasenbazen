package pl.lodz.p.edu.rest.resource.client;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.*;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.repository.RepositoryFactory;
import pl.lodz.p.edu.rest.repository.RepositoryType;
import pl.lodz.p.edu.rest.repository.impl.ClientRepository;

import java.util.ArrayList;
import java.util.List;

@Path("/clients")
public class ClientsResource {

    RepositoryFactory repositoryFactory = new RepositoryFactory(
            Persistence.createEntityManagerFactory("POSTGRES_DB_PERSIST"));

    @GET
    @Produces("application/json")
    public List<Client> getAllClient() {
        ClientRepository clientRepository = (ClientRepository)
                repositoryFactory.getRepository(RepositoryType.ClientRepository);
        return clientRepository.getAll();
    }

    @POST
    public void createClient(Client client) {
        return;
    }

    @PUT
    public void upsertManyClients() {

    }
}
