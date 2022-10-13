package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Embeddable
public class UniqueId implements Serializable {

    @Id
    @NotNull
    @Column(name = "id")
    private UUID uniqueID;

    public UniqueId() {
        this.uniqueID = UUID.randomUUID();
    }

    public String toString() {
        return uniqueID.toString();
    }
}
