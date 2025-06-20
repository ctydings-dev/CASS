/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.manager;

import CASS.data.BaseDTO;
import CASS.data.TypeDTO;
import CASS.data.person.AccountDTO;
import CASS.data.person.CompanyDTO;
import CASS.data.person.EmployeeDTO;
import CASS.data.person.PersonDTO;
import CASS.services.ExtendedItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ctydi
 */
public class PersonManager {
    
        

    PersonService prsnSvc;

    public PersonManager(PersonService prsnSvc) {
    
        this.prsnSvc = prsnSvc;
    }
     private PersonService getPersonService(){
        return this.prsnSvc;
    }
    
    
    public PersonDTO getPerson(Integer key) throws ServiceError{
        return this.getPersonService().getPerson(new BaseDTO(key));
    }
    
  
    public AccountDTO getAccount(Integer key) throws ServiceError{
        return this.getPersonService().getAccount(new BaseDTO(key));
    }
    
    
    public EmployeeDTO getEmployee(Integer key) throws ServiceError{
        return this.getPersonService().getEmployee(new BaseDTO(key));
    }
    
    
    
    
    
    
    
    private void personValid(PersonDTO toCheck) throws ServiceError{
        if(toCheck.isIs_active() == false || toCheck.isIs_current() == false){
          throw new ServiceError("PERSON IS NOT ACTIVE/CURRENT!");
        }
 
    }
    
    public void personValid(Integer toCheck) throws ServiceError{
         this.personValid(this.getPerson(toCheck));
    }
    
    public void employeeValid(Integer toCheck) throws ServiceError{
        
        this.employeeValid(this.getEmployee(toCheck));
        
    }
    
    
    public void  employeeValid(EmployeeDTO toCheck) throws ServiceError{
        
       this.personValid(toCheck.getPersonID());
        
        
     if(toCheck.isIsActive() == false){
         throw new ServiceError("EMPLOYEE IS INACTIVE");
     }
        
        
    }
    
    
    
    public void accountValid(Integer accountId) throws ServiceError{
        
        
        AccountDTO toCheck = this.getAccount(accountId);
        
      this.personValid(toCheck.getPersonId());
        
        
        
        
   this.accountValid(toCheck);
       
        
        
    }
    
    public List<AccountDTO> getAccountsByType(Integer type) throws ServiceError{
        AccountDTO [] results = this.getPersonService().getAccountsByType(new TypeDTO(type));
        
     return   Arrays.asList(results);
        
    }
    
    
    public CompanyDTO getCompany(Integer key) throws ServiceError{
     return   this.getPersonService().getCompany(new BaseDTO(key));
        }
    
    
    
    private void accountValid(AccountDTO toCheck) throws ServiceError{
          
        if(toCheck.getClosedDate() == null){
            return;
        }
        
                Date current= new Date(System.currentTimeMillis());
        
        Date check  = new Date(toCheck.getClosedDate());
        
       if(check.after(current) == false){
           throw new ServiceError("ACCOUNT IS CLOSED!");
       }
        
    }
    
    
    public List<PersonDTO> getPeople() throws ServiceError{
       return this.getPersonService().getPersons();
   
    }
    
    public List<EmployeeDTO> getEmployees() throws ServiceError{
  return this.getPersonService().getEmployees();
    }
    
    
    public AccountDTO getAccountByName(String name){
        try{
            return this.getPersonService().getAccountByName(name);
        }
        catch(Throwable e){
            return null;
        }
        
    }
        public AccountDTO getAccountByNumber(String name){
        try{
            return this.getPersonService().getAccountByNumber(name);
        }
        catch(Throwable e){
            return null;
        }
        
    }
    
}
