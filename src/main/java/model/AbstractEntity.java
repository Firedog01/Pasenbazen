package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@MappedSuperclass
@Embeddable
public abstract class AbstractEntity implements Serializable {

    @Embedded
    @NotNull
    private UniqueId entityId;

    @Version
    @NotEmpty
    private long version;

    public AbstractEntity() {}

    public AbstractEntity(UniqueId entityId, long version) {
        this.entityId = entityId;
        this.version = version;
    }

    public UniqueId getEntityId() {
        return entityId;
    }

    public void setEntityId(UniqueId entityId) {
        this.entityId = entityId;
    }
}
