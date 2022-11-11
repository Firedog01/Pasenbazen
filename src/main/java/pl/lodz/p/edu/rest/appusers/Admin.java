package pl.lodz.p.edu.rest.appusers;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
@DiscriminatorValue("admin")
@PrimaryKeyJoinColumn(name = "login")
public class Admin extends User {
    public Admin(String login) {
        super(login);
    }

    protected Admin() {

    }
}
