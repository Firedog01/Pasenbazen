package pl.lodz.p.edu.rest.model;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import pl.lodz.p.edu.rest.exception.UserException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

//@Table(name = "Client", uniqueConstraints = {@UniqueConstraint(columnNames = "client_id")}) //TODO example of unique
@Entity
@Table(name = "clients")
@NamedQueries({
        @NamedQuery(name = "Client.getAll", query = "SELECT c FROM Client c"),
        @NamedQuery(name = "Client.getByUuid", query = "SELECT n FROM Client n WHERE n.uuid = :uuid"),
        @NamedQuery(name = "Client.getByIdString", query = "SELECT n FROM Client n WHERE n.clientId = :clientId"),
        @NamedQuery(name = "Client.count", query = "SELECT count(c) FROM Client c"),
})
@Transactional
@Access(AccessType.FIELD)
public class Client extends AbstractEntity  {

    @Id
    @Size(min = 1)
    @Column(name = "client_id", nullable = false)
    private String clientId;

//    @Id
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    @Column(name = "client_id_type")
//    private idType idType;

    @Size(min = 1)
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 1)
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Embedded
    private Address address;

    @Column(name = "archive")
    private boolean archive;

    public Client(
            String clientId,
            String firstName,
            String lastName,
            Address address
    ) throws UserException {
        super();
        if (firstName.isEmpty()) {
            throw new UserException("Imię nie może być puste");
        }
        if (lastName.isEmpty()) {
            throw new UserException("Nazwisko nie może być puste");
        }
        if (clientId.isEmpty()) {
            throw new UserException("Identyfikator klienta nie może być pusty");
        }
        if (address == null) {
            throw new UserException("Adres nie może być pusty");
        }

        this.clientId = clientId;
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

//    public idType getIdType() {
//        return idType;
//    }

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
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", archive=").append(archive);
        sb.append(", uuid=").append(getUuidId());
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
//        return clientId.equals(client.clientId) && idType == client.idType;
        return clientId.equals(client.clientId);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(clientId, idType);
        return Objects.hash(clientId);
    }


}
