package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.edu.rest.model.users.DTO.EmployeeDTO;
import pl.lodz.p.edu.rest.exception.user.MalformedUserException;

@Entity
@DiscriminatorValue("employee")
public class Employee extends User {

    @Column(name = "desk")
    private String desk;

    public Employee() {}

    public Employee(String login, String desk) throws MalformedUserException {
        super(login);
        this.desk = desk;
    }

    public Employee(EmployeeDTO employeeDTO) throws MalformedUserException {
        this.merge(employeeDTO);
    }



    public boolean verify() {
        return super.verify() && !desk.isEmpty();
    }

    public void merge(EmployeeDTO employeeDTO) {
        this.setLogin(employeeDTO.getLogin());
        this.desk = employeeDTO.getDesk();
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

}
