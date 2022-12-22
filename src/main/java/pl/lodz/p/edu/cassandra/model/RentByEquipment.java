package pl.lodz.p.edu.cassandra.model;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(defaultKeyspace = "just_rent")
@CqlName("rentsByEquipment")
public class RentByEquipment implements Serializable {

    @CqlName("rentUuid")
    @ClusteringColumn(0)
    private UUID rentUuid;

    @CqlName("equipmentUuid")
    @PartitionKey
    private UUID equipmentUuid;

    @CqlName("clientUuid")
    private UUID clientUuid;

    @CqlName("beginTime")
    private LocalDateTime beginTime;

    @CqlName("endTime")
    @ClusteringColumn(1)
    private LocalDateTime endTime;

    @CqlName("shipped")
    private boolean shipped;

    @CqlName("eqReturned")
    private boolean eqReturned;


    public RentByEquipment(LocalDateTime beginTime, LocalDateTime endTime,
                        UUID equipment, UUID client) {

        this.rentUuid = UUID.randomUUID();
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shipped = false;
        this.eqReturned = false;
        this.equipmentUuid = equipment;
        this.clientUuid = client;
    }

    public RentByEquipment() {

    }
//
//    public double getRentCost() {
//        if (!eqReturned) {
//            return 0.0;
//        } else if (equipment.isMissing()) {
//            return equipment.getBail();
//        } else {
//            long diffDays = Math.abs(Duration.between(beginTime, endTime).toDays()); //If something works incorrectly, it's here
//            if (diffDays > 1) {
//                return equipment.getFirstDayCost() + equipment.getNextDaysCost() * (diffDays - 1);
//            } else {
//                return equipment.getFirstDayCost();
//            }
//        }
//    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("Rent{");
        sb.append("uuid=").append(rentUuid.toString());
        sb.append("Klient uuid=").append(getClientUuid().toString());
        sb.append("Sprzęt uuid=").append(getEquipmentUuid().toString());
        sb.append("Czas wypożyczenia=");
        sb.append("Początek=").append(beginTime);
        sb.append(" do ");
        sb.append("Koniec=").append(endTime);
        sb.append('}');
        return sb.toString();
    }


    public UUID getRentUuid() {
        return rentUuid;
    }

    public void setRentUuid(UUID rentUuid) {
        this.rentUuid = rentUuid;
    }

    public UUID getEquipmentUuid() {
        return equipmentUuid;
    }

    public void setEquipmentUuid(UUID equipmentUuid) {
        this.equipmentUuid = equipmentUuid;
    }

    public UUID getClientUuid() {
        return clientUuid;
    }

    public void setClientUuid(UUID clientUuid) {
        this.clientUuid = clientUuid;
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
}