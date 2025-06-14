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
import CASS.data.item.SerializedItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.EmployeeDTO;
import CASS.data.person.PersonDTO;
import CASS.services.ExtendedItemService;
import CASS.services.ItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static java.util.Map.entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    
    public void reverseTransaction(TransactionDTO toReverse, EmployeeDTO reverser) throws ServiceError {
        try {
            this.addTransactionReversalNote(toReverse, reverser);
            this.getItemService().reverseTransaction(toReverse, reverser);
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }
    }
    
    public int getFacility(){
        return this.getDefaultFacility();
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
    
    public Integer getQty(Integer itemKey, Integer facility) throws ServiceError {
        return this.getItemService().getItemQty(new ItemDTO(itemKey), new BaseDTO(facility));
    }
    
    public TransactionDTO addTransaction(int item, int employee, int qty, int type) throws ServiceError {
          int facility = this.getDefaultFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty,facility, type, true);
        
        return this.addInventoryTransaction(trans);
        
    }
    
    public TransactionDTO addSaleTransaction(int item, int employee, int qty) throws ServiceError {
          int facility = this.getDefaultFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty, facility,TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SALE), true);
        
        return this.addInventoryTransaction(trans);
        
    }
    
    public TransactionDTO addShipmentTransaction(int item, int employee, int qty) throws ServiceError {
          int facility = this.getDefaultFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty,facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SHIPMENT), true);
        
        return this.addInventoryTransaction(trans);
        
    }
    
    public TransactionDTO addRecieveTransaction(int item, int employee, int qty) throws ServiceError {
          int facility = this.getDefaultFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty, facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RECIEVE), true);
        
        return this.addInventoryTransaction(trans);
        
    }    
    
    public TransactionDTO addReleaseTransaction(int item, int employee, int qty) throws ServiceError {
          int facility = this.getDefaultFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty,facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RELEASE), true);
        
        return this.addInventoryTransaction(trans);
        
    }    
    
    public TransactionDTO addReturnTransaction(int item, int employee, int qty) throws ServiceError {
          int facility = this.getDefaultFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty,facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RETURN), true);
        
        return this.addInventoryTransaction(trans);
        
    }
    
    public TransactionDTO addServiceTransaction(int item, int employee, int qty) throws ServiceError {
          int facility = this.getDefaultFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty,facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SERVICE), true);
        
        return this.addInventoryTransaction(trans);
        
    }

    public TransactionDTO addReturnRemovedTransaction(int item, int employee, int qty) throws ServiceError {
        
        int facility = this.getDefaultFacility();
        
        TransactionDTO trans = new TransactionDTO(item, employee, qty,facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RETURN_REMOVED), true);
        
        String note = "ITEM RETURNED BUT NOT SUITABLE FOR RESALE.";
        
        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.NOTE_TYPE.SALES);
        
        trans = this.addInventoryTransaction(trans);
        this.addInventoryTransactionNote(trans, type, note);        
        return trans;
        
    }
    
    public Integer addItem(ItemDTO toAdd) throws ServiceError {
        Integer ret =  this.getItemService().addItem(toAdd);
        toAdd.setKey(ret);
        return ret;
    }
    
    public void checkTransaction(TransactionDTO entry) throws ServiceError {
        
        try {
            
            if (this.getPersonService().employeeValid(entry.getEmployee()) == false) {
             throw new ServiceError("INVALID EMPLOYEE!");
            }
            
            if (entry.getQuantity() < 1) {
                throw new ServiceError("Nonpositive transaction quantity!");
            }
            
            int multi = this.getItemService().getMultiplyer(entry.getType());
            int qty = entry.getQuantity() * multi;
            
            int adjustdQty = qty + this.getQty(entry.getItem(), entry.getFacility());
            
            if(adjustdQty < 0){
                throw new ServiceError("INSUFFICENT INVENTORY!");
            }
            
            
            
        
            
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }
        
    }
    
    public TransactionDTO addInventoryTransaction(TransactionDTO toAdd) throws ServiceError {
       this.checkTransaction(toAdd);
       
        TransactionDTO ret =  this.getItemService().addInventoryTransaction(toAdd);
        toAdd.setKey(ret.getKey());
        return ret;
        
    }
    
    public static String createAccountNumber(PersonDTO person, TypeDTO type) {
        String name = "" + person.getFirstName().charAt(0);
        name = name + "_" + person.getLastName() + "_" + type.getTypeName();
        name = name + "-" + person.getKey();
        return name;
        
    }
    
    public void addInventoryTransactionNote(TransactionDTO trans, TypeDTO toUse, String note) throws ServiceError {
        this.getItemService().addInventoryTransactionNote(trans, toUse, note);
    }
    
    public void addInventoryTransactionSalesNote(TransactionDTO trans, String note) throws ServiceError {
        
        TypeDTO toUse = TypeRepository.getTypeDTO(TypeRepository.NOTE_TYPE.SALES);
        
        this.addInventoryTransactionNote(trans, toUse, note);
    }
    
    private Integer getPriceIfPresent(Integer item, double price) throws ServiceError {
        
        List<PriceDTO> prices = this.getItemService().getActivePrices(new ItemDTO(item));
        
        for (PriceDTO entry : prices) {
            
            double diff = Math.abs(entry.getSalePrice() - price);
            
            if (diff < 0.01) {
                return entry.getKey();
            }
            
        }
        
        return -1;
        
    }
    
    
    public PriceDTO getPrice(Integer price) throws ServiceError{
        return this.getItemService().getPrice(new BaseDTO(price)); 
    }
    
    
    
    public void checkPrice(PriceDTO toCheck) throws ServiceError{
     
        
    }
    
    
    
    public TypeDTO getDefaultCurrency() {
        return TypeRepository.getTypeDTO(TypeRepository.CURRENCY.USD);
        
    }
    
    private String getStdPriceCode(Integer item, double price, Integer employee) {
        Date curr = new Date(System.currentTimeMillis());
        
        String ret = "ITM:" + item + "_PRC:" + price + "_EMP:" + employee;
        ret = ret + "_ON:" + curr.toString();
        return ret;
        
    }
    
    public PriceDTO addPrice(Integer item, double price, Integer employee) throws ServiceError {
        String code = this.getStdPriceCode(item, price, employee);
        PriceDTO toAdd = new PriceDTO(item, 0.0, price, false, false, this.getDefaultCurrency().getKey(), employee, code);
        return this.getItemService().addItemPrice(toAdd);
        
    }
    
    public PriceDTO addPrice(Integer item, double price, double cost, Integer employee) throws ServiceError {
        String code = this.getStdPriceCode(item, price, employee);
        PriceDTO toAdd = new PriceDTO(item, cost, price, false, false, this.getDefaultCurrency().getKey(), employee, code);
        return this.getItemService().addItemPrice(toAdd);
        
    }
    
    public Integer getPrice(Integer item, double price) throws ServiceError {
        
        Integer ret = this.getPriceIfPresent(item, price);
        
        if (ret < 0) {
            throw new ServiceError("No valid price found!");
        }
        
        return ret;
        
    }
    
    public Integer addSerializedItem(Integer item, String sn, boolean forSale) throws ServiceError {
        
        return this.getItemService().addSerializedItem(new ItemDTO(item), sn, forSale).getKey();
        
    }
    
    public Integer addSerializedItem(SerializedItemDTO toAdd) throws ServiceError {
        
        return this.getItemService().addSerializedItem(new ItemDTO(toAdd.getItem()), toAdd.getSerialNumber(), toAdd.isForSale()).getKey();
        
    }
    
    
    public Integer getDefaultFacility(){
        return 1;
    }
    
     public Integer addSerializedItem(Integer item, String sn, Integer employee,boolean forSale, boolean addToInventory) throws ServiceError {
         return this.addSerializedItem(item,sn,employee,this.getDefaultFacility(),forSale,addToInventory);
              }
     
     public Integer addSerializedItem(Integer item, String sn, Integer employee) throws ServiceError {
         return this.addSerializedItem(item,sn,employee,this.getDefaultFacility(),true,true);
              }
     
     public Integer possesSerializedItem(Integer item, String sn, Integer employee, boolean forSale,boolean addToInventory) throws ServiceError {
         return this.addSerializedItem(item,sn,employee,this.getDefaultFacility(),forSale,false);
              }
    
    
    public Integer addSerializedItem(Integer item, String sn, Integer employee, Integer facility,boolean forSale, boolean addToInventory) throws ServiceError {
        
       SerializedItemDTO  ser =  this.getItemService().addSerializedItem(new ItemDTO(item), sn, forSale);
              this.addSerializedItemToInventory(ser, employee, facility, addToInventory);
               return ser.getKey();
    }
    
    private void addSerializedItemToInventory(SerializedItemDTO toAdd, Integer employee, Integer facility, boolean addToInventory) throws ServiceError {
        
        
        int type = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RECIEVE);
        
        if(addToInventory == false){
           type= TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.NON_INVENTORY_RECIEVE);
        }
        
        TransactionDTO trans = new TransactionDTO(toAdd.getItem(), employee,1,facility, type, true);
        
        trans = this.addInventoryTransaction(trans);
        
        this.getItemService().addSerializedItemToInventory(toAdd, trans);
        
    }
    
    
        public List<SerializedItemDTO> getSerializedItemsForSale(Integer item) throws ServiceError{
    
            List<SerializedItemDTO> items = this.getSerializedItemsFromInventory(item);
        return    items.stream().filter(value ->{
                return value.isForSale();
            }).collect(Collectors.toList());
                 }
    
    
    
        
        
        
        
        
        
        
        
    
     public List<SerializedItemDTO> getSerializedItemsFromInventory(Integer item) throws ServiceError{
         return this.getSerializedItemsFromInventory(item, this.getFacility());
     }
    
    
    
    public List<SerializedItemDTO> getSerializedItemsFromInventory(Integer item, Integer facility) throws ServiceError{
    
  SerializedItemDTO []  items = this.getItemService().getSerializedItems(new ItemDTO(item), new BaseDTO(facility));
          return Arrays.asList(items);
            }
    
    
    public void removeSerializedItem(Integer item, Integer employee, boolean sold) throws ServiceError{
        SerializedItemDTO ser= new SerializedItemDTO(item);
        this.getItemService().removeSerializedItem(ser);
        
        this.getItemService().removeSerializedItemFromInventory(ser);
               this.addRemovedSerializedItemNote(item, employee,sold);
        
        
        
    }
    
    
    
    public void addSerializedItemNote(Integer item, Integer employee, Integer type, String note) throws ServiceError{
        this.getItemService().addSerializedItemNote(new SerializedItemDTO(item), new EmployeeDTO(employee), new TypeDTO(type), note);
    }
    
  private void addRemovedSerializedItemNote(Integer item, Integer employee, boolean sold) throws ServiceError{
       int type = TypeRepository.getKey(TypeRepository.NOTE_TYPE.INVENTORY);
      
      String note = "SERIALIZED ITEM REMOVED FROM INVENTORY.";
      
      if(sold){
          note = "ITEM SOLD.";
          
      }
      
      
      this.addSerializedItemNote(item,employee,type,note);
      
  }
    
 
  
  public SerializedItemDTO getSerialziedItem(Integer key) throws ServiceError{
      
      return this.getItemService().getSerializedItem(new SerializedItemDTO(key));
      
      
  }
  
}
