package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.edu.rest.exception.UserException;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

    public Admin() {
    }

    public Admin(String login) throws UserException {
        super(login);
    }
}
