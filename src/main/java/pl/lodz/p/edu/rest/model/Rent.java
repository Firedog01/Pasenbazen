package pl.lodz.p.edu.rest.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.rest.model.EQ.Equipment;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "rent")
@Transactional
@Access(AccessType.FIELD)
public class Rent extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(initialValue = 0, name = "rent_sequence_generator")
    @Column(name = "rent_id")
    private long id;

    @NotNull
    @JoinColumn(name = "equipment_id")
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Equipment equipment;

    @NotNull
    @JoinColumn(name = "client_id")
    @JoinColumn(name = "client_id_type")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Client client;

//    @JoinColumn(name = "address_id")
//    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotNull
    @Embedded
    private Address shippingAddress;

    @NotNull
    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    @NotNull
    @Column(name = "end_time")
    private LocalDateTime endTime;


    @Column(name = "shipped")
    private boolean shipped;


    @Column(name = "eq_returned")
    private boolean eqReturned;


    public Rent(LocalDateTime beginTime, LocalDateTime endTime,
                Equipment equipment, Client client, Address shippingAddress) {
        super();

        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shipped = false;
        this.eqReturned = false;
        this.equipment = equipment;
        this.client = client;
        this.shippingAddress = shippingAddress;
    }

    protected Rent() {}

    public Rent(long id, LocalDateTime beginTime, LocalDateTime endTime,
                Equipment equipment, Client client, Address shippingAddress) {

        this(beginTime, endTime, equipment, client, shippingAddress);
        this.id = id;
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

    public String toString() {
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
