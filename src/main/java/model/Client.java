package model;

import exception.ClientException;

import java.util.List;

public class Client {
    private String firstName;
    private String lastName;
    private String ID;

    private idType idType;
    private boolean archive;

    private Address address;


    public Client(String firstName, String lastName, Address address, String ID,
                  model.idType idType) throws ClientException {

        if (firstName.isEmpty()) {
            throw new ClientException("Imię nie może być puste");
        }

        if (lastName.isEmpty()) {
            throw new ClientException("Nazwisko nie może być puste");
        }

        if (ID.isEmpty()) {
            throw new ClientException("ID nie może być puste");
        }

        if (address == null) {
            throw new ClientException("Adres nie może być pusty");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.archive = false;
        this.ID = ID;
        this.idType = idType;
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("model.Client{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", ID='").append(ID).append('\'');
        sb.append(", archive=").append(archive);
        sb.append('}');
        return sb.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getID() {
        return ID;
    }

    public model.idType getIdType() {
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

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setIdType(model.idType idType) {
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
}
