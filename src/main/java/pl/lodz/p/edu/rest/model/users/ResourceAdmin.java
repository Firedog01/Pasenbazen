package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.edu.rest.exception.UserException;

@Entity
@DiscriminatorValue("resource_admin")
public class ResourceAdmin extends User {

    @Column(name = "resource_admin")
    private boolean resourceAdmin;

    public ResourceAdmin(String login, boolean resourceAdmin) throws UserException {
        super(login);
        this.resourceAdmin = resourceAdmin;
    }
    public ResourceAdmin() {
        resourceAdmin = true;
    }

    public boolean getResourceAdmin() {
        return resourceAdmin;
    }

    public void setResourceAdmin(boolean resourceAdmin) {
        this.resourceAdmin = resourceAdmin;
    }
}
