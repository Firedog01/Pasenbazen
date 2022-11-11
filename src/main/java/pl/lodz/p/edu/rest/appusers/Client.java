package pl.lodz.p.edu.rest.appusers;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
@DiscriminatorValue("client")
@PrimaryKeyJoinColumn(name = "login")
public class Client extends User { //fixme not sure about that
    public Client(String login) {
        super(login);
    }

    protected Client() {

    }
}
