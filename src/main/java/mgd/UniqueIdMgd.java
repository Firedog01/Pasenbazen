package mgd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UniqueIdMgd {
    private UUID uuid;

    public UniqueIdMgd() {
        this.uuid = UUID.randomUUID();
    }

    @JsonCreator
    public UniqueIdMgd(@JsonProperty("uuid") UUID uuid) {
        this.uuid = uuid;
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUniqueID(UUID uuid) {
        this.uuid = uuid;
    }

    public String toString() {
        return uuid.toString();
    }
}
