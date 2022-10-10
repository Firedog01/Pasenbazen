package model;

import exception.ClientException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "Client")
@Access(AccessType.FIELD)
public class Client {

    @NotEmpty
    @Column(name = "firstname")
    private String firstName;

    @NotEmpty
    @Column(name = "lastname")
    private String lastName;

    @Id
    @NotNull
    @Column(name = "id")
    private String ID;

    @Column(name = "idtype")
    private idType idType;
    @Column(name = "archive")
    private boolean archive;

    @Column(name = "address") //FIXME?
    private Address address;

    @OneToMany //TODO Fetch type LAZY?? Limiting amount of rents??
    private List<Rent> currentRents;



    public Client(String firstName, String lastName, String ID,
                  idType idType, String city, String street, String streetNr) throws ClientException {

        if (firstName.isEmpty()) {
            throw new ClientException("Imię nie może być puste");
        }

        if (lastName.isEmpty()) {
            throw new ClientException("Nazwisko nie może być puste");
        }

        if (ID.isEmpty()) {
            throw new ClientException("ID nie może być puste");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.archive = false;
        this.ID = ID;
        this.idType = idType;

        this.address = new Address(city, street, streetNr);

        if (address == null) {
            throw new ClientException("Adres nie może być pusty");
        }
    }

    public Client() {

    }


    @Embeddable
    @Access(AccessType.FIELD)
    public enum idType {
        DowodOsobisty, Passport
    }


    @Embeddable
    @Access(AccessType.FIELD)
    public class Address {

        @Column(name = "city")
        @NotNull
        private String city;
        @Column(name = "street")
        private String street;
        @Column(name = "streetNr")
        @NotNull
        private String streetNr;

        public Address(String city, String street, String streetNr) {
            this.city = city;
            this.street = street;
            this.streetNr = streetNr;

        }
        // FIXME
        //    public Address(String city, String street, String streetNr, int addressId) {
        //        this.city = city;
        //        this.street = street;
        //        this.streetNr = streetNr;
        //        this.addressId = addressId;
        //    }

        public Address() {
        }

        public String getCity() {
            return city;
        }

        public String getStreet() {
            return street;
        }

        public String getStreetNr() {
            return streetNr;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setStreetNr(String streetNr) {
            this.streetNr = streetNr;
        }

        String getAddressInfo() {
            final StringBuilder sb = new StringBuilder("Rent{");
            sb.append("Miasto=").append(getCity());
            sb.append("Ulica=").append(getStreet());
            sb.append("Numer mieszkania=").append(getStreetNr());

            return sb.toString();
        }
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

    public void setID(String ID) {
        this.ID = ID;
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
        sb.append(", ID='").append(ID).append('\'');
        sb.append(", archive=").append(archive);
        sb.append('}');
        return sb.toString();
    }
}
