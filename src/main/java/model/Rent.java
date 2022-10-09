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
    public String getRentInfo() {
        final StringBuilder sb = new StringBuilder("Rent{");
        sb.append("id=").append(id);
        sb.append("Klient=").append(getClient().toString()); //FIXME to string
        sb.append("Sprzęt=").append(getEquipment().toString());
        sb.append("Adres dostawy= ").append(getShippingAddress().getAddressInfo());
        sb.append("Czas wypożyczenia=");
        sb.append("Początek=").append(beginTime);
        sb.append(" do ");
        sb.append("Koniec=").append(endTime);
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

    public void setId(int id) {
        this.id = id;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    public void setEqReturned(boolean eqReturned) {
        this.eqReturned = eqReturned;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
