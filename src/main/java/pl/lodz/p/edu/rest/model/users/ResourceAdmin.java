package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("resource_admin")
public class ResourceAdmin extends User {

}
