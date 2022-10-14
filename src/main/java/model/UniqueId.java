package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Embeddable
public class UniqueId implements Serializable {

    @NotNull
    @Column(name = "unique_id")
    private UUID uniqueID;

    public UniqueId() {
        this.uniqueID = UUID.randomUUID();
    }

    public String toString() {
        return uniqueID.toString();
    }

    public UUID getUniqueID() {
        return uniqueID;
    }
}
