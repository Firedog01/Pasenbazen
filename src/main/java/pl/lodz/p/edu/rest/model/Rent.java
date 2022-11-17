package pl.lodz.p.edu.rest.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import pl.lodz.p.edu.rest.model.DTO.RentDTO;
import pl.lodz.p.edu.rest.model.users.Client;

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
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Client client;

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
                Equipment equipment, Client client) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.shipped = false;
        this.eqReturned = false;
        this.equipment = equipment;
        this.client = client;
    }

    protected Rent() {}

    public Rent(RentDTO rentDTO, Equipment equipment, Client client) {
        this.beginTime = LocalDateTime.parse(rentDTO.getBeginTime());
        this.endTime = LocalDateTime.parse(rentDTO.getEndTime());
        this.equipment = equipment;
        this.client = client;
    }

    public Rent(long id, LocalDateTime beginTime, LocalDateTime endTime,
                Equipment equipment, Client client) {

        this(beginTime, endTime, equipment, client);
        this.id = id;
    }

    public boolean verify() {
        return beginTime.isBefore(endTime)
                && client.verify() && equipment.verify()
                && !equipment.isArchive() && !equipment.isMissing();
    }


    public double getRentCost() {
        if (!eqReturned) {
            return 0.0;
        } else if (equipment.isMissing()) {
            return equipment.getBail();
        } else {
            long diffDays = Math.abs( ChronoUnit.DAYS.between(beginTime, endTime));
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
        sb.append("Czas wypożyczenia=");
        sb.append("Początek=").append(beginTime);
        sb.append(" do ");
        sb.append("Koniec=").append(endTime);
        sb.append('}');
        return sb.toString();
    }


    public void merge(RentDTO rentDTO, Equipment equipment, Client client) {
        this.beginTime = LocalDateTime.parse(rentDTO.getBeginTime());
        this.endTime = LocalDateTime.parse(rentDTO.getEndTime());
        this.equipment = equipment;
        this.client = client;
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
}
