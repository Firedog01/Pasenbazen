package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.edu.rest.DTO.AdminDTO;
import pl.lodz.p.edu.rest.exception.UserException;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

    public Admin() {
    }

    public Admin(AdminDTO adminDTO) throws MalformedUserException {
        super(adminDTO.getLogin());
    }

    public Admin(String login) throws MalformedUserException {
        super(login);
    }
}
