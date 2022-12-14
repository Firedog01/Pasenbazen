package pl.lodz.p.edu.cassandra.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class AbstractEntity implements Serializable {

    private UUID uuid;

    private final long version = 1L; //idk

    protected AbstractEntity() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

}
