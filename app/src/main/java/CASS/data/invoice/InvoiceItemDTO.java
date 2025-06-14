/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.invoice;

import CASS.data.BaseDTO;

/**
 *
 * @author ctydi
 */
public class InvoiceItemDTO extends BaseDTO{
 

    
    private Integer invoice;
    
    private Integer item;
    
    private Integer invoiceItemType;
    
    private Integer facility;
    
    private Integer quantity;
    
    private Integer priceId;
    
 
    
    private double tax;
    
    private double adjustment;
    
    private Integer inventoryTransactionId;

    public InvoiceItemDTO(Integer invoice, Integer invoiceItemType, Integer item, Integer facility, Integer quantity, Integer priceId, double cost,  double adjustment) {
        this.invoice = invoice;
        this.invoiceItemType = invoiceItemType;
        this.facility = facility;
        this.quantity = quantity;
        this.priceId = priceId;
        this.tax = cost;
        this.adjustment = adjustment;
        this.item = item;
    }
    
       public InvoiceItemDTO(Integer key, Integer invoice, Integer invoiceItemType, Integer item, Integer facility, Integer quantity, Integer priceId, double cost, double adjustment) {
        super(key);
           this.invoice = invoice;
        this.invoiceItemType = invoiceItemType;
        this.facility = facility;
        this.quantity = quantity;
        this.priceId = priceId;
        this.tax = cost;
        this.adjustment = adjustment;
        this.item = item;
    }
    
       
       
       public InvoiceItemDTO(Integer invoice, Integer transactionId,Integer qty, Integer price,double tax, double adj){
           this.inventoryTransactionId = transactionId;
           this.tax = tax;
           this.quantity = qty;
           this.adjustment = adj;
          this.priceId = price;
           this.invoice = invoice;           
       }
       
    


    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public Integer getInvoiceItemType() {
        return invoiceItemType;
    }

    public Integer getFacility() {
        return facility;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPriceId() {
        return priceId;
    }

  

    public double getTax() {
        return tax;
    }

    public double getAdjustment() {
        return adjustment;
    }

    public Integer getInventoryTransactionId() {
        return inventoryTransactionId;
    }

    public Integer getItem() {
        return item;
    }

    public void setInventoryTransactionId(Integer inventoryTransactionId) {
        this.inventoryTransactionId = inventoryTransactionId;
    }

    public void setInventoryTransactionType(Integer transactionType) {
     this.invoiceItemType = transactionType;}

    public void setItemId(Integer item) {
   this.item = item;  }
    
    
    
    
}
