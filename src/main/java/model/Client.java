package model;

import exception.ClientException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "client")
@Access(AccessType.FIELD)
public class Client extends AbstractEntity  {

    @Id
    @Column(name = "client_id")
    private String clientId;

    @Id
    @Column(name = "client_id_type")
    private idType idType;

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @JoinColumn(name = "address_id")
    @ManyToOne(fetch =  FetchType.EAGER, cascade = CascadeType.MERGE)
    private Address address;

    @Column(name = "archive")
    private boolean archive;

    public Client(
            String clientId,
            idType idType,
            String firstName,
            String lastName,
            Address address
    ) throws ClientException {
        if (firstName.isEmpty()) {
            throw new ClientException("Imię nie może być puste");
        }
        if (lastName.isEmpty()) {
            throw new ClientException("Nazwisko nie może być puste");
        }
        if (clientId.isEmpty()) {
            throw new ClientException("Identyfikator klienta nie może być pusty");
        }
        if (address == null) {
            throw new ClientException("Adres nie może być pusty");
        }

        this.clientId = clientId;
        this.idType = idType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.archive = false;

    }

    protected Client() {}

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public idType getIdType() {
        return idType;
    }

    public void setIdType(idType idType) {
        this.idType = idType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
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
