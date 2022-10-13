package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import model.EQ.Equipment;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "RENT")
@Access(AccessType.FIELD)
public class Rent extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long rent_id;


    private int id;

    @NotEmpty
    @Column(name = "EQ_RENT")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Equipment equipment;
                                                    //FIXME merge albo refresh?
    @NotEmpty
    @Column(name = "CLIENT")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Client client;


    @NotEmpty
    @Column(name = "ADDRESS")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Address shippingAddress;

    @NotEmpty
    @Column(name = "BTIME")
    private LocalDateTime beginTime;

    @NotEmpty
    @Column(name = "eTime")
    private LocalDateTime endTime;


    @Column(name = "shipped")
    private boolean shipped;


    @Column(name = "eqRet")
    private boolean eqReturned;


    public Rent(int id, LocalDateTime beginTime, LocalDateTime endTime,
                Equipment equipment, Client client, Address shippingAddress) {

        this.id = id;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shipped = false;
        this.eqReturned = false;
        this.equipment = equipment;
        this.client = client;
        this.shippingAddress = shippingAddress;
    }

    public Rent() {

    }

    public double getRentCost() {
        if (!eqReturned) {
            return 0.0;
        } else if (equipment.isMissing()) {
            return equipment.getBail();
        } else {
            long diffDays= Math.abs(Days.daysBetween(beginTime, endTime).getDays());
            //FIXME Nie jestem pewien co do tego, ustawiłem sprawdzanie od 1, bo myślę, że gdzieś indziej będzie sprawdzane
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
