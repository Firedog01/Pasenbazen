package mgd;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import mgd.EQ.EquipmentMgd;
import model.Address;
import model.Client;
import model.EQ.Equipment;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.joda.time.Days;
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

    @BsonProperty("equipment")
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


    public double getRentCost() {
        if (!eqReturned) {
            return 0.0;
        } else if (equipment.isMissing()) {
            return equipment.getBail();
        } else {
            long diffDays = Math.abs(Days.daysBetween(beginTime, endTime).getDays());
            if (diffDays > 1) {
                return equipment.getFirstDayCost() + equipment.getNextDaysCost() * (diffDays - 1);
            } else {
                return equipment.getFirstDayCost();
            }
        }
    }

    public EquipmentMgd getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentMgd equipment) {
        this.equipment = equipment;
    }

    public ClientMgd getClient() {
        return client;
    }

    public void setClient(ClientMgd client) {
        this.client = client;
    }

    public AddressMgd getAddress() {
        return address;
    }

    public void setAddress(AddressMgd address) {
        this.address = address;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public boolean isShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    public boolean isEqReturned() {
        return eqReturned;
    }

    public void setEqReturned(boolean eqReturned) {
        this.eqReturned = eqReturned;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentMgd)) return false;

        RentMgd rentMgd = (RentMgd) o;

        if (shipped != rentMgd.shipped) return false;
        if (eqReturned != rentMgd.eqReturned) return false;
        if (equipment != null ? !equipment.equals(rentMgd.equipment) : rentMgd.equipment != null) return false;
        if (client != null ? !client.equals(rentMgd.client) : rentMgd.client != null) return false;
        if (address != null ? !address.equals(rentMgd.address) : rentMgd.address != null) return false;
        if (beginTime != null ? !beginTime.equals(rentMgd.beginTime) : rentMgd.beginTime != null) return false;
        return endTime != null ? endTime.equals(rentMgd.endTime) : rentMgd.endTime == null;
    }

    @Override
    public int hashCode() {
        int result = equipment != null ? equipment.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (shipped ? 1 : 0);
        result = 31 * result + (eqReturned ? 1 : 0);
        return result;
    }
}