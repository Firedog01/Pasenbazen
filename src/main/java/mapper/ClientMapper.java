package mapper;

import exception.ClientException;
import mgd.AddressMgd;

import model.Client;
import model.UniqueId;
import org.bson.Document;

import java.util.UUID;

import static model.Address_.*;
import static model.Client_.*;

public class ClientMapper {
    public static Document toMongoClient(Client client) {
        Document clientDocument = new Document(
                ID, client.getEntityId().getUniqueID())
                .append(CLIENT_ID, client.getClientId())
                .append(ID_TYPE, client.getIdType())
                .append(FIRST_NAME, client.getFirstName())
                .append(LAST_NAME, client.getLastName())
                .append(ADDRESS, client.getAddress().getEntityId().getUniqueID()
                );
        return clientDocument;
    }

    public static Client fromMongoClient(Document clientDocument, AddressMgd addressMgd) throws ClientException {
        Client clientModel = new Client(
                new UniqueId(clientDocument.get(ID, UUID.class)).getUniqueID(),
                clientDocument.getString(CLIENT_ID),
                clientDocument.get(ID_TYPE, model.idType.class),
                clientDocument.getString(FIRST_NAME),
                clientDocument.getString(LAST_NAME),
                AddressMapper.fromMongoAddress(addressMgd)
                );
        return clientModel;
    }
}
