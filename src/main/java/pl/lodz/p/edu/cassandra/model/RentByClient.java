package pl.lodz.p.edu.cassandra.model;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(defaultKeyspace = "just_rent")
@CqlName("rentsByClient")
public class RentByClient implements Serializable {

    @CqlName("rentUuid")
    @ClusteringColumn(0)
    private UUID rentUuid;

    @CqlName("equipmentUuid")
    private UUID equipmentUuid;

    @CqlName("clientUuid")
    @PartitionKey
    private UUID clientUuid;

    @CqlName("beginTime")
    @ClusteringColumn(1)
    private String beginTime;

    @CqlName("endTime")
    private String endTime;

    @CqlName("shipped")
    private boolean shipped;

    @CqlName("eqReturned")
    private boolean eqReturned;


    public RentByClient(LocalDateTime beginTime, LocalDateTime endTime,
                        UUID equipment, UUID client) {

        this.rentUuid = UUID.randomUUID();
        this.beginTime = beginTime.toString();
        this.endTime = endTime.toString();
        this.shipped = false;
        this.eqReturned = false;
        this.equipmentUuid = equipment;
        this.clientUuid = client;
    }

    public RentByClient(UUID rentUuid, UUID equipmentUuid, UUID clientUuid,
                        String beginTime, String endTime, boolean shipped, boolean eqReturned) {
        this.rentUuid = rentUuid;
        this.equipmentUuid = equipmentUuid;
        this.clientUuid = clientUuid;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shipped = shipped;
        this.eqReturned = eqReturned;
    }

    public RentByClient() {

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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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