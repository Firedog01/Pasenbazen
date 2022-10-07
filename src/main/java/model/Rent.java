package model;

import model.EQ.Equipment;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;

public class Rent {

    private int id;

    private org.joda.time.LocalDateTime beginTime;
    private org.joda.time.LocalDateTime endTime;

    private boolean shipped;

    private boolean eqReturned;

    private Equipment equipment;

    private Client client;

    private Address shippingAddress;

    public Rent(int id, org.joda.time.LocalDateTime beginTime, org.joda.time.LocalDateTime endTime,
                Equipment equipment, Client client, Address shippingAddress) {
        this.id = id; // W jaki sposób to id ma być generowane?
        this.beginTime = beginTime;
        // this.beginTime = LocalDateTime.now();  FIXME ?
        this.endTime = endTime;
        this.shipped = false;
        this.eqReturned = false;
        this.equipment = equipment;
        this.client = client;
        this.shippingAddress = shippingAddress;
    }

    public double getRentCost() {
        if (!eqReturned) {
            return 0.0;
        } else if (equipment.isMissing()) {
            return equipment.getBail();
        } else {
            long diffDays= Math.abs(Days.daysBetween(beginTime, endTime).getDays());
            // Nie jestem pewien co do tego, ustawiłem sprawdzanie od 1, bo myślę, że gdzieś indziej będzie sprawdzane
            // Czy data jest w ogóle większa od 0?
            if (diffDays > 1) {
                return equipment.getFirstDayCost() + equipment.getNextDaysCost() * (diffDays - 1);
            } else {
                return equipment.getFirstDayCost();
            }
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("model.Rent{");
        sb.append("id=").append(id);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", shipped=").append(shipped);
        sb.append(", eqReturned=").append(eqReturned);
        sb.append('}');
        return sb.toString();
    }

    public int getId() {
        return id;
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

    public Equipment getEquipment() {
        return equipment;
    }

    public Client getClient() {
        return client;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }
}
