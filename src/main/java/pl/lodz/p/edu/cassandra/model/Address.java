package pl.lodz.p.edu.cassandra.model;

import java.util.UUID;


public class Address extends AbstractEntity {

    private long id;

    private String city;

    private String street;

    private String streetNr;

    public Address(String city, String street, String streetNr) {
        this.city = city;
        this.street = street;
        this.streetNr = streetNr;
    }

    public Address(UUID uuid, String city, String street, String streetNr) {
        super();
        this.city = city;
        this.street = street;
        this.streetNr = streetNr;
    }

    protected Address() {}

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