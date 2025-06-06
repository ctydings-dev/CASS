/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.person;

import CASS.util.DataObjectGenerator;
import java.util.List;

/**
 *
 * @author ctydi
 */
public class CompositeEmployee {

    private EmployeeDTO employee;

    private PersonDTO person;

    private EmployeeTypeDTO type;



    public CompositeEmployee(PersonDTO person, EmployeeDTO emp, EmployeeTypeDTO type) {
        this.person = person;
        this.employee = emp;
        this.type = type;
       // this.roles = DataObjectGenerator.createList();

    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public EmployeeTypeDTO getType() {
        return type;
    }

    /*public List<EmployeeRoleDTO> getRoles() {
        return roles;
    }
*/
}
