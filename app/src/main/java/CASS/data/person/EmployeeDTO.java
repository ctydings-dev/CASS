/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.person;

import CASS.data.BaseDTO;
import CASS.data.CreatedDTO;
import CASS.data.TypeDTO;

/**
 *
 * @author ctydi
 */
public class EmployeeDTO extends CreatedDTO {

    private int personID;

    private String hireDate;

    private int employee_type_id;

    private boolean isActive;

    private String employeeCode;

    public EmployeeDTO(int personID, String hireDate, int employee_type_id, boolean isActive, String employeeCode, int key, String createdDate) {
        super(key, createdDate);
        this.personID = personID;
        this.hireDate = hireDate;
        this.employee_type_id = employee_type_id;
        this.isActive = isActive;
        this.employeeCode = employeeCode;
    }
    
      public EmployeeDTO(int personID, String hireDate, TypeDTO employeeType, boolean isActive, String employeeCode, int key, String createdDate) {
        super(key, createdDate);
        this.personID = personID;
        this.hireDate = hireDate;
        this.employee_type_id = employeeType.getTypeID();
        this.isActive = isActive;
        this.employeeCode = employeeCode;
    }

    public int getPersonID() {
        return personID;
    }

    public String getHireDate() {
        return hireDate;
    }

    public int getEmployeeTypeId() {
        return employee_type_id;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }
    
    
    

}
