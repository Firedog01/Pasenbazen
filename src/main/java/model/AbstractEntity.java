package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@MappedSuperclass
@Embeddable
public abstract class AbstractEntity implements Serializable {

    @Embedded
    @NotNull
//    @Column(name = "entity_ID")
    private UniqueId entityId;

    @Version
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long version;

//    protected AbstractEntity() {}

    protected AbstractEntity() {
        this.entityId = new UniqueId();
    }

    public UniqueId getEntityId() {
        return entityId;
    }

    public void setEntityId(UniqueId entityId) {
        this.entityId = entityId;
    }
}
