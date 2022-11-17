package pl.lodz.p.edu.rest.DTO;

import pl.lodz.p.edu.rest.model.users.Admin;

public class AdminDTO extends UserDTO {
    public AdminDTO() {}

    public AdminDTO(Admin a) {
        super(a);
    }
}
