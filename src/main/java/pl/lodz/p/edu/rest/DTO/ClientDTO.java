package pl.lodz.p.edu.rest.DTO;

import pl.lodz.p.edu.rest.model.Address;

public class ClientDTO {

    private String clientId;
    private String firstName;
    private String lastName;
    private Address address;

    public ClientDTO() {
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
}
