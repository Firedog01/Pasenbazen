package mgd;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import mgd.EQ.EquipmentMgd;
import model.Address;
import model.Client;
import model.EQ.Equipment;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.joda.time.LocalDateTime;

import java.util.Objects;

public class RentMgd extends AbstractEntityMgd {

    @BsonCreator
    public RentMgd(@BsonProperty("_id") UniqueIdMgd entityId,
                   @BsonProperty("beginTime") LocalDateTime beginTime,
                   @BsonProperty("endTime") LocalDateTime endTime,
                   @BsonProperty("equipment") EquipmentMgd equipment,
                   @BsonProperty("client") ClientMgd client,
                   @BsonProperty("address") AddressMgd address) {
        super(entityId);
        this.equipment = equipment;
        this.client = client;
        this.address = address;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shipped = false;
        this.eqReturned = false;
    }

    @BsonProperty("eq")
    private EquipmentMgd equipment;

    @BsonProperty("client")
    private ClientMgd client;

    @BsonProperty("address")
    private AddressMgd address;

    @BsonProperty("beginTime")
    private LocalDateTime beginTime;

    @BsonProperty("endTime")
    private LocalDateTime endTime;

    @BsonProperty("shipped")
    private boolean shipped;

    @BsonProperty("eqReturned")
    private boolean eqReturned;


    public EquipmentMgd getEquipment() {
        return equipment;
    }

    public ClientMgd getClient() {
        return client;
    }

    public AddressMgd getAddress() {
        return address;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isShipped() {
        return shipped;
    }

    public boolean isEqReturned() {
        return eqReturned;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RentMgd{");
        sb.append("equipment=").append(equipment);
        sb.append(", client=").append(client);
        sb.append(", address=").append(address);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", shipped=").append(shipped);
        sb.append(", eqReturned=").append(eqReturned);
        sb.append('}');
        return sb.toString();
    }


    @Override
    public void close() throws Exception {

    }
}
