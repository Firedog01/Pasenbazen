package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.edu.rest.model.users.DTO.AdminDTO;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    @Column(name = "favourite_ice_cream")
    private String favouriteIceCream;

    public Admin() {
    }

    public boolean verify() {
        return super.verify() && !favouriteIceCream.isEmpty();
    }

    public void merge(AdminDTO adminDTO) {
        this.setLogin(adminDTO.getLogin());
        this.favouriteIceCream = adminDTO.getFavouriteIceCream();
    }

    public Admin(AdminDTO adminDTO) throws MalformedUserException {
        this.merge(adminDTO);
    }

    public Admin(String login, String favouriteIceCream) throws MalformedUserException {
        super(login);
        this.favouriteIceCream = favouriteIceCream;
    }

    public String getFavouriteIceCream() {
        return favouriteIceCream;
    }

    public void setFavouriteIceCream(String favouriteIceCream) {
        this.favouriteIceCream = favouriteIceCream;
    }
}
