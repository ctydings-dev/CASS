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

    private Integer personID;

    private String hireDate;

    private Integer employee_type_id;

    private boolean isActive;

    private String employeeCode;

    
      public EmployeeDTO(int ID){
          super(ID);
      
    }
    
    
    
    
    public EmployeeDTO(Integer personID, String hireDate, Integer employee_type_id, boolean isActive, String employeeCode, Integer key, String createdDate) {
        super(key, createdDate);
        this.personID = personID;
        this.hireDate = hireDate;
        this.employee_type_id = employee_type_id;
        this.isActive = isActive;
        this.employeeCode = employeeCode;
    }
    
        public EmployeeDTO(Integer personID, String hireDate, Integer employee_type_id, boolean isActive, String employeeCode) {
        super();
        this.personID = personID;
        this.hireDate = hireDate;
        this.employee_type_id = employee_type_id;
        this.isActive = isActive;
        this.employeeCode = employeeCode;
    }
    
    
      public EmployeeDTO(Integer personID, String hireDate, TypeDTO employeeType, boolean isActive, String employeeCode, Integer key, String createdDate) {
        super(key, createdDate);
        this.personID = personID;
        this.hireDate = hireDate;
        this.employee_type_id = employeeType.getTypeID();
        this.isActive = isActive;
        this.employeeCode = employeeCode;
    }
        public EmployeeDTO(Integer personID, String hireDate, TypeDTO employeeType, boolean isActive, String employeeCode) {
        super();
        this.personID = personID;
        this.hireDate = hireDate;
        this.employee_type_id = employeeType.getTypeID();
        this.isActive = isActive;
        this.employeeCode = employeeCode;
    }
      
      

    public Integer getPersonID() {
        return personID;
    }

    public String getHireDate() {
        return hireDate;
    }

    public Integer getEmployeeTypeId() {
        return employee_type_id;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }
    
    
    

}
