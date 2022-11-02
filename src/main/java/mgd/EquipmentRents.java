package mgd;

import mgd.EQ.EquipmentMgd;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Collections;
import java.util.List;

public class EquipmentRents extends AbstractEntityMgd {

    @BsonProperty("equipment")
    private EquipmentMgd equipment;

    @BsonProperty("rent_list")
    private List<RentMgd> rentList;

    @BsonCreator
    public EquipmentRents(@BsonProperty("_id") UniqueIdMgd entityId,
                          @BsonProperty("equipment") EquipmentMgd equipment,
                          @BsonProperty("rent_list") List<RentMgd> rentList) {
        super(entityId);
        this.equipment = equipment;
        this.rentList = rentList;
    }

    public EquipmentMgd getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentMgd equipment) {
        this.equipment = equipment;
    }

    public List<RentMgd> getRentList() {
        return Collections.unmodifiableList(rentList);
    }

    public void append(RentMgd rent) {
        this.rentList.add(rent);
    }
    
    @Override
    public void close() throws Exception {

    }
}
