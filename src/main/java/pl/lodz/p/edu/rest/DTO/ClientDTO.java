package pl.lodz.p.edu.rest.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.rest.model.Address;
import pl.lodz.p.edu.rest.model.users.Client;

public class ClientDTO extends UserDTO {

    @NotNull
    @NotEmpty
    private String clientId;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    private Address address;

    public ClientDTO() {
    }

    public ClientDTO(Client c) {
        super(c);
        this.firstName = c.getFirstName();
        this.lastName = c.getLastName();
        this.address = c.getAddress();
    }

    public String getClientId() {
        return clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
