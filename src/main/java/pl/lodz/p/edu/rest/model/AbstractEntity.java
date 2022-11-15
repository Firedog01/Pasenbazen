package pl.lodz.p.edu.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@MappedSuperclass
@Embeddable
public abstract class AbstractEntity implements Serializable {

    @NotNull
    @Embedded
    private UniqueId entityId;

    @Version
    @NotNull
    @Column(name = "version")
    private long version;

    protected AbstractEntity() {
        entityId = new UniqueId();
    }

    public UniqueId getEntityId() {
        return entityId;
    }

    public void setEntityId(UniqueId entityId) {
        this.entityId = entityId;
    }
}
