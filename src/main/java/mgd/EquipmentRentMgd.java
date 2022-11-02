package mgd;

import mgd.EQ.EquipmentMgd;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Collections;
import java.util.List;

// -> EquipmentRentMgd
public class EquipmentRentMgd extends AbstractEntityMgd {

    @BsonProperty("equipment")
    private EquipmentMgd equipment;

    @BsonProperty("rent")
    private RentMgd rent;

    @BsonCreator
    public EquipmentRentMgd(@BsonProperty("_id") UniqueIdMgd entityId,
                            @BsonProperty("equipment") EquipmentMgd equipment,
                            @BsonProperty("rent") RentMgd rent) {
        super(entityId);
        this.equipment = equipment;
        this.rent = rent;
    }

    public EquipmentRentMgd(EquipmentMgd equipment, RentMgd rent) {
        super(new UniqueIdMgd());
        this.equipment = equipment;
        this.rent = rent;
    }

    public EquipmentMgd getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentMgd equipment) {
        this.equipment = equipment;
    }

    public RentMgd getRent() {
        return rent;
    }


    @Override
    public void close() throws Exception {

    }
}
