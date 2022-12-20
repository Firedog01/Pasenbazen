package pl.lodz.p.edu.cassandra.model;


import com.datastax.oss.driver.api.mapper.annotations.*;
import pl.lodz.p.edu.cassandra.exception.ClientException;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention.UPPER_SNAKE_CASE;

@Entity(defaultKeyspace = "just_rent")
@CqlName("clients")
@NamingStrategy(convention = UPPER_SNAKE_CASE)
public class Client implements Serializable {

    @CqlName("clientUuid")
    private UUID uuid;

    @CqlName("clientId")
    @PartitionKey
    private String clientId;

    @CqlName("idType")
//    @ClusteringColumn
    private String idType;
    //FIXME String or other type of enum mapping?
    // There is possibility to add custom codec to cluster configuration but...
    // We have only session configuration based on lecture document

    @CqlName("firstName")
    private String firstName;

    @CqlName("lastName")
    private String lastName;

    @CqlName("archive")
    private boolean archive;

    @CqlName("city")
    private String city;

    @CqlName("street")
    private String street;

    @CqlName("streetNr")
    private String streetNr;

    public Client(
            String clientId,
            String idType,
            boolean archive,
            String firstName,
            String lastName,
            String city,
            String street,
            String streetNr
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
        if (city.isEmpty()) {
            throw new ClientException("Miasto nie może być pusty");
        }
        if (streetNr.isEmpty()) {
            throw new ClientException("Numer mieszkania nie może być puste");
        }

        this.clientId = clientId;
        this.idType = idType;
        this.archive = archive;
        this.city = city;
        this.uuid = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.streetNr = streetNr;

    }
    public Client(UUID uuid,
                  String clientId,
                  String idType, //Changed from IdType to String
                  boolean archive,
                  String firstName,
                  String lastName,
                  String city,
                  String street,
                  String streetNr
    ) throws ClientException {
        this(clientId, idType, archive, firstName, lastName, city, street, streetNr);
        this.uuid = uuid;
    }

    protected Client() {
    }

    public String getClientId() {
        return clientId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
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

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public UUID getClientUuid() {
        return uuid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNr() {
        return streetNr;
    }

    public void setStreetNr(String streetNr) {
        this.streetNr = streetNr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("model.Client{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", ID='").append(clientId).append('\'');
        sb.append(", archive=").append(archive);
        sb.append('}');
        return sb.toString();
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Client client = (Client) o;
//        return clientId.equals(client.clientId) && idType == client.idType;
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(clientId, idType);
//    }


}
