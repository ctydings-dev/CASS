/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import CASS.data.TypeRepository;
import CASS.data.item.ItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.services.ItemService;
import CASS.services.ServiceError;

/**
 *
 * @author ctydi
 */
public class ItemDataSeed {
    
    
    private static int ABYSS,ROVER;
    
    
    
    public static void seedItemData(ItemService svc) throws ServiceError{
        addItems(svc);
    }
    
    public static void addItems(ItemService svc) throws ServiceError{
     addAbyss(svc);  
     addRover(svc);
      
        
    }
        public static void addAbyss(ItemService svc) throws ServiceError{
        
        int type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.REGULATOR);
                ItemDTO abyss = new ItemDTO(0,"ABYSS REGULATOR","ABYSS-POST-2015",type,PersonDataSeeder.MARES,true,true);
       
        ABYSS = svc.addItem(abyss);
       type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SHIPMENT);
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
        
        
          public static void addRover(ItemService svc) throws ServiceError{
        
        int type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.REGULATOR);
                ItemDTO abyss = new ItemDTO(0,"ROVER","ROVER-2015-2021",type,PersonDataSeeder.MARES,true,true);
       
        ROVER = svc.addItem(abyss);
       type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SHIPMENT);
        TransactionDTO trans = new TransactionDTO( ROVER,PersonDataSeeder.KRIS_EMP,15,type,true);
        svc.addInventoryTransaction(trans);
          type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SALE);
       trans = new TransactionDTO( ROVER,PersonDataSeeder.KRIS_EMP,2,type,true);
        svc.addInventoryTransaction(trans); 
             type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SALE);
       trans = new TransactionDTO( ROVER,PersonDataSeeder.KRIS_EMP,1,type,true);
        svc.addInventoryTransaction(trans); 
          type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SALE);
       trans = new TransactionDTO( ROVER,PersonDataSeeder.KRIS_EMP,1,type,false);
        svc.addInventoryTransaction(trans); 
             type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RETURN_REMOVED);
       trans = new TransactionDTO( ROVER,PersonDataSeeder.KRIS_EMP,1,type,true);
        svc.addInventoryTransaction(trans); 
 
        
    }
        
        
        
        
    
}
