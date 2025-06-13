/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.manager;

import CASS.data.BaseDTO;
import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.item.ItemDTO;
import CASS.data.item.PriceDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.EmployeeDTO;
import CASS.data.person.PersonDTO;
import CASS.services.ExtendedItemService;
import CASS.services.ItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import static java.util.Map.entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctydi
 */
public class InventoryManager {

    ExtendedItemService svc;

    PersonManager prsnSvc;

    public InventoryManager(ExtendedItemService svc, PersonManager prsnSvc) {
        this.svc = svc;
        this.prsnSvc = prsnSvc;
    }

    protected ExtendedItemService getItemService() {
        return this.svc;

    }

    protected PersonManager getPersonService() {
        return this.prsnSvc;
    }




public void reverseTransaction(TransactionDTO toReverse, EmployeeDTO reverser) throws ServiceError{
        try {
            this.addTransactionReversalNote(toReverse, reverser);
            this.getItemService().reverseTransaction(toReverse, reverser);
        } catch (SQLException ex) {
          throw new ServiceError(ex); }
}

    

    private void addTransactionReversalNote(TransactionDTO toReverse, EmployeeDTO reverser) throws ServiceError, SQLException {

     
        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.NOTE_TYPE.INVENTORY);

        reverser = this.getPersonService().getEmployee(reverser.getKey());
        PersonDTO person = this.getPersonService().getPerson(reverser.getPersonID());

        String note = "TRANSACTION REVERSED BY " + person.getFirstName() + " " + person.getLastName();

        note = note + " (" + reverser.getEmployeeCode() + ")";
        note = note + " AT " + new Date(System.currentTimeMillis()) + ".";

        this.addInventoryTransactionNote(toReverse, type, note);
        
    }
    





    private TransactionDTO updateInventoroy(TransactionDTO entry, boolean reverse) throws SQLException, ServiceError {

        if (this.getItemService().inventoryContains(entry.getItem(), entry.getFacility()) == false) {
            this.getItemService().addInventory(entry);
        }

        int multi = this.getItemService().getMultiplyer(entry.getType());
        int qty = entry.getQuantity() * multi;
        if (reverse) {
            qty = qty * -1;
        }

        this.getItemService().updateInventoryItem(entry, qty);
           return this.getItemService().getLatestTransaction();

    }
    
    
    public Integer getQty(Integer itemKey, Integer facility) throws ServiceError{
    return    this.getItemService().getItemQty(new ItemDTO(itemKey),new BaseDTO(facility));
    }
    
    public TransactionDTO addTransaction(int item,int employee, int qty, int type  ) throws ServiceError{
        
        TransactionDTO trans =  new TransactionDTO( item,employee,qty,type,true);
        
        return this.addInventoryTransaction(trans);
        
    }
    
    
       public TransactionDTO addSaleTransaction(int item,int employee, int qty  ) throws ServiceError{
        
        TransactionDTO trans =  new TransactionDTO( item,employee,qty,TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SALE),true);
        
        return this.addInventoryTransaction(trans);
        
    }
       
            public TransactionDTO addShipmentTransaction(int item,int employee, int qty  ) throws ServiceError{
        
        TransactionDTO trans =  new TransactionDTO( item,employee,qty,TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SHIPMENT),true);
        
        return this.addInventoryTransaction(trans);
        
    }
            
                  public TransactionDTO addRecieveTransaction(int item,int employee, int qty  ) throws ServiceError{
        
        TransactionDTO trans =  new TransactionDTO( item,employee,qty,TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RECIEVE),true);
        
        return this.addInventoryTransaction(trans);
        
    } 
            
               public TransactionDTO addReleaseTransaction(int item,int employee, int qty  ) throws ServiceError{
        
        TransactionDTO trans =  new TransactionDTO( item,employee,qty,TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RELEASE),true);
        
        return this.addInventoryTransaction(trans);
        
    } 
            
            public TransactionDTO addReturnTransaction(int item,int employee, int qty  ) throws ServiceError{
        
        TransactionDTO trans =  new TransactionDTO( item,employee,qty,TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RETURN),true);
        
        return this.addInventoryTransaction(trans);
        
    }
       
         public TransactionDTO addServiceTransaction(int item,int employee, int qty  ) throws ServiceError{
        
        TransactionDTO trans =  new TransactionDTO( item,employee,qty,TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SERVICE),true);
        
        return this.addInventoryTransaction(trans);
        
    }
              public TransactionDTO addReturnRemovedTransaction(int item,int employee, int qty  ) throws ServiceError{
        
        TransactionDTO trans =  new TransactionDTO( item,employee,qty,TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RETURN_REMOVED),true);
        
           String note = "ITEM RETURNED BUT NOT SUITABLE FOR RESALE.";
    
        
        
        
        
        
   TypeDTO   type  = TypeRepository.getTypeDTO(TypeRepository.NOTE_TYPE.SALES);
        
       trans =  this.addInventoryTransaction(trans);
        this.addInventoryTransactionNote(trans, type, note);   
       return trans;
        
    }
         
         
              
    
    public Integer addItem(ItemDTO toAdd) throws ServiceError{
        return this.getItemService().addItem(toAdd);
    }
    
    
  
    public boolean checkTransaction(TransactionDTO entry) throws ServiceError{
        
        try {
            
            if(this.getPersonService().employeeValid(entry.getEmployee()) == false){
                return false;
            }
             
            int multi = this.getItemService().getMultiplyer(entry.getType());
            int qty = entry.getQuantity() * multi;
               
                return (qty + this.getQty(entry.getItem(),entry.getFacility())) > 0;
               
        } catch (SQLException ex) {
           throw new ServiceError(ex); }

    }
    

 


 
    public TransactionDTO addInventoryTransaction(TransactionDTO toAdd) throws ServiceError{
        if(this.checkTransaction(toAdd) == false){
            throw new ServiceError("Invalid Transaction!");
        }
      return   this.getItemService().addInventoryTransaction(toAdd);
        
    }
    
    
    
    
    
 
   public static String createAccountNumber(PersonDTO person, TypeDTO type){
           String name = ""+ person.getFirstName().charAt(0);
              name = name + "_" + person.getLastName() + "_" + type.getTypeName();
       name = name + "-"+ person.getKey();
              return name;
             
   }

    public void addInventoryTransactionNote(TransactionDTO trans, TypeDTO toUse, String note) throws ServiceError {
    this.getItemService().addInventoryTransactionNote(trans, toUse, note);
    }

 
    public void addInventoryTransactionSalesNote(TransactionDTO trans,String note) throws ServiceError {
        
      TypeDTO  toUse  = TypeRepository.getTypeDTO(TypeRepository.NOTE_TYPE.SALES);
        
    this.addInventoryTransactionNote(trans, toUse, note);
    }

    

    private Integer getPriceIfPresent(Integer item, double price) throws ServiceError{
        
         List<PriceDTO> prices = this.getItemService().getActivePrices(new ItemDTO(item));

                
                for(PriceDTO entry : prices){
                    
         double diff  = Math.abs(entry.getSalePrice() - price);
        
         if(diff < 0.01){
             return entry.getKey();
         }
         
                }
         
    
    
    return -1;
    
    
    }
    
    public TypeDTO getDefaultCurrency(){
       return TypeRepository.getTypeDTO(TypeRepository.CURRENCY.USD);

    }
    
    private String getStdPriceCode(Integer item, double price, Integer employee){
        Date curr = new Date(System.currentTimeMillis());
        
        String ret = "ITM:" + item + "_PRC:"+price + "_EMP:" + employee;
        ret = ret +"_ON:"+curr.toString();
        return ret;
        
    }
    
    
    
    public PriceDTO addPrice(Integer item ,double price, Integer employee) throws ServiceError{
       String code = this.getStdPriceCode(item, price, employee);
        PriceDTO toAdd = new PriceDTO(item,0.0,price,false,false,this.getDefaultCurrency().getKey(),employee, code);
        return this.getItemService().addItemPrice(toAdd);
        
    }
    
    public PriceDTO addPrice(Integer item ,double price, double cost,Integer employee) throws ServiceError{
       String code = this.getStdPriceCode(item, price, employee);
        PriceDTO toAdd = new PriceDTO(item,cost,price,false,false,this.getDefaultCurrency().getKey(),employee, code);
        return this.getItemService().addItemPrice(toAdd);
        
    }
    
    
    public Integer getPrice(Integer item, double price) throws ServiceError{
        
        Integer ret = this.getPriceIfPresent(item, price);
   
        
        
        if(ret < 0){
      throw new ServiceError("No valid price found!");
        }
        
        
        return ret;
              
    }
    
    
}
