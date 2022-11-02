package mapper;

import mgd.ClientMgd;
import mgd.EQ.EquipmentMgd;
import model.Address;
import model.Client;
import model.EQ.Equipment;
import model.Rent;
import model.UniqueId;
import org.bson.Document;
import org.joda.time.LocalDateTime;


import java.util.UUID;

import static model.Client_.ADDRESS;
import static model.Rent_.*;

public class RentMapper {
    public static Document toMongoRent(Rent rent) {
        Document rentDocument = new Document(ID, rent.getEntityId().getUniqueID())
                .append(CLIENT, rent.getClient().getEntityId().getUniqueID())
                .append(ADDRESS, rent.getClient().getAddress().getEntityId().getUniqueID())
                .append(EQUIPMENT, rent.getEquipment().getEntityId().getUniqueID())
                .append(BEGIN_TIME, rent.getBeginTime())
                .append(END_TIME, rent.getEndTime());
        //COST?
        return rentDocument;
    }

    public static Rent fromMongoRent(Document rentDocument, EquipmentMgd equipmentMgd, ClientMgd clientMgd) {
        return null;
    }
}
