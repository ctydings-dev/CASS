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

    public InvoiceManager(InventoryManager svc, PersonManager prsnSvc) {
        this.svc = svc;
        this.prsnSvc = prsnSvc;
    }

    private PersonManager getPersonService() {
        return this.prsnSvc;
    }

    private InventoryManager getItemService() {
        return this.svc;
    }

    private InvoiceService getInvoiceService() {
        return null;
    }

    private TransactionDTO getTransaction(InvoiceDTO invoice, InvoiceItemDTO toCheck) {
        return new TransactionDTO(toCheck.getItem(), invoice.getEmployeeID(), toCheck.getQuantity(), toCheck.getInvoiceItemType(), true);

    }

    public boolean checkInvoiceItem(InvoiceDTO invoice, InvoiceItemDTO toCheck) throws ServiceError {
        TransactionDTO entry = this.getTransaction(invoice, toCheck);

        return this.getItemService().checkTransaction(entry);

    }

    public InvoiceDTO addInvoice(InvoiceDTO toAdd, InvoiceItemDTO[] items) throws ServiceError {

        if (this.getPersonService().accountValid(toAdd.getAccountID()) == false) {
            throw new ServiceError("Account Invalid!");
        }

        for (InvoiceItemDTO item : items) {
            if (this.checkInvoiceItem(toAdd, item) == false) {
                throw new ServiceError("Invalid Invoice Item!");
            }
        }

        toAdd = this.getInvoiceService().addInvoice(toAdd);

        for (InvoiceItemDTO item : items) {
            this.addInvoiceItem(toAdd, item);
        }

        return toAdd;

    }

    public InvoiceItemDTO addInvoiceItem(InvoiceDTO invoice, InvoiceItemDTO toAdd) throws ServiceError {
        if (this.checkInvoiceItem(invoice, toAdd) == false) {
            throw new ServiceError("Invalid Invoice Item!");
        }
        toAdd.setInvoice(invoice.getKey());

        TransactionDTO trans = this.getTransaction(invoice, toAdd);
        trans = this.getItemService().addInventoryTransaction(trans);
        toAdd.setInventoryTransactionId(trans.getKey());
        return this.getInvoiceService().addInvoiceItem(toAdd);
    }

    public InvoiceDTO createInvoice(Integer account, Integer employee, Integer type, InvoiceItemDTO[] items) throws ServiceError {

        InvoiceDTO toAdd = new InvoiceDTO(account, employee, type);

        return this.addInvoice(toAdd, items);

    }

    public InvoiceDTO createSalesInvoice(Integer account, Integer employee, InvoiceItemDTO[] items) throws ServiceError {

        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.INVOICE_TYPE.SALE);

        return this.createInvoice(account, employee, type.getKey(), items);

    }

    public InvoiceDTO createSalesInvoice(Integer account, Integer employee) throws ServiceError {
        return this.createSalesInvoice(account, employee, new InvoiceItemDTO[0]);
    }

    public InvoiceItemDTO createInvoiceItemDTO(InvoiceDTO invoice, Integer item, Integer type, Integer qty, Integer facility, Integer price, Double tax, Double adj) throws ServiceError {
        InvoiceItemDTO toAdd = new InvoiceItemDTO(invoice.getKey(), type, facility, qty, price, tax, adj);
        return this.addInvoiceItem(invoice, toAdd);
    }

    public double calculateTax(Integer priceId) {
        return 0;

    }

    public InvoiceItemDTO createSaleInvoiceItem(InvoiceDTO invoice, Integer item, Integer qty, Integer facility, Integer price) throws ServiceError {

        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.TRANSACTION_TYPE.SALE);
        Double tax = this.calculateTax(price);

        return this.createSaleInvoiceItem(invoice, item, qty, facility, price, tax, 0.0);

    }

    public Integer getPrice(Integer item, double price) {

        return 0;

    }

    public InvoiceItemDTO createSaleInvoiceItem(InvoiceDTO invoice, Integer item, Integer qty, Integer facility, double price) throws ServiceError {

        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.TRANSACTION_TYPE.SALE);
        Integer priceId = this.getPrice(item, price);
        Double tax = this.calculateTax(priceId);
        return this.createSaleInvoiceItem(invoice, item, qty, facility, priceId, tax, 0.0);

    }

    public InvoiceItemDTO createSaleInvoiceItem(InvoiceDTO invoice, Integer item, Integer qty, Integer facility, Integer price, Double tax, Double adj) throws ServiceError {

        TypeDTO type = TypeRepository.getTypeDTO(TypeRepository.TRANSACTION_TYPE.SALE);

        return this.createInvoiceItemDTO(invoice, item, type.getKey(), qty, facility, price, tax, adj);

    }

}
