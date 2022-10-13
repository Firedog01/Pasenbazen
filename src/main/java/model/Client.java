package model;

import exception.ClientException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "client")
@Access(AccessType.FIELD)
public class Client extends AbstractEntity  {

//    @Embeddable
//    @Access(AccessType.FIELD)
//    public enum idType {
//        DowodOsobisty, Passport
//    }
    @Id
    private long id;

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "client_id")
    private String clientId; // pesel or passport

    @NotEmpty
    @Column(name = "id_type")
    private idType idType;

    @NotEmpty
    @Column(name = "archive")
    private boolean archive;

    @NotNull
//    @Column(name = "address") //FIXME?
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Address address;

    public Client(String firstName,
                  String lastName,
                  String clientId,
                  idType idType,
                  Address address
    ) throws ClientException {

        if (firstName.isEmpty()) {
            throw new ClientException("Imię nie może być puste");
        }

        if (lastName.isEmpty()) {
            throw new ClientException("Nazwisko nie może być puste");
        }

        if (clientId.isEmpty()) {
            throw new ClientException("ID nie może być puste");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.archive = false;
        this.clientId = clientId;
        this.idType = idType;

        if (address == null) {
            throw new ClientException("Adres nie może być pusty");
        }
    }

    public Client() {}


//    public void setId_client(Long id_client) {
//        this.clientId = id_client;
//    }
//
//    public Long getId_client() {
//        return id_client;
//    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getClientId() {
        return clientId;
    }

    public idType getIdType() {
        return idType;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setIdType(idType idType) {
        this.idType = idType;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("model.Client{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", archive=").append(archive);
        sb.append('}');
        return sb.toString();
    }
}
