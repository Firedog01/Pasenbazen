package mgd;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class ClientAddressMgd extends AbstractEntityMgd {

    @BsonProperty
    private String clientId;
    @BsonProperty
    private ClientMgd.idType clientIdType;
    @BsonProperty
    private String firstName;
    @BsonProperty
    private String lastName;
    @BsonProperty
    private AddressMgd address;
    @BsonProperty
    private boolean archive;
    @BsonProperty
    private String city;
    @BsonProperty
    private String street;
    @BsonProperty
    private String streetNr;

    @BsonCreator
    public ClientAddressMgd(
            @BsonProperty("_id") UniqueIdMgd entityId,
            @BsonProperty("client_id") String clientId,
            @BsonProperty("client_id_type") ClientMgd.idType clientIdType,
            @BsonProperty("first_name") String firstName,
            @BsonProperty("last_name") String lastName,
            @BsonProperty("address") AddressMgd address,
            @BsonProperty("archive") boolean archive,
            @BsonProperty("city") String city,
            @BsonProperty("street") String street,
            @BsonProperty("street_nr") String streetNr) {
        super(entityId);
        this.clientId = clientId;
        this.clientIdType = clientIdType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.archive = archive;
        this.city = city;
        this.street = street;
        this.streetNr = streetNr;
    }

    public String getClientId() {
        return clientId;
    }

    public ClientMgd.idType getClientIdType() {
        return clientIdType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public AddressMgd getAddress() {
        return address;
    }

    public boolean isArchive() {
        return archive;
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

    @Override
    public void close() throws Exception {

    }
}
