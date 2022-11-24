package pl.lodz.p.edu.rest.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Embeddable
@Access(AccessType.FIELD)
@Valid
public class Address {

    @NotNull
    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @NotNull
    @Column(name = "streetNr")
    private String streetNr;

    public Address(String city, String street, String streetNr) {
        this.city = city;
        this.street = street;
        this.streetNr = streetNr;
    }

    protected Address() {
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

    String getAddressInfo() {
        String sb = "Rent{" + "Miasto=" + getCity() +
                "Ulica=" + getStreet() +
                "Numer mieszkania=" + getStreetNr();

        return sb;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNr='" + streetNr + '\'' +
                '}';
    }
}