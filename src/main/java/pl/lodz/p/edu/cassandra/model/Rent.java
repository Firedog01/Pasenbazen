package pl.lodz.p.edu.cassandra.model;

import pl.lodz.p.edu.cassandra.model.EQ.Equipment;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;


public class Rent implements Serializable {

    private UUID rentUuid;

    private Equipment equipment;

    private Client client;


    private Address shippingAddress;


    private LocalDateTime beginTime;


    private LocalDateTime endTime;


    private boolean shipped;


    private boolean eqReturned;


    public Rent(LocalDateTime beginTime, LocalDateTime endTime,
                Equipment equipment, Client client, Address shippingAddress) {

        this.rentUuid = UUID.randomUUID();
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shipped = false;
        this.eqReturned = false;
        this.equipment = equipment;
        this.client = client;
        this.shippingAddress = shippingAddress;
    }

    protected Rent() {}

    public double getRentCost() {
        if (!eqReturned) {
            return 0.0;
        } else if (equipment.isMissing()) {
            return equipment.getBail();
        } else {
            long diffDays= Math.abs(Duration.between(beginTime, endTime).toDays()); //If something works incorrectly, it's here
            if (diffDays > 1) {
                return equipment.getFirstDayCost() + equipment.getNextDaysCost() * (diffDays - 1);
            } else {
                return equipment.getFirstDayCost();
            }
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("Rent{");
        sb.append("uuid=").append(rentUuid.toString());
        sb.append("Klient=").append(getClient().toString());
        sb.append("Sprzęt=").append(getEquipment().toString());
        sb.append("Adres dostawy= ").append(shippingAddress);
        sb.append("Czas wypożyczenia=");
        sb.append("Początek=").append(beginTime);
        sb.append(" do ");
        sb.append("Koniec=").append(endTime);
        sb.append('}');
        return sb.toString();
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

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}