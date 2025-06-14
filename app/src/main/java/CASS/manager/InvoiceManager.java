/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.manager;

import CASS.data.BaseDTO;
import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.invoice.InvoiceDTO;
import CASS.data.invoice.InvoiceItemDTO;
import CASS.data.item.PriceDTO;
import CASS.data.item.SerializedItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.AccountDTO;
import CASS.data.person.EmployeeDTO;
import CASS.services.ExtendedItemService;
import CASS.services.InvoiceService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import java.util.Date;

/**
 *
 * @author ctydi
 */
public class InvoiceManager {

    InventoryManager svc;

    PersonManager prsnSvc;
    
    InvoiceService inv;

    public InvoiceManager(InvoiceService inv, InventoryManager svc, PersonManager prsnSvc) {
        this.svc = svc;
        this.inv = inv;
        this.prsnSvc = prsnSvc;
    }

    private PersonManager getPersonService() {
        return this.prsnSvc;
    }

    private InventoryManager getItemService() {
        return this.svc;
    }

    private InvoiceService getInvoiceService() {
return this.inv;
    }

    
    private int getFacility(){
        return this.getItemService().getFacility();
        
    }
    
    
    
    
    private TransactionDTO getTransaction(InvoiceDTO invoice, InvoiceItemDTO toCheck) {
        int facility = this.getFacility();
        return new TransactionDTO(toCheck.getItem(), invoice.getEmployeeID(), toCheck.getQuantity(),facility, toCheck.getInvoiceItemType(), true);

    }

    private void checkInvoiceItem(InvoiceDTO invoice, InvoiceItemDTO toCheck) throws ServiceError {
        TransactionDTO entry = this.getTransaction(invoice, toCheck);

    this.getItemService().checkTransaction(entry);
        PriceDTO price = this.getItemService().getPrice(toCheck.getPriceId());
       
        this.getItemService().checkPrice(price);
        
    
        if(price.getItem() != entry.getItem()){
            throw new ServiceError("ITEM DOES NOT MATCH PRICE ITEM!");
        }
        
        
        
        
        
        
    }

    public InvoiceDTO addInvoice(InvoiceDTO toAdd, InvoiceItemDTO[] items) throws ServiceError {

        if (this.getPersonService().accountValid(toAdd.getAccountID()) == false) {
            throw new ServiceError("Account Invalid!");
        }

        for (InvoiceItemDTO item : items) {
           
            this.checkInvoiceItem(toAdd, item);
           
        }

        toAdd = this.getInvoiceService().addInvoice(toAdd);

        for (InvoiceItemDTO item : items) {
            this.addInvoiceItem(toAdd, item);
        }

        return toAdd;

    }

    private InvoiceItemDTO addInvoiceItem(InvoiceDTO invoice, InvoiceItemDTO toAdd) throws ServiceError {
        this.checkInvoiceItem(invoice, toAdd);
         
        toAdd.setInvoice(invoice.getKey());

        TransactionDTO trans = this.getTransaction(invoice, toAdd);
        trans = this.getItemService().addInventoryTransaction(trans);
        toAdd.setInventoryTransactionId(trans.getKey());
       InvoiceItemDTO ret = this.getInvoiceService().addInvoiceItem(toAdd);
       toAdd.setKey(ret.getKey());
       return ret;
    }

    public static String createInvoiceName(Integer account, Integer emp, Integer type){
        Date curr = new Date(System.currentTimeMillis());
        String ret = "INVOICE:"+account +"_EMP:"+ emp +"_TYPE:"+type+"_ON:" + curr;
        
        return ret;
        
    }
    
    public InvoiceDTO createInvoice(Integer account, Integer employee, Integer type, InvoiceItemDTO[] items) throws ServiceError {

        String name= this.createInvoiceName(account,employee,type);
        
        InvoiceDTO toAdd = new InvoiceDTO(name,account, employee, type);

        return this.addInvoice(toAdd, items);

    }

    public InvoiceDTO createSalesInvoice(Integer account, Integer employee, InvoiceItemDTO[] items) throws ServiceError {

        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.INVOICE_TYPE.SALE);

        return this.createInvoice(account, employee, type.getKey(), items);

    }

    public InvoiceDTO createSalesInvoice(Integer account, Integer employee) throws ServiceError {
        return this.createSalesInvoice(account, employee, new InvoiceItemDTO[0]);
    }

   

    public double calculateTax(Integer priceId) {
        return 0;

    }

    public InvoiceItemDTO createInvoiceItem(InvoiceDTO invoice, Integer item, Integer qty, Integer transactionType, Integer facility,  Integer price, Double tax, Double adj) throws ServiceError 
    {
     
     
       InvoiceItemDTO toAdd =  new InvoiceItemDTO(invoice.getKey(),0,qty,price,tax,adj );
       toAdd.setItemId(item);
       toAdd.setInventoryTransactionType(transactionType);
     return  this.addInvoiceItem(invoice, toAdd);
       
       
    }
    
      public InvoiceItemDTO createSaleInvoiceItem(InvoiceDTO invoice, Integer item, Integer qty,  Integer facility,  Integer price, Double tax, Double adj) throws ServiceError 
    {
     TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.TRANSACTION_TYPE.SALE);
        return this.createInvoiceItem(invoice, item, qty, type.getKey(), facility, price, tax, adj);
    }
 

    public Integer getPrice(Integer item, double price) {

        return 0;

    }

    public Integer getDefaultFacility(){
        return 1;
        
    }
   
    
   public InvoiceItemDTO sellSerializedItem(Integer serializedItemId,Integer price, Integer invoice) throws ServiceError{
       InvoiceDTO invoiceDto = this.getInvoiceService().getInvoice(new BaseDTO(invoice));
       Integer facility = this.getFacility();
       
       return this.sellSerializedItem(serializedItemId,price,invoiceDto, facility);
  
   }
    
    
    
    
    public InvoiceItemDTO sellSerializedItem(Integer serializedItemId,Integer price, InvoiceDTO invoice, Integer facility) throws ServiceError{
        
 SerializedItemDTO sn = this.getItemService().getSerialziedItem(serializedItemId);
 
        
   InvoiceItemDTO  ret =    this.createSaleInvoiceItem(invoice, sn.getItem(), 1, facility, price, 0.0,  0.0);
 
   this.getItemService().removeSerializedItem(sn.getKey(), invoice.getEmployeeID(), true);
   
   this.getInvoiceService().addSerializedItemToInvoiceItem(sn, ret);
   return ret;
        
    }

}
