/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.manager;

import CASS.data.BaseDTO;
import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.item.InventoryItemDTO;
import CASS.data.item.ItemDTO;
import CASS.data.item.ItemOwnershipDTO;
import CASS.data.item.PriceDTO;
import CASS.data.item.SerializedItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.AccountDTO;
import CASS.data.person.CompanyDTO;
import CASS.data.person.EmployeeDTO;
import CASS.data.person.PersonDTO;
import CASS.services.ExtendedItemService;
import CASS.services.ItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import CASS.util.DataObjectGenerator;
import CASS.util.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    
    Integer shopAcc;
    
    Integer facility;
    
    private Map<Integer,Map<CompanyDTO, Integer>> cachedCompanies;
    
    
    private Map<Integer, ItemDTO> cahcedItems;
    
    private Map<Integer, SerializedItemDTO> cachedSerialized;
    
    

    public InventoryManager(ExtendedItemService svc, PersonManager prsnSvc, Integer shopAcc, Integer facility) {
        this.svc = svc;
        this.prsnSvc = prsnSvc;
        this.shopAcc = shopAcc;
        this.facility = facility;
this.resetCache();
    }

    
    
    
    public void resetCache(){
        this.cachedCompanies = DataObjectGenerator.createMap();
        this.cahcedItems = DataObjectGenerator.createMap();
        this.cachedSerialized = DataObjectGenerator.createMap();
    }
    
    private Map<Integer,Map<CompanyDTO, Integer>> getCachedCompanies(){
        return this.cachedCompanies;
    }
    private Map<Integer,ItemDTO> getCachedItems(){
        return this.cahcedItems;
    }
    
    private Map<Integer,SerializedItemDTO> getCachedSerializedItems(){
        return this.cachedSerialized;
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

    public int getFacility() {
        return this.facility;
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
this.resetCache();
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
        int facility = this.getFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty, facility, type, true);

        return this.addInventoryTransaction(trans);

    }

    public TransactionDTO addSaleTransaction(int item, int employee, int qty) throws ServiceError {
        int facility = this.getFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty, facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SALE), true);

        return this.addInventoryTransaction(trans);

    }

    public TransactionDTO addShipmentTransaction(int item, int employee, int qty) throws ServiceError {
        int facility = this.getFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty, facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SHIPMENT), true);

        return this.addInventoryTransaction(trans);

    }

    public TransactionDTO addRecieveTransaction(int item, int employee, int qty) throws ServiceError {
        int facility = this.getFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty, facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RECIEVE), true);

        return this.addInventoryTransaction(trans);

    }

    public TransactionDTO addReleaseTransaction(int item, int employee, int qty) throws ServiceError {
        int facility = this.getFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty, facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RELEASE), true);

        return this.addInventoryTransaction(trans);

    }

    public TransactionDTO addReturnTransaction(int item, int employee, int qty) throws ServiceError {
        int facility = this.getFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty, facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RETURN), true);

        return this.addInventoryTransaction(trans);

    }

    public TransactionDTO addServiceTransaction(int item, int employee, int qty) throws ServiceError {
        
        int facility = this.getFacility();
        TransactionDTO trans = new TransactionDTO(item, employee, qty, facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.SERVICE), true);

        return this.addInventoryTransaction(trans);

    }

    public TransactionDTO addReturnRemovedTransaction(int item, int employee, int qty) throws ServiceError {
   this.resetCache();
        int facility = this.getFacility();

        TransactionDTO trans = new TransactionDTO(item, employee, qty, facility, TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RETURN_REMOVED), true);

        String note = "ITEM RETURNED BUT NOT SUITABLE FOR RESALE.";

        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.NOTE_TYPE.SALES);

        trans = this.addInventoryTransaction(trans);
        this.addInventoryTransactionNote(trans, type, note);
        return trans;

    }

    public Integer addItem(ItemDTO toAdd,Integer adder) throws ServiceError {
           this.resetCache();
        Integer ret = this.getItemService().addItem(toAdd);
        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.NOTE_TYPE.INVENTORY);
 TransactionDTO inven = new TransactionDTO(ret,adder,0,this.getFacility(),type.getKey(),true);
        
        this.addInventoryTransaction(inven);
   
        toAdd.setKey(ret);
        return ret;
    }

    public void checkTransaction(TransactionDTO entry) throws ServiceError {

        try {

            this.getPersonService().employeeValid(entry.getEmployee());

            if (entry.getQuantity() < 0) {
                throw new ServiceError("Negative transaction quantity!");
            }

            int multi = this.getItemService().getMultiplyer(entry.getType());
            int qty = entry.getQuantity() * multi;

            int adjustdQty = qty + this.getQty(entry.getItem(), entry.getFacility());

            if (adjustdQty < 0) {
                throw new ServiceError("INSUFFICENT INVENTORY!");
            }

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    public TransactionDTO addInventoryTransaction(TransactionDTO toAdd) throws ServiceError {
        this.checkTransaction(toAdd);
   this.resetCache();
        TransactionDTO ret = this.getItemService().addInventoryTransaction(toAdd);
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
          this.resetCache();
        this.getItemService().addInventoryTransactionNote(trans, toUse, note);
    }

    public void addInventoryTransactionSalesNote(TransactionDTO trans, String note) throws ServiceError {
   this.resetCache();
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

    public PriceDTO getPrice(Integer price) throws ServiceError {
        return this.getItemService().getPrice(new BaseDTO(price));
    }

    public void checkPrice(PriceDTO toCheck) throws ServiceError {

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
          this.resetCache();
        String code = this.getStdPriceCode(item, price, employee);
        PriceDTO toAdd = new PriceDTO(item, 0.0, price, false, false, this.getDefaultCurrency().getKey(), employee, code);
        return this.getItemService().addItemPrice(toAdd);

    }

    public PriceDTO addPrice(Integer item, double price, double cost, Integer employee) throws ServiceError {
        this.resetCache();
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
this.resetCache();
        return this.getItemService().addSerializedItem(new ItemDTO(item), sn, forSale).getKey();

    }

    public Integer addSerializedItem(SerializedItemDTO toAdd) throws ServiceError {
this.resetCache();
        return this.getItemService().addSerializedItem(new ItemDTO(toAdd.getItem()), toAdd.getSerialNumber(), toAdd.isForSale()).getKey();

    }

  
    public Integer addSerializedItem(Integer item, String sn, Integer employee, boolean forSale, boolean addToInventory) throws ServiceError {
        return this.addSerializedItem(item, sn, employee, this.getFacility(), forSale, addToInventory);
    }

    public Integer addSerializedItem(Integer item, String sn, Integer employee) throws ServiceError {
        return this.addSerializedItem(item, sn, employee, this.getFacility(), true, true);
    }

    public Integer possesSerializedItem(Integer item, String sn, Integer employee, boolean forSale, boolean addToInventory) throws ServiceError {
        return this.addSerializedItem(item, sn, employee, this.getFacility(), forSale, false);
    }

    public Integer addSerializedItem(Integer item, String sn, Integer employee, Integer facility, boolean forSale, boolean addToInventory) throws ServiceError {

        SerializedItemDTO ser = this.getItemService().addSerializedItem(new ItemDTO(item), sn, forSale);
        this.addSerializedItemToInventory(ser, employee, facility, addToInventory);
        return ser.getKey();
    }

    private void addSerializedItemToInventory(SerializedItemDTO toAdd, Integer employee, Integer facility, boolean addToInventory) throws ServiceError {
this.resetCache();
        int type = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RECIEVE);

        if (addToInventory == false) {
            type = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.NON_INVENTORY_RECIEVE);
        }

        TransactionDTO trans = new TransactionDTO(toAdd.getItem(), employee, 1, facility, type, true);

        trans = this.addInventoryTransaction(trans);

        this.getItemService().addSerializedItemToInventory(toAdd, trans);

        this.getItemService().setSerializedItemOwnership(toAdd, new AccountDTO(this.getShopAccount()));
        
    }

    public List<SerializedItemDTO> getSerializedItemsForSale(Integer item) throws ServiceError {

        List<SerializedItemDTO> items = this.getSerializedItemsFromInventory(item);
        return items.stream().filter(value -> {
            return value.isForSale();
        }).collect(Collectors.toList());
    }

    public List<SerializedItemDTO> getSerializedItemsFromInventory(Integer item) throws ServiceError {
        return this.getSerializedItemsFromInventory(item, this.getFacility());
    }

    public List<SerializedItemDTO> getSerializedItemsFromInventory(Integer item, Integer facility) throws ServiceError {

        SerializedItemDTO[] items = this.getItemService().getSerializedItems(new ItemDTO(item), new BaseDTO(facility));
        return Arrays.asList(items);
    }

    public void removeSerializedItem(Integer item, Integer employee) throws ServiceError {
        SerializedItemDTO ser = new SerializedItemDTO(item);
        this.getItemService().removeSerializedItem(ser);

        this.getItemService().removeSerializedItemFromInventory(ser);
        this.getItemService().clearSerializedItemOwnership(ser);
        int type = TypeRepository.getKey(TypeRepository.NOTE_TYPE.INVENTORY);

        String note = "SERIALIZED ITEM REMOVED FROM INVENTORY";

        this.addSerializedItemNote(item, employee, type, note);
this.resetCache();
    }

    public void sellSerializedItem(Integer item, Integer employee, Integer acc) throws ServiceError {

        SerializedItemDTO ser = new SerializedItemDTO(item);
        this.getItemService().removeSerializedItem(ser);

        this.getItemService().removeSerializedItemFromInventory(ser);

        setSerializedItemOwnershipBySell(item, acc, employee);

    }

    public void addSerializedItemNote(Integer item, Integer employee, Integer type, String note) throws ServiceError {
        this.getItemService().addSerializedItemNote(new SerializedItemDTO(item), new EmployeeDTO(employee), new TypeDTO(type), note);
    }

    public SerializedItemDTO getSerialziedItem(Integer key) throws ServiceError {
        
        
        if(this.getCachedSerializedItems().containsKey(key)){
            return this.getCachedSerializedItems().get(key);
        }
        
        
        
        
        
        SerializedItemDTO ret = this.getItemService().getSerializedItem(new SerializedItemDTO(key));
        
        this.getCachedSerializedItems().put(key,ret);
        
        return ret;
    }
    
    public Map<SerializedItemDTO,ItemDTO> getAllSerialziedItems() throws ServiceError{
        
      
        SerializedItemDTO[] items = this.getItemService().getAllSerializedItems();
        
        Map<SerializedItemDTO,ItemDTO> ret = DataObjectGenerator.createMap();
        
        for(int index = 0; index < items.length; index++){
           SerializedItemDTO item = items[index];
            this.getCachedSerializedItems().put(item.getKey(), item);
                       ret.put(item,this.getItem(item.getItem()));
        }
        
        
        return ret;
    }
    

    public ItemDTO getItem(Integer item) throws ServiceError {
        
        
        if(this.getCachedItems().containsKey(item)){
            return this.getCachedItems().get(item);
        }
        ItemDTO ret = this.getItemService().getItem(new BaseDTO(item));
        
        
        this.getCachedItems().put(item, ret);
        
        
        return ret;
    }

    public void setSerializedItemOwnership(Integer item, Integer owner, Integer employee) throws ServiceError {
        this.setSerializedItemOwnership(item, owner, employee, false);
        
        if(owner != this.getShopAccount()){
            this.getItemService().removeSerializedItemFromInventory(new SerializedItemDTO(item));
        }
       
    }

    
    public void addSerializedItemToInventory(SerializedItemDTO item, Integer employee) throws ServiceError{
      //  Integer item, Integer emp, Integer qty, Integer facility, Integer type, boolean isVali
      
        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.TRANSACTION_TYPE.RECIEVE);

        TransactionDTO trans = new TransactionDTO(item.getItem(), employee,1, this.getFacility(), type.getKey(),true);
        
        this.getItemService().addSerializedItemToInventory(item, trans);
        
    }
    
    
    
    
    
    
    public void setSerializedItemOwnershipBySell(Integer item, Integer owner, Integer employee) throws ServiceError {
        EmployeeDTO emp = this.getPersonService().getEmployee(employee);

        AccountDTO acc = this.getPersonService().getAccount(owner);

        SerializedItemDTO sn = this.getSerialziedItem(item);

        ItemDTO base = this.getItem(sn.getItem());

        String note = "ITEM " + sn.getSerialNumber() + " / " + base.getItemName();
        note += " SOLD TO ACC: " + acc.getAccountName() + "/";
        note += acc.getAccountNumber();
         this.getItemService().removeSerializedItemFromInventory(sn);
        this.setSerializedItemOwnership(item, owner, employee, true, note);

    }

    public Integer getShopAccount(){
        return this.shopAcc;
    }
    
    
    public void setSerializedItemOwnership(Integer item, Integer owner, Integer employee, boolean ignoreChecks) throws ServiceError {
        EmployeeDTO emp = this.getPersonService().getEmployee(employee);

        AccountDTO acc = this.getPersonService().getAccount(owner);

        SerializedItemDTO sn = this.getSerialziedItem(item);

        ItemDTO base = this.getItem(sn.getItem());

        String note = "ITEM " + sn.getSerialNumber() + " / " + base.getItemName();
        note += " TRANSFERED OWNERSHIP TO ACC: " + acc.getAccountName() + "/";
        note += acc.getAccountNumber();
        this.setSerializedItemOwnership(item, owner, employee, ignoreChecks, note);

    }

    public void setSerializedItemOwnership(Integer item, Integer owner, Integer employee, boolean ignoreChecks, String note) throws ServiceError {
this.resetCache();
        if (ignoreChecks == false) {
            this.getPersonService().employeeValid(employee);
            this.getPersonService().accountValid(owner);
        }

        this.getItemService().setSerializedItemOwnership(new SerializedItemDTO(item), new AccountDTO(owner));

        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.NOTE_TYPE.OWNERSHIP);

        this.addSerializedItemNote(item, employee, type.getKey(), note);

    }
    
    
       public List<InventoryItemDTO> getInventory() throws ServiceError{
       return this.getInventory(this.getFacility());
       }
    
    
    public List<InventoryItemDTO> getInventory(Integer facility) throws ServiceError{
     
        InventoryItemDTO[] results = this.getItemService().getInventory(new BaseDTO(facility));
      return  Arrays.asList(results);
     
    }
    public Map<TypeDTO, Integer> getTypesInInventory() throws ServiceError{
        return this.getTypesInInventory(this.getFacility());
        
    }
    
    
    
    public Map<TypeDTO, Integer> getTypesInInventory(Integer facility) throws ServiceError{
        List<InventoryItemDTO> items = this.getInventory(facility);
        
        
        Map<Integer, Integer> count = DataObjectGenerator.createMap();
        
        
        
        
        items.forEach(item ->{
        
            int typeId = item.getItemType();
            
            if(count.containsKey(typeId) == false){
                count.put(typeId,0);
                            }
        
           count.put(typeId,count.get(typeId) + 1);
    
        });
        
          Map<TypeDTO, Integer> ret = DataObjectGenerator.createMap();
       
        
        count.keySet().forEach(entry ->{
        TypeDTO type = TypeRepository.getItemType(entry);
        
        ret.put(type, count.get(entry));
        
        
        });
        
        
        
        
        
        return ret;
    }
    
        
    public Map<CompanyDTO, Integer> getCompaniesInInventory() throws ServiceError{
        return this.getCompaniesInInventory(this.getFacility());
        
    }
    
    
    
    
    
    public Map<CompanyDTO, Integer> getCompaniesInInventory(Integer facility) throws ServiceError{
        List<InventoryItemDTO> items = this.getInventory(facility);
        
        
        Map<Integer, Integer> count = DataObjectGenerator.createMap();
        
        
        
        
        items.forEach(item ->{
        
            int typeId = item.getCompany();
            
            if(count.containsKey(typeId) == false){
                count.put(typeId,0);
                            }
        
           count.put(typeId,count.get(typeId) + 1);
    
        });
        
          Map<CompanyDTO, Integer> ret = DataObjectGenerator.createMap();
    
        for(int entry : count.keySet()){
        
       CompanyDTO comp =  this.getPersonService().getCompany(entry);
        ret.put(comp, count.get(entry));
        
        
                }
        
        return ret;
       }
    
    
    
    
    public AccountDTO getOwner(Integer key) throws ServiceError{
              return this.getItemService().getItemOwner(new SerializedItemDTO(key));
     }
    
    
    public Map<Date,AccountDTO> getItemHistory(Integer key) throws ServiceError{
        
      ItemOwnershipDTO [] history =   this.getItemService().getAllOwnersForItem(new SerializedItemDTO(key));
      
      
      
      Map<Date,AccountDTO> ret = DataObjectGenerator.createMap();
      
      for(int index = 0; index < history.length; index++){
          
         Date date = DateUtils.parseDate(history[index].getCreatedDate());
         
         
         AccountDTO acc = this.getPersonService().getAccount(history[index].getAccount());
         ret.put(date, acc);
          
          
      }
      
     return ret;
        
    }
    
    public boolean hasItemWithAlias(String alias){
        return this.getItemByAlias(alias) != null;
    }
    
    
    public ItemDTO getItemByAlias(String alias){
        
        try{
            return this.getItemService().getItemByAlias(alias);
        }
        catch(Throwable e){
            return null;
        }
        
        
    }
    
    
    public SerializedItemDTO getSerializedItem(Integer item, String serialNumber){
        
        try {
            return this.getItemService().getSerializedItem(new ItemDTO(item), serialNumber);
        } catch (ServiceError ex) {
       return null;  }
        
        
    }
    
    
    
    
    
    

}
