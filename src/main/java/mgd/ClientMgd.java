package mgd;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

public class ClientMgd extends AbstractEntityMgd {

    public enum idType {DowodOsobisty, Paszport}

    @BsonCreator
    public ClientMgd(@BsonProperty("_id") UniqueIdMgd entityId,
                     @BsonProperty("client_id") String clientId,
                     @BsonProperty("client_id_type") ClientMgd.idType clientIdType,
                     @BsonProperty("first_name") String firstName,
                     @BsonProperty("last_name") String lastName,
                     @BsonProperty("address") AddressMgd address,
                     @BsonProperty("archive") boolean archive) {
        super(entityId);
        this.clientId = clientId;
        this.idType = clientIdType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.archive = archive;
    }

    @BsonProperty("client_id")
    private String clientId;

    @BsonProperty("client_id_type")
    private idType idType;

    @BsonProperty("first_name")
    private String firstName;

    @BsonProperty("last_name")
    private String lastName;

    @BsonProperty("address")
    private AddressMgd address;

    @BsonProperty("archive")
    private boolean archive;

    @Override
    public void close() throws Exception {

    }


    public String getClientId() {
        return clientId;
    }

    public idType getIdType() {
        return idType;
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

    public AddressMgd getAddress() {
        return address;
    }

    public void setAddress(AddressMgd address) {
        this.address = address;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("model.Client{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", ID='").append(clientId).append('\'');
        sb.append(", archive=").append(archive);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientMgd client = (ClientMgd) o;
        return clientId.equals(client.clientId) && idType == client.idType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, idType);
    }
}
