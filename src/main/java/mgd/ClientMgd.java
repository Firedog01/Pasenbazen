package mgd;

import exception.ClientException;
import model.Address;
import model.idType;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Objects;

public class ClientMgd extends AbstractEntityMgd {

    @BsonCreator
    public ClientMgd(@BsonProperty("_id") UniqueIdMgd entityId,
                     @BsonProperty("client_id") String clientId,
                     @BsonProperty("first_name") String firstName,
                     @BsonProperty("last_name") String lastName,
                     @BsonProperty("address") AddressMgd address,
                     @BsonProperty("archive") boolean archive) {
        super(entityId);
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.archive = archive;
    }

    public ClientMgd(
            String clientId,
            String firstName,
            String lastName,
            AddressMgd address
    ) {
        super(new UniqueIdMgd());
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.archive = false;
    }

    @BsonProperty("client_id")
    private String clientId;

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
        if (!(o instanceof ClientMgd)) return false;

        ClientMgd clientMgd = (ClientMgd) o;

        if (archive != clientMgd.archive) return false;
        if (clientId != null ? !clientId.equals(clientMgd.clientId) : clientMgd.clientId != null) return false;
        if (firstName != null ? !firstName.equals(clientMgd.firstName) : clientMgd.firstName != null) return false;
        if (lastName != null ? !lastName.equals(clientMgd.lastName) : clientMgd.lastName != null) return false;
        return address != null ? address.equals(clientMgd.address) : clientMgd.address == null;
    }

    @Override
    public int hashCode() {
        int result = clientId != null ? clientId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (archive ? 1 : 0);
        return result;
    }
}
