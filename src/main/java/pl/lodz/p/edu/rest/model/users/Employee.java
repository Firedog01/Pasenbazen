package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.edu.rest.DTO.EmployeeDTO;
import pl.lodz.p.edu.rest.exception.UserException;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;

@Entity
@DiscriminatorValue("resource_admin")
public class Employee extends User {

    public Employee(String login) throws MalformedUserException {
        super(login);
    }

    public Employee(EmployeeDTO employeeDTO) throws MalformedUserException {
        super(employeeDTO.getLogin());
    }

    public Employee() {

    }
}
