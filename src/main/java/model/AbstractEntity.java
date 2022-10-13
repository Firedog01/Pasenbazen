package model;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Embedded
    @NotNull
    private UniqueId entityId;

    @Version
    @NotEmpty
    private long version;

    protected AbstractEntity() {
    }
}
