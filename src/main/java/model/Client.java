package model;

import exception.ClientException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "CLIENT")
@Access(AccessType.FIELD)
public class Client extends AbstractEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_client;

    private String pesel;  //TODO

    @NotEmpty
    @Column(name = "FIRSTNAME")
    private String firstName;

    @NotEmpty
    @Column(name = "LASTNAME")
    private String lastName;

    @NotEmpty
    @Column(name = "IDTYPE")
    private idType idType;
    @Column(name = "ARCHIVE")
    private boolean archive;

    @NotEmpty
    @Column(name = "ADDRESS")
    @ManyToOne()  //TODO
    private Address address;

    @OneToMany //TODO Fetch type LAZY?? Limiting amount of rents??
    private List<Rent> currentRents;


    public Client(String firstName, String lastName, String pesel,
                  idType idType, Address address) throws ClientException {

        if (firstName.isEmpty()) {
            throw new ClientException("Imię nie może być puste");
        }

        if (lastName.isEmpty()) {
            throw new ClientException("Nazwisko nie może być puste");
        }

        if (pesel.isEmpty()) {
            throw new ClientException("ID nie może być puste");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.archive = false;
        this.pesel = pesel;
        this.idType = idType;

        if (address == null) {
            throw new ClientException("Adres nie może być pusty");
        }
    }

    public Client() {

    }

    @Embeddable
    @Access(AccessType.FIELD)
    public enum idType {
        DowodOsobisty, Passport
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public idType getIdType() {
        return idType;
    }

    public boolean isArchive() {
        return archive;
    }

    public Address getAddress() {
        return address;
    }

    public Long getId_client() {
        return id_client;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setPesel(String ID) {
        this.pesel = ID;
    }

    public void setIdType(idType idType) {
        this.idType = idType;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setId_client(Long id_client) {
        this.id_client = id_client;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("model.Client{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", ID='").append(pesel).append('\'');
        sb.append(", archive=").append(archive);
        sb.append('}');
        return sb.toString();
    }
}
