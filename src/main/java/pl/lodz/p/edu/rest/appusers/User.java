
package pl.lodz.p.edu.rest.appusers;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pl.lodz.p.edu.rest.model.UniqueId;


@Entity
@Table(name = "user")
//@Table(name = "User", uniqueConstraints = {@UniqueConstraint(columnNames = {"login"})})
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
public abstract class User extends UniqueId {
    private boolean isActive = false;

    @Id
    @Column(name = "login", unique = true)
    @NotNull
    private String login;

    protected User(String login) {
        super();
        this.login = login;
    }

    protected User() {

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("isActive=").append(isActive);
        sb.append(", login='").append(login).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
