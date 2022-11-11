package pl.lodz.p.edu.rest.appusers;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "resource_manager")
@DiscriminatorValue("resource_manager")
@PrimaryKeyJoinColumn(name = "login")
public class ResourceManager extends User {
    public ResourceManager(String login) {
        super(login);
    }

    protected ResourceManager() {

    }
}
