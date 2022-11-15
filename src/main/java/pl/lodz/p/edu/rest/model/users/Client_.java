package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.Id;
import pl.lodz.p.edu.rest.exception.UserException;
import pl.lodz.p.edu.rest.model.AbstractEntity;
import pl.lodz.p.edu.rest.model.Address;

public class Client_ extends AbstractUser {


    private String firstName;

    private String lastName;

    private Address address;

    public Client_(String login, String firstName, String lastName, Address address) throws UserException {
        super(login);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
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
