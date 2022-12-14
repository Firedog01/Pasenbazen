package pl.lodz.p.edu.cassandra.model;


import pl.lodz.p.edu.cassandra.exception.ClientException;

import java.util.Objects;
import java.util.UUID;


public class Client extends AbstractEntity {


    private String clientId;


    private IdType idType;


    private String firstName;


    private String lastName;


    private Address address;

    private boolean archive;

    public Client(
            String clientId,
            IdType idType,
            String firstName,
            String lastName,
            Address address

    ) throws ClientException {
        super();
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
    public Client(UUID uuid,
                  String clientId,
                  IdType idType,
                  String firstName,
                  String lastName,
                  Address address
    ) throws ClientException {
        this(clientId, idType, firstName, lastName, address);
    }

    protected Client() {
        super();
    }

    public String getClientId() {
        return clientId;
    }

    public IdType getIdType() {
        return idType;
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
        sb.append(", ID='").append(clientId).append('\'');
        sb.append(", archive=").append(archive);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId.equals(client.clientId) && idType == client.idType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, idType);
    }


}
