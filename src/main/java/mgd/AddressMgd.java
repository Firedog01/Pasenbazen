package mgd;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class AddressMgd extends AbstractEntityMgd {

    @BsonCreator
    public AddressMgd(@BsonProperty("_id") UniqueIdMgd entityId,
                      @BsonProperty("city") String city,
                      @BsonProperty("street") String street,
                      @BsonProperty("street_nr") String streetNr) {
        super(entityId);
        this.city = city;
        this.street = street;
        this.streetNr = streetNr;
    }

    public AddressMgd(String city, String street, String streetNr) {
        super(new UniqueIdMgd());
        this.city = city;
        this.street = street;
        this.streetNr = streetNr;
    }

    @BsonProperty("city")
    private String city;

    @BsonProperty("street")
    private String street;

    @BsonProperty("street_nr")
    private String streetNr;

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

    @Override
    public void close() throws Exception {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressMgd)) return false;

        AddressMgd that = (AddressMgd) o;

        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        return streetNr != null ? streetNr.equals(that.streetNr) : that.streetNr == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (streetNr != null ? streetNr.hashCode() : 0);
        return result;
    }
}
