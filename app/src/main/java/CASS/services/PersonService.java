/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services;

import CASS.data.BaseDTO;
import CASS.data.person.EmployeeDTO;
import CASS.data.person.PersonDTO;
import CASS.data.person.*;
import java.util.List;

/**
 *
 * @author ctydi
 */
public interface PersonService {

    public PersonDTO getPerson(BaseDTO key) throws ServiceError;

    public List<PersonDTO> getPersons() throws ServiceError;

    public int addPerson(PersonDTO toAdd) throws ServiceError;
    
    public List<PersonDTO> searchPersons(PersonSearchParameters params) throws ServiceError;
  
    
    public EmployeeDTO getEmployee(BaseDTO key)throws ServiceError;
    
    
    public List<EmployeeDTO> getEmployees() throws ServiceError;
    
    public int addEmployee(EmployeeDTO toAdd) throws ServiceError;
    
    public List<EmployeeDTO> searchEmployees(EmployeeSearchParameters params) throws ServiceError;
    
    

    
    
}
