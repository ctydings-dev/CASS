/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.manager;

import CASS.data.BaseDTO;
import CASS.data.person.AccountDTO;
import CASS.data.person.EmployeeDTO;
import CASS.data.person.PersonDTO;
import CASS.services.ExtendedItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import java.util.Date;

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
    
    
    
    
    
    
    
    private boolean personValid(PersonDTO toCheck){
        if(toCheck.isIs_active() == false || toCheck.isIs_current() == false){
            return false;
        }
        return true;
    }
    
    public boolean personValid(Integer toCheck) throws ServiceError{
        return this.personValid(this.getPerson(toCheck));
    }
    
    public boolean employeeValid(Integer toCheck) throws ServiceError{
        
        return this.employeeValid(this.getEmployee(toCheck));
        
    }
    
    
    public boolean employeeValid(EmployeeDTO toCheck) throws ServiceError{
        
        if(this.personValid(toCheck.getPersonID()) == false){
            return false;
        }
        
        return toCheck.isIsActive();
        
        
    }
    
    
    
    public boolean accountValid(Integer accountId) throws ServiceError{
        
        
        AccountDTO toCheck = this.getAccount(accountId);
        
        if(this.personValid(toCheck.getPersonId()) == false){
            return false;
        }
        
        
        
        
   return this.accountValid(toCheck);
       
        
        
    }
    
    
    private boolean accountValid(AccountDTO toCheck){
          
        if(toCheck.getClosedDate() == null){
            return true;
        }
        
                Date current= new Date(System.currentTimeMillis());
        
        Date check  = new Date(toCheck.getClosedDate());
        
        return check.after(current);
        
    }
    
    
}
