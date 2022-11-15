package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.edu.rest.exception.UserException;

@Entity
@DiscriminatorValue("user_admin")
public class UserAdmin extends User {

    @Column(name = "user_admin")
    private boolean userAdmin;
    public UserAdmin() {
        userAdmin = true;
    }

    public UserAdmin(String login) throws UserException {
        super(login);
        this.userAdmin = true;
    }

    public boolean getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(boolean userAdmin) {
        this.userAdmin = userAdmin;
    }
}
