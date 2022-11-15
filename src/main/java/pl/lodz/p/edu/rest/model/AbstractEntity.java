package pl.lodz.p.edu.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Embeddable
public abstract class AbstractEntity implements Serializable {

    @NotNull
    @Embedded
    @Column(name = "entity_id")
    private UUID entityId;

    @Version
    @NotNull
    @Column(name = "version")
    private long version;

    public AbstractEntity() {
        entityId = UUID.randomUUID();
    }

    public UUID getEntityId() {
        return entityId;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "entityId=" + entityId +
                ", version=" + version +
                '}';
    }
}
