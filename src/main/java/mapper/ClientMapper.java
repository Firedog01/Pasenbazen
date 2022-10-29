package mapper;

import model.Client;
import org.bson.Document;

public class ClientMapper {
    public static Document toMongoClient(Client client) {
//        Document clientDocument = new Document(ID, client.getEntityId().getUniqueID())
//                .append()
        return new Document();
    }
}
