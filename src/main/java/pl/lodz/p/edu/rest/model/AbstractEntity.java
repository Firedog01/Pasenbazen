package pl.lodz.p.edu.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Embeddable
public abstract class AbstractEntity implements Serializable {

    @Embedded
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

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
