package pl.lodz.p.edu.rest.DTO;

import jakarta.inject.Inject;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pl.lodz.p.edu.rest.model.Address;
import pl.lodz.p.edu.rest.model.idType;

import java.util.UUID;

public class ClientCreationRequest {



    @Id
    private UUID uuid;
    @NotEmpty
    private String clientId;

    @NotEmpty
    private idType idType;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private Address address;

    public ClientCreationRequest(String clientId, pl.lodz.p.edu.rest.model.idType idType, String firstName, String lastName, Address address) {
        this.uuid = UUID.randomUUID();
        this.clientId = clientId;
        this.idType = idType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public pl.lodz.p.edu.rest.model.idType getIdType() {
        return idType;
    }

    public void setIdType(pl.lodz.p.edu.rest.model.idType idType) {
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
}
