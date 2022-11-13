package pl.lodz.p.edu.rest.model;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

//    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    @Column(name = "uuid")
//    private UUID uuid;
    private UUID uuid = UUID.randomUUID();

    @Version
    @NotNull
    private long version;

    protected AbstractEntity() {
    }

    public UUID getUuidId() {
        return uuid;
    }

    public void setEntityId(UUID uuid) {
        this.uuid = uuid;
    }
}
