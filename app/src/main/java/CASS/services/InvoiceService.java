/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services;

import CASS.data.BaseDTO;
import CASS.data.CreatedDTO;
import CASS.data.invoice.InvoiceDTO;
import CASS.data.invoice.InvoiceItemDTO;
import CASS.data.invoice.ShipmentDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.AccountDTO;
import CASS.data.person.EmployeeDTO;
import java.util.List;

/**
 *
 * @author ctydi
 */
public abstract class InvoiceService {

    public abstract InvoiceDTO getInvoice(BaseDTO key) throws ServiceError;

    public abstract InvoiceDTO[] getInvoices() throws ServiceError;

    public abstract InvoiceDTO[] getInvoicesForAccount(AccountDTO owner) throws ServiceError;

    public abstract InvoiceItemDTO[] getInvoiceItems(InvoiceDTO invoice) throws ServiceError;

    public abstract InvoiceDTO addInvoice(InvoiceDTO toAdd) throws ServiceError;

    public abstract InvoiceItemDTO addInvoiceItem(InvoiceItemDTO toAdd) throws ServiceError;
    
    
    public abstract ShipmentDTO addShipment(ShipmentDTO shipment) throws ServiceError;

    

    public InvoiceDTO addInvoice(InvoiceDTO toAdd, InvoiceItemDTO[] items) throws ServiceError {

        toAdd = this.addInvoice(toAdd);

        for (InvoiceItemDTO item : items) {
            item.setInvoice(toAdd.getKey());
            this.addInvoiceItem(item);

        }
        return toAdd;
    }

}
