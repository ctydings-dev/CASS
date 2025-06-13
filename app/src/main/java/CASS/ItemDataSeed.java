/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.item.ItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.EmployeeDTO;
import CASS.manager.InventoryManager;
import CASS.manager.PersonManager;
import CASS.services.ItemService;
import CASS.services.ServiceError;
import CASS.util.DateUtils;

/**
 *
 * @author ctydi
 */
public class ItemDataSeed {
    
    
    private static int ABYSS,ROVER, ABYSS_PRICE,ROVER_PRICE;
    
    
    
    public static void seedItemData(InventoryManager svc) throws ServiceError{
        addItems(svc);
    }
    
    public static void addItems(InventoryManager svc) throws ServiceError{
       
        
     addAbyss(svc);  
     addRover(svc);
      
        
    }
        public static void addAbyss(InventoryManager svc) throws ServiceError{
      
            
          
        int type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.REGULATOR);
                ItemDTO abyss = new ItemDTO(0,"ABYSS","ABYSS-POST-2015",type,PersonDataSeeder.MARES,true,true);
               ABYSS = svc.addItem(abyss);
               
              ABYSS_PRICE = svc.addPrice(ABYSS, 749.99, 300,PersonDataSeeder.KRIS_EMP ).getKey();
               
               
               
               
       type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RECIEVE);
        TransactionDTO trans = new TransactionDTO(ABYSS,PersonDataSeeder.KRIS_EMP,10,type,true);
        svc.addInventoryTransaction(trans);
          type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SALE);
       trans = new TransactionDTO(ABYSS,PersonDataSeeder.KRIS_EMP,1,type,true);
        svc.addInventoryTransaction(trans); 
             type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SALE);
       trans = new TransactionDTO(ABYSS,PersonDataSeeder.KRIS_EMP,1,type,true);
        svc.addInventoryTransaction(trans); 
             type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RETURN);
       trans = new TransactionDTO(ABYSS,PersonDataSeeder.KRIS_EMP,1,type,true);
        svc.addInventoryTransaction(trans); 
        type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RETURN_REMOVED);
       trans = new TransactionDTO(ABYSS,PersonDataSeeder.KRIS_EMP,1,type,true);
        svc.addInventoryTransaction(trans);   
        
        
    }
        
        
          public static void addRover(InventoryManager svc) throws ServiceError{
        
        int type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.REGULATOR);
                ItemDTO rover = new ItemDTO(0,"ROVER","ROVER-2015-2021",type,PersonDataSeeder.MARES,true,true);
       EmployeeDTO kris = new EmployeeDTO(PersonDataSeeder.KRIS_EMP);
        ROVER = svc.addItem(rover);
        TransactionDTO trans;
      svc.addRecieveTransaction(ROVER,PersonDataSeeder.KRIS_EMP, 15);
        
        
       svc.addSaleTransaction(ROVER,PersonDataSeeder.KRIS_EMP,2);
        
        
       svc.addSaleTransaction(ROVER,PersonDataSeeder.KRIS_EMP,1);
        
        
     trans =    svc.addSaleTransaction(ROVER,PersonDataSeeder.KRIS_EMP,8);
        
        svc.reverseTransaction(trans, kris);
        
     svc.addSaleTransaction(ROVER,PersonDataSeeder.KRIS_EMP,1);
   
    svc.addReturnRemovedTransaction(ROVER,PersonDataSeeder.KRIS_EMP,1);
 
        
    }
        
          public static void addAbysPrices(ItemService svc){
              
              double buy = 499.99;
              double sell = 549.99;
              String start = DateUtils.createDate(1, 1, 2020);
              
              
              
              
      //        PriceDTO
              
              
              
          }
        
    
}
