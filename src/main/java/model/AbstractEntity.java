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

    public AbstractEntity() {
        entityId = new UniqueId();
        version = 0;
    }

    public AbstractEntity(UniqueId entityId, long version) {
        this.entityId = entityId;
        this.version = version;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
