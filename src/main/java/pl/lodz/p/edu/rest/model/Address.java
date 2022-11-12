package pl.lodz.p.edu.rest.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

//@Entity
//@Table(name = "address")
@Access(AccessType.FIELD)
@Embeddable
@Transactional
public class Address {

//    @Id
//    @Column(name = "address_id")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(initialValue = 0, name = "address_sequence_generator")
//    private long id;

    @NotNull
    private String city;

    private String street;

    @NotNull
    private String streetNr;

    public Address(String city, String street, String streetNr) {
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