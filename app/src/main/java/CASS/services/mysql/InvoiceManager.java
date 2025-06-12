/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.data.BaseDTO;
import CASS.data.person.AccountDTO;
import CASS.services.ExtendedItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import java.util.Date;

/**
 *
 * @author ctydi
 */
public class InvoiceManager {
    
    ExtendedItemService svc;

    PersonService prsnSvc;

    public InvoiceManager(ExtendedItemService svc, PersonService prsnSvc) {
        this.svc = svc;
        this.prsnSvc = prsnSvc;
    }
    
    
    private PersonService getPersonService(){
        return this.prsnSvc;
    }
    
    
    
    
    private boolean accountValid(Integer accountId) throws ServiceError{
        
        
        AccountDTO toCheck = this.getPersonService().getAccount(new BaseDTO(accountId));
        
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
