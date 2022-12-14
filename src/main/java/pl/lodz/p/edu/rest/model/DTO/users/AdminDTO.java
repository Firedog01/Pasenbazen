package pl.lodz.p.edu.rest.model.DTO.users;

import pl.lodz.p.edu.rest.model.users.Admin;

public class AdminDTO extends UserDTO {
    private String favouriteIceCream;

    public AdminDTO() {}

    public AdminDTO(Admin a) {
        super(a);
        this.favouriteIceCream = a.getFavouriteIceCream();
    }

    public String getFavouriteIceCream() {
        return favouriteIceCream;
    }

    public void setFavouriteIceCream(String favouriteIceCream) {
        this.favouriteIceCream = favouriteIceCream;
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "favouriteIceCream='" + favouriteIceCream + '\'' +
                "} " + super.toString();
    }
}
