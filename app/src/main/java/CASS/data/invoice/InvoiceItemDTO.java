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

    public InvoiceItemDTO(Integer invoice, Integer invoiceItemType, Integer facility, Integer quantity, Integer priceId, double cost, double adjustment) {
        this.invoice = invoice;
        this.invoiceItemType = invoiceItemType;
        this.facility = facility;
        this.quantity = quantity;
        this.priceId = priceId;
        this.tax = cost;
        this.adjustment = adjustment;
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
    
    
    
    
}
