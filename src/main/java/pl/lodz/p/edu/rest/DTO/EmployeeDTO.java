package pl.lodz.p.edu.rest.DTO;

import pl.lodz.p.edu.rest.model.users.ResourceAdmin;

public class EmployeeDTO extends UserDTO {
    public EmployeeDTO() {}

    public EmployeeDTO(ResourceAdmin e) {
        super(e);
    }
}
