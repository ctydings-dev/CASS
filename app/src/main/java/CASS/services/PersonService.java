/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services;

import CASS.search.PersonSearchParameters;
import CASS.search.CompanySearchParameters;
import CASS.search.EmployeeSearchParameters;
import CASS.data.BaseDTO;
import CASS.data.TypeAssignmentDTO;
import CASS.data.TypeDTO;
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
    
    
    public int addCompany(CompanyDTO toAdd)throws ServiceError;
    
    public int addCompanyRep(CompanyDTO comp, PersonDTO person) throws ServiceError;
    
    public List<CompanyDTO> getCompanies() throws ServiceError;
    
    public CompanyDTO getCompany(BaseDTO key) throws ServiceError;
    
   
    public List<CompanyDTO> searchCompanies(CompanySearchParameters params) throws ServiceError;
    
    
    public Integer addRoleForEmployee(EmployeeDTO employee, TypeDTO target) throws ServiceError;
    
    
    public List<TypeAssignmentDTO> getRolesForEmployee(BaseDTO employee) throws ServiceError;
    
    public List<EmployeeDTO> getEmployeesIDsWithAssignment(TypeDTO target) throws ServiceError;
 
    
    public Integer addPersonToCompany(PersonDTO person, CompanyDTO company) throws ServiceError;
    
    public List<PersonDTO> getPeopleForCompany(CompanyDTO company) throws ServiceError;
    
    
}
