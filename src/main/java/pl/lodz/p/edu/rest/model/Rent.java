package pl.lodz.p.edu.rest.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.rest.model.DTO.RentDTO;
import pl.lodz.p.edu.rest.model.users.Client;

import java.time.LocalDateTime;


@Access(AccessType.FIELD)
@Entity
@Table(name = "rent")
@Transactional
@Valid
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

    @Column(name = "end_time")
    private LocalDateTime endTime;


    public Rent(LocalDateTime beginTime, LocalDateTime endTime,
                Equipment equipment, Client client) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.equipment = equipment;
        this.client = client;
    }

    protected Rent() {
    }

    public Rent(RentDTO rentDTO, Equipment equipment, Client client) {
        this.beginTime = LocalDateTime.parse(rentDTO.getBeginTime());
        if (rentDTO.getEndTime() != null) {
            this.endTime = LocalDateTime.parse(rentDTO.getEndTime());
        } else {
            this.endTime = null;
        }
        this.equipment = equipment;
        this.client = client;
    }

    public Rent(long id, LocalDateTime beginTime, LocalDateTime endTime,
                Equipment equipment, Client client) {

        this(beginTime, endTime, equipment, client);
        this.id = id;
    }

    public String toString() {
        String sb = "Rent{" + "id=" + id +
                "Klient=" + getClient().toString() +
                "Sprzęt=" + getEquipment().toString() +
                "Czas wypożyczenia=" +
                "Początek=" + beginTime +
                " do " +
                "Koniec=" + endTime +
                '}';
        return sb;
    }


    public void merge(RentDTO rentDTO, Equipment equipment, Client client) {
        this.beginTime = LocalDateTime.parse(rentDTO.getBeginTime());
        if (rentDTO.getEndTime() != null) {
            this.endTime = LocalDateTime.parse(rentDTO.getEndTime());
        } else {
            this.endTime = null;
        }
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
