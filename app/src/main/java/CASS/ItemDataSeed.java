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
     addGeneric(svc);
     addTanks(svc);
     //createShitloads(svc,150);
      
        
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
    
    
    private static void addGenericItem(InventoryManager svc, ItemDTO item, int qty, double price) throws ServiceError{
         svc.addItem(item,PersonDataSeeder.JD_EMP);
           int transType  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RECIEVE);
       
        TransactionDTO trans = new TransactionDTO(item.getKey(),PersonDataSeeder.KRIS_EMP,qty,svc.getFacility(),transType,true);
               
        svc.addInventoryTransaction(trans);
        svc.addPrice(item.getKey(), price, PersonDataSeeder.KRIS_EMP);
        
        
    }
    
    
    
    
    
        private static void createShitloads(InventoryManager svc, int qty) throws ServiceError
        {
         int type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.TEST);
       
        
            for(int index = 0; index < qty; index++){
                if(index % 10 == 0){
                    
                    System.out.println("ON " + index);
                }
       double price = 20.99;
               
                  ItemDTO item = new ItemDTO(0,"TEST"+ index,"TEST:"+index,type,PersonDataSeeder.XSCUBA,true,false);
        
       addGenericItem(svc,item,index+1, price);
        
        
            }
            
            
        }
    
    
    
    public static void addGeneric(InventoryManager svc) throws ServiceError{
        
        int type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.HOSE);
       
        
        
        
          ItemDTO item = new ItemDTO(0,"LP HOSE 25 INCH","LP_HOSE_25_XSCUBA",type,PersonDataSeeder.XSCUBA,true,false);
        
        
       int qty = 10;
       double price = 20.99;
               
       
       addGenericItem(svc,item, qty, price);
        
              type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.HOSE);
         qty = 12;
         price = 19.99;
              
         item = new ItemDTO(0,"LP HOSE 20 INCH","LP_HOSE_20_XSCUBA",type,PersonDataSeeder.XSCUBA,true,false);
          addGenericItem(svc,item, qty, price);
         type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.MASK);
         qty = 5;
         price = 49.99;
              
         item = new ItemDTO(0,"TECHO MASK(S)","TECNO_MARES_S",type,PersonDataSeeder.MARES,true,false);
        addGenericItem(svc,item, qty, price);
                 qty = 6;
    
              
         item = new ItemDTO(0,"TECHO MASK(M)","TECNO_MARES_M",type,PersonDataSeeder.MARES,true,false);
          addGenericItem(svc,item, qty, price);
        
               qty = 2;
    
              
         item = new ItemDTO(0,"TECHO MASK(L)","TECNO_MARES_L",type,PersonDataSeeder.MARES,true,false);
       addGenericItem(svc,item, qty, price);
        
          
          
          
          
          
        
    }
    
    public static void addTanks(InventoryManager svc) throws ServiceError{
        
         int type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.TANK);
                 ItemDTO tank = new ItemDTO(0,"80 CFT Tank Steel Non-Galvanzied","80-CF-TANK-LUX-NON_GAL",type,PersonDataSeeder.LUX,true,true);
               svc.addItem(tank,PersonDataSeeder.JD_EMP);
             
        
        svc.addPrice(tank.getKey(), 249.99, 150,PersonDataSeeder.JD_EMP).getKey();
        
         addSerializedItems(svc,tank,4,PersonDataSeeder.JD_EMP);
               
         
         
         tank = new ItemDTO(0,"100 CFT Tank Steel Non-Galvanzied","100-CF-TANK-LUX-NON_GAL",type,PersonDataSeeder.LUX,true,true);
               svc.addItem(tank,PersonDataSeeder.JD_EMP);
             
        
        svc.addPrice(tank.getKey(), 299.99, 200,PersonDataSeeder.JD_EMP).getKey();
        
         addSerializedItems(svc,tank,3,PersonDataSeeder.JD_EMP);
           
            tank = new ItemDTO(0,"130 CFT Tank Steel Non-Galvanzied","130-CF-TANK-LUX-NON_GAL",type,PersonDataSeeder.LUX,true,true);
               svc.addItem(tank,PersonDataSeeder.JD_EMP);
             
        
        svc.addPrice(tank.getKey(), 349.99, 250,PersonDataSeeder.JD_EMP).getKey();
        
         addSerializedItems(svc,tank,8,PersonDataSeeder.JD_EMP);
           
         
         
         
         
    }
    
    
    
    
    
        public static void addAbyss(InventoryManager svc) throws ServiceError{
      
            
          
        int type = TypeRepository.getKey(TypeRepository.ITEM_TYPE.REGULATOR);
                ItemDTO abyss = new ItemDTO(0,"ABYSS","ABYSS-POST-2015",type,PersonDataSeeder.MARES,true,true);
               ABYSS = svc.addItem(abyss,PersonDataSeeder.KRIS_EMP );
             
               
             ItemDTO abyssOcto = new ItemDTO(0,"ABYSS OCTO","ABYSS-POST-2015-OCTO",type,PersonDataSeeder.MARES,true,true);
             svc.addItem(abyssOcto,PersonDataSeeder.KRIS_EMP );
               
               
               
               
                ItemDTO mr22 = new ItemDTO(0,"MR22","MR22-POST-2015",type,PersonDataSeeder.MARES,true,true);
              svc.addItem(mr22,PersonDataSeeder.KRIS_EMP );
               
               
               
              
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
        ROVER = svc.addItem(rover,PersonDataSeeder.KRIS_EMP );
       
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
