package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.Id;
import pl.lodz.p.edu.rest.exception.UserException;
import pl.lodz.p.edu.rest.model.AbstractEntity;

public abstract class AbstractUser extends AbstractEntity {
    @Id
    private String login;

    private boolean active;

    public AbstractUser(String login) throws UserException {
        if(login == null || login.isEmpty()) {
            throw new UserException("Client login cannot be null nor empty");
        }
        this.login = login;
        this.active = true;
    }

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
