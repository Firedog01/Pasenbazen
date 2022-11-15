package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.*;
import pl.lodz.p.edu.rest.exception.UserException;
import pl.lodz.p.edu.rest.model.AbstractEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "user")
@DiscriminatorColumn(name = "user_type")
public abstract class User extends AbstractEntity {
    @Id
    private String login;

    private boolean active;

    public User(String login) throws UserException {
        if(login == null || login.isEmpty()) {
            throw new UserException("Client login cannot be null nor empty");
        }
        this.login = login;
        this.active = true;
    }

    public User() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
