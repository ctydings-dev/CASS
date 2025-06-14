/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import static CASS.AddressDataSeeder.ANC;
import static CASS.PersonDataSeeder.ME;
import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.address.AddressDTO;
import CASS.data.item.SerializedItemDTO;
import CASS.data.person.AccountDTO;
import CASS.data.person.PersonDTO;
import CASS.manager.InventoryManager;
import CASS.manager.InvoiceManager;
import CASS.services.AddressService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import java.util.List;

/**
 *
 * @author ctydi
 */
public class InvoiceDataSeeder {
    
    
    
    public static void seedInvoiceData(InvoiceManager mngr, InventoryManager inven,AddressService addrSvc, PersonService svc) throws ServiceError{
        
        seedAbyssSalesData(mngr,inven, addrSvc, svc);
        
    }
    
    private static Integer createTempAccount(AddressService addrSvc, PersonService svc) throws ServiceError{
        
        String street = "STREET " + Math.random();
        
        String fName = "f" + Math.random();
        String mName = "m" + Math.random();
        String lName = "l" + Math.random();
         String alias = "a" + Math.random();
        fName = fName.substring(0,10);
        mName = mName.substring(0,10);
        lName = lName.substring(0,10);
        alias = alias.substring(0,10);
        
        street = street.substring(0,10);
        AddressDTO toAdd = new AddressDTO(street, "","99515",ANC);
      int addr=   addrSvc.addAddress(toAdd);
        
        
     PersonDTO perDTO =   new PersonDTO(fName,mName,lName,alias,alias,'M',true,true,addr,"","","2001-05-15",0);
       
     Integer person = svc.addPerson(perDTO);
             TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.ACCOUNT_TYPE.TEST);
         AccountDTO acc = new AccountDTO(alias,"GEN-"+ person,person,type.getKey());
        
        return svc.addAccount(acc).getKey();
 
    }
    
    
    
    private static void seedAbyssSalesData(InvoiceManager mngr, InventoryManager inv,AddressService addrSvc, PersonService svc) throws ServiceError{
         Integer invoice = createInvoice(mngr, PersonDataSeeder.ME_ACC, PersonDataSeeder.JD_EMP);   
         sellAbyss(mngr,inv,invoice);
         
         int total = 5;
         
         for(int index = 0; index < total; index++){
          Integer acc = createTempAccount(addrSvc,svc);
          invoice = createInvoice(mngr, acc, PersonDataSeeder.JD_EMP);
           sellAbyss(mngr,inv,invoice);
         }
         
       
}

public static void sellAbyss(InvoiceManager mngr, InventoryManager inv, Integer invoice) throws ServiceError{
    List<SerializedItemDTO> items =    inv.getSerializedItemsForSale(ItemDataSeed.ABYSS);
    
    SerializedItemDTO toSell = items.get(0);

    mngr.sellSerializedItem(toSell.getKey(),ItemDataSeed.ABYSS_PRICE, invoice);
    
    
        
        
    }
    
    
    private static Integer createInvoice(InvoiceManager mngr, int account, int emp) throws ServiceError{
        
        
       return mngr.createSalesInvoice(account, emp).getKey();
        
        
    }
    
    
}
