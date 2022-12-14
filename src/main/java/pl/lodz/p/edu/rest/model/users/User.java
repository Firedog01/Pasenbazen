package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import pl.lodz.p.edu.rest.exception.ObjectNotValidException;
import pl.lodz.p.edu.rest.model.AbstractEntity;

@Valid
@Entity
@Table(name = "tuser")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User extends AbstractEntity {
    @Id
    @Column(name = "login")
    @NotEmpty
    private String login;

    @Column(name = "archive")
    private boolean active;

    public User(String login) throws ObjectNotValidException {
        if(login == null || login.isEmpty()) {
            throw new ObjectNotValidException("Client login cannot be null nor empty");
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

    public void merge(User user) {
        this.login = user.login;
        this.active = user.active;
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", active=" + active +
                ", abstractEntity=" + super.toString() +
                '}';
    }
}
