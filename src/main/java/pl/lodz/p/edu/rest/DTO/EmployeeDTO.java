package pl.lodz.p.edu.rest.DTO;

import pl.lodz.p.edu.rest.model.users.Employee;

public class EmployeeDTO extends UserDTO {
    public EmployeeDTO() {}

    public EmployeeDTO(Employee e) {
        super(e);
    }
}
