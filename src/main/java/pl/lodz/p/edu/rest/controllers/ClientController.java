package pl.lodz.p.edu.rest.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.exception.ClientException;
import pl.lodz.p.edu.rest.managers.ClientManager;
import pl.lodz.p.edu.rest.model.Client;
import pl.lodz.p.edu.rest.model.idType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/clients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
//@Transactional(Transactional.TxType.REQUIRED) //db connection? idk
@RequestScoped
public class ClientController {

    @Inject
    private ClientManager clientManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path(("/"))
    public Response registerClient(Client client) {
        if (clientManager.registerClient(client)) {
            return Response.status(Response.Status.CREATED).entity(client).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }


//  FIXME BAWIĘ SIĘ TUTAJ PONIŻEJ, TAK SAMO Z CLIENTCREATIONREQUEST.
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/alt/")
//    public Response addClient(@Context UriInfo uriInfo, @Valid ClientCreationRequest ccr) {
//        if (entityManager.find(Client.class, ccr.getUuid() != null)) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//        URI uri = uriInfo.getAbsolutePathBuilder()
//                .path(ccr.getUuid().toString())
//                .build();
//        return Response.created(uri).build();
//    }

    @DELETE
    @Path("/{id}")
    public Response unregisterClient(@PathParam("id") UUID uuid) {
        if (clientManager.unregisterClient(uuid)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path(("/{id}"))
//    public Response updateClient(@PathParam("id") UUID uuid) {
//        return null;
//
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/{type}")
    public Response getClientByClientId(@PathParam("id") String clientId, @PathParam("type") idType idType) {
        Client client = clientManager.getByClientId(clientId, idType);
        if(client != null) {
            return Response.status(Response.Status.OK).entity(client).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getClientByUuid(@PathParam("uuid") UUID uuid) {
        Client client = clientManager.getClientByUuid(uuid);
        if(client != null) {
            return Response.status(Response.Status.OK).entity(client).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClients() {
        List<Client> clients = clientManager.getAllClients();
        return Response.status(Response.Status.OK).entity(clients).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/available")
    public Response getAllAvailableClients() {
        List<Client> all = clientManager.getAllClients();
        List<Client> available = new ArrayList<>();
        for (Client c : all) {
            if(!(c.isArchive())) {
                available.add(c);
            }
        }
        return Response.status(Response.Status.OK).entity(available).build();
    }
}
