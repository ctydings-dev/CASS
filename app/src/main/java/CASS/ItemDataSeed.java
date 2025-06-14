/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.invoice.InvoiceDTO;
import CASS.data.item.ItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.EmployeeDTO;
import CASS.manager.InventoryManager;
import CASS.manager.InvoiceManager;
import CASS.manager.PersonManager;
import CASS.services.ItemService;
import CASS.services.ServiceError;
import CASS.util.DateUtils;

/**
 *
 * @author ctydi
 */
public class ItemDataSeed {
    
    private static int counter = 0;
    
    public static int ABYSS,ROVER, ABYSS_PRICE,ROVER_PRICE;
    
    
    
    public static void seedItemData(InventoryManager svc) throws ServiceError{
        addItems(svc);
    }
    
    public static void addItems(InventoryManager svc) throws ServiceError{
       
        
     addAbyss(svc);  
     addRover(svc);
      
        
    }
    
    
    
    public static void addSerializedItems(InventoryManager mng, ItemDTO item, int qty, Integer employee) throws ServiceError{
        
        
        for(int index =0; index < qty; index++){
            
            String sn = generateSerialNumber(item);
            
            mng.addSerializedItem(item.getKey(), sn, employee,true,true);
        }
        
        
        
    }
    
    private static String generateSerialNumber(ItemDTO item){
        
        String ret = "SN_"+ item.getItemName()+":"+ counter;
        
        counter++;
        return ret;
    }
    
    
        public static void addAbyss(InventoryManager svc) throws ServiceError{
      
            
          
        int type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.REGULATOR);
                ItemDTO abyss = new ItemDTO(0,"ABYSS","ABYSS-POST-2015",type,PersonDataSeeder.MARES,true,true);
               ABYSS = svc.addItem(abyss);
             
              
              ABYSS_PRICE = svc.addPrice(ABYSS, 749.99, 300,PersonDataSeeder.KRIS_EMP ).getKey();
               
               addSerializedItems(svc,abyss,10,PersonDataSeeder.KRIS_EMP);
               
               
       type  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RECIEVE);
        TransactionDTO trans = new TransactionDTO(ABYSS,PersonDataSeeder.KRIS_EMP,10,1,type,true);
  
 
        
        
    }
        
        
        
        private static void addSale(InvoiceManager svc, Integer item, Integer price,Integer acc, Integer employee, int qty){
         
          // InvoiceDTO invoice =  svc.createSalesInvoice(acc, employee);
            
         //  svc.addInvoiceItem(invoice, toAdd)
            
            
        }
        
        
        
        
          public static void addRover(InventoryManager svc) throws ServiceError{
        
        int type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.REGULATOR);
                ItemDTO rover = new ItemDTO(0,"ROVER","ROVER-2015-2021",type,PersonDataSeeder.MARES,true,true);
       EmployeeDTO kris = new EmployeeDTO(PersonDataSeeder.KRIS_EMP);
        ROVER = svc.addItem(rover);
       
        TransactionDTO trans;
  
         addSerializedItems(svc,rover,15,PersonDataSeeder.KRIS_EMP);
               
        
        
        
   //    
    // svc.addSaleTransaction(ROVER,PersonDataSeeder.KRIS_EMP,2);
        
        
     //  svc.addSaleTransaction(ROVER,PersonDataSeeder.KRIS_EMP,1);
        
        
   // trans =    svc.addSaleTransaction(ROVER,PersonDataSeeder.KRIS_EMP,8);
        
       // svc.reverseTransaction(trans, kris);
        
  //   svc.addSaleTransaction(ROVER,PersonDataSeeder.KRIS_EMP,1);
   
 //   svc.addReturnRemovedTransaction(ROVER,PersonDataSeeder.KRIS_EMP,1);
 
        
    }
        
          public static void addAbysPrices(ItemService svc){
              
              double buy = 499.99;
              double sell = 549.99;
              String start = DateUtils.createDate(1, 1, 2020);
              
              
              
              
      //        PriceDTO
              
              
              
          }
        
    
}
