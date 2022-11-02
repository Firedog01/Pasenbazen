package mapper;

import mgd.AddressMgd;
import model.Address;
import model.UniqueId;
import org.bson.Document;

import java.util.UUID;

import static model.Address_.*;


public class AddressMapper {
    public static Document toMongoAddress(Address address) {
        Document addressDocument = new Document(
                ID, address.getEntityId().getUniqueID())
                .append(CITY, address.getCity())
                .append(STREET, address.getStreet())
                .append(STREET_NR, address.getStreetNr()
        );
        return addressDocument;
    }

    public static Address fromMongoAddress(AddressMgd addressMgd) {
        Address addressModel = new Address(
//                new UniqueId(addressDocument.get(ID, UUID.class)).getUniqueID(),
//                addressDocument.getString(CITY),
//                addressDocument.getString(STREET),
//                addressDocument.getString(STREET_NR)
                addressMgd.getEntityId().getUuid(),
                addressMgd.getCity(),
                addressMgd.getStreet(),
                addressMgd.getStreetNr()
        );
        return addressModel;
    }

}
