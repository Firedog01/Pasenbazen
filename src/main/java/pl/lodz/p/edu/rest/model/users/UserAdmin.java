package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("user_admin")
public class UserAdmin extends User {

    public UserAdmin() {
    }
}
