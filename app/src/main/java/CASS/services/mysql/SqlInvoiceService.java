/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.data.BaseDTO;
import CASS.data.invoice.InvoiceDTO;
import CASS.data.invoice.InvoiceItemDTO;
import CASS.data.invoice.ShipmentDTO;
import CASS.data.item.ItemDTO;
import CASS.data.item.SerializedItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.AccountDTO;
import CASS.services.InvoiceService;
import CASS.services.ItemService;
import CASS.services.ServiceError;
import CASS.util.DataObjectGenerator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctydi
 */
public class SqlInvoiceService extends InvoiceService {

    SqlService svc;
    ItemService itm;

    public SqlInvoiceService(SqlService svc, ItemService itm) {
        this.svc = svc;
        this.itm  = itm;

    }

    private SqlService getService() {
        return svc;
    }

    private ItemService getItemService() {
        return this.itm;

    }

    private InvoiceDTO createInvoiceFromResultSet(ResultSet rs) throws SQLException {

        int key = rs.getInt(TABLE_COLUMNS.INVOICE.INVOICE_TABLE.ID);
        int type = rs.getInt(TABLE_COLUMNS.INVOICE.INVOICE_TABLE.TYPE);
        int emp = rs.getInt(TABLE_COLUMNS.INVOICE.INVOICE_TABLE.EMPLOYEE);
        int acc = rs.getInt(TABLE_COLUMNS.INVOICE.INVOICE_TABLE.ACCOUNT);
        String created = rs.getString(TABLE_COLUMNS.INVOICE.INVOICE_TABLE.CREATED_DATE);
        String finished = rs.getString(TABLE_COLUMNS.INVOICE.INVOICE_TABLE.FINISIHED);
        String name = rs.getString(TABLE_COLUMNS.INVOICE.INVOICE_TABLE.NAME);
        return new InvoiceDTO(name, acc, type, emp, finished, key, created);

    }

    private InvoiceItemDTO createInvoiceItemFromResultSet(ResultSet rs) throws SQLException, ServiceError {

        int key = rs.getInt(TABLE_COLUMNS.INVOICE.INVOICE_ITEM.ID);
        int invoice = rs.getInt(TABLE_COLUMNS.INVOICE.INVOICE_ITEM.INVOICE);

        int price = rs.getInt(TABLE_COLUMNS.INVOICE.INVOICE_ITEM.PRICE);
        int trans = rs.getInt(TABLE_COLUMNS.INVOICE.INVOICE_ITEM.TRANSACTION);
        double tax = rs.getDouble(TABLE_COLUMNS.INVOICE.INVOICE_ITEM.TAX);

        double adj = rs.getDouble(TABLE_COLUMNS.INVOICE.INVOICE_ITEM.ADJUSTMENT);

        TransactionDTO transaction = this.getItemService().getTransaction(new BaseDTO(trans));
        int qty = transaction.getQuantity();
        Integer item = transaction.getItem();
        Integer facility = transaction.getFacility();
        Integer type = transaction.getType();

        return new InvoiceItemDTO(key, invoice, type, item, facility, qty, price, tax, adj);

    }

    @Override
    public InvoiceDTO getInvoice(BaseDTO key) throws ServiceError {

        String stmt = "SELECT * FROM " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.TABLE_NAME;
        stmt = stmt + " WHERE " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.ID;
        stmt = stmt + " = " + key.getKey() + ";";
        try {
            ResultSet rs = this.getService().executeQuery(stmt);
            rs.next();
            return this.createInvoiceFromResultSet(rs);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public InvoiceDTO[] getInvoices() throws ServiceError {
        String stmt = "SELECT * FROM " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.TABLE_NAME;
        List<InvoiceDTO> invoices = DataObjectGenerator.createList();
        try {
            ResultSet rs = this.getService().executeQuery(stmt);
            while (rs.next()) {
                invoices.add(this.createInvoiceFromResultSet(rs));
            }

            InvoiceDTO[] ret = new InvoiceDTO[invoices.size()];
            for (int index = 0; index < ret.length; index++) {
                ret[index] = invoices.get(index);
            }

            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public InvoiceDTO[] getInvoicesForAccount(AccountDTO owner) throws ServiceError {
        String stmt = "SELECT * FROM " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.TABLE_NAME;

        stmt = stmt + " WEHRE " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.ACCOUNT;
        stmt = stmt + " = " + owner.getKey() + ";";

        List<InvoiceDTO> invoices = DataObjectGenerator.createList();
        try {
            ResultSet rs = this.getService().executeQuery(stmt);
            while (rs.next()) {
                invoices.add(this.createInvoiceFromResultSet(rs));
            }

            InvoiceDTO[] ret = new InvoiceDTO[invoices.size()];
            for (int index = 0; index < ret.length; index++) {
                ret[index] = invoices.get(index);
            }

            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }
    }

    @Override
    public InvoiceItemDTO[] getInvoiceItems(InvoiceDTO invoice) throws ServiceError {
        String stmt = "SELECT * FROM " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.TABLE_NAME;

        stmt = stmt + " WEHRE " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.INVOICE;
        stmt = stmt + " = " + invoice.getKey() + ";";

        List<InvoiceItemDTO> invoices = DataObjectGenerator.createList();
        try {
            ResultSet rs = this.getService().executeQuery(stmt);
            while (rs.next()) {
                invoices.add(this.createInvoiceItemFromResultSet(rs));
            }

            InvoiceItemDTO[] ret = new InvoiceItemDTO[invoices.size()];
            for (int index = 0; index < ret.length; index++) {
                ret[index] = invoices.get(index);
            }

            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public InvoiceDTO addInvoice(InvoiceDTO toAdd) throws ServiceError {
        String insert = this.getInvoiceInsert(toAdd);

        try {
            this.getService().executeStatement(insert);
            String query = "SELECT * FROM " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.TABLE_NAME;
            query = query + " ORDER BY " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.ID;
            query = query + " DESC;";
            ResultSet rs = this.getService().executeQuery(query);
            rs.next();
            return this.createInvoiceFromResultSet(rs);

        } catch (SQLException ex) {

            throw new ServiceError(ex);

        }

    }

    private String getInvoiceInsert(InvoiceDTO toAdd) {
        String stmt = "INSERT INTO " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.TABLE_NAME;
        stmt += " (" + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.ACCOUNT;
        stmt += ", " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.TYPE;
        stmt += ", " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.EMPLOYEE;
        if (toAdd.hasCreatedDate()) {
            stmt += ", " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.CREATED_DATE;
        }
        if (toAdd.hasFinishedDate()) {
            stmt += ", " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.FINISIHED;
        }

        
        stmt = stmt +", " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.NAME;
        
        
        stmt = stmt + ") VALUES (" + toAdd.getAccountID();
        stmt = stmt + ", " + toAdd.getInvoiceType();
        stmt = stmt + ", " + toAdd.getEmployeeID();

        if (toAdd.hasCreatedDate()) {
            stmt += ", '" + toAdd.getCreatedDate() + "'";
        }
        if (toAdd.hasFinishedDate()) {
            stmt += ", '" + toAdd.getFinishedDate() + "'";
        }

        
        stmt = stmt + ",'" + toAdd.getName() + "'";
        
        stmt = stmt + ");";

        return stmt;
    }

    @Override
    public InvoiceItemDTO addInvoiceItem(InvoiceItemDTO toAdd) throws ServiceError {
        try {
            String stmt = "INSERT INTO " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.TABLE_NAME;
            stmt = stmt + " (" + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.INVOICE;
            stmt += ", " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.PRICE;
            stmt += ", " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.ADJUSTMENT;
            stmt += ", " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.TAX;
            stmt += ", " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.TRANSACTION;
            stmt += ") VALUES (" + toAdd.getInvoice();
            stmt += ", " + toAdd.getPriceId();
            stmt += ", " + toAdd.getAdjustment();
            stmt += ", " + toAdd.getTax();
            stmt += ", " + toAdd.getInventoryTransactionId();
            stmt += ");";
            this.getService().executeStatement(stmt);

            String query = "SELECT * FROM " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.TABLE_NAME;
            query += " ORDER BY " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.ID;
            query += " DESC;";
            ResultSet rs = this.getService().executeQuery(query);
            rs.next();
            return this.createInvoiceItemFromResultSet(rs);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public ShipmentDTO addShipment(ShipmentDTO shipment) throws ServiceError {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void closeInvoice(InvoiceDTO toClose) throws ServiceError {
        try {
            String stmt = "UPDATE " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.TABLE_NAME;
            stmt = stmt + " SET " + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.FINISIHED;
            stmt = stmt + " = CURRENT_TIMESTAMP WHERE ";
            stmt = stmt + TABLE_COLUMNS.INVOICE.INVOICE_TABLE.ID + " = ";
            stmt = stmt + toClose.getKey() + ";";
            this.getService().executeStatement(stmt);
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public void addSerializedItemToInvoiceItem(SerializedItemDTO toAdd, InvoiceItemDTO item) throws ServiceError {
        try {
            String stmt = "INSERT INTO " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM_SERIAL_NUMBER.TABLE_NAME;
            stmt += " (" + TABLE_COLUMNS.INVOICE.INVOICE_ITEM_SERIAL_NUMBER.ITEM;
            stmt += ", " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM_SERIAL_NUMBER.INVOICE_ITEM;
            stmt += ") VALUES ( " + toAdd.getKey() + ", " + item.getKey() + ");";

            this.getService().executeStatement(stmt);
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public InvoiceDTO[] getInvoicesWithItem(ItemDTO key) throws ServiceError {
       
    InvoiceItemDTO [] items = this.getInvoiceItemsWithItem(key);
    
    List<Integer> sorted = DataObjectGenerator.createList();
    
    for(int index = 0; index < items.length; index++){
       
        Integer invoice = items[index].getInvoice();
        
        if(sorted.contains(invoice) == false){
            sorted.add(invoice);
        }
       
    }
    
    InvoiceDTO [] ret = new InvoiceDTO[sorted.size()];
    for(int index = 0; index < sorted.size(); index++){
 
InvoiceDTO toAdd = this.getInvoice(new BaseDTO(sorted.get(index)));
        ret[index] = toAdd;
    }
    return ret;
    }

    @Override
    public InvoiceDTO[] getInvoicesWithSerialNumber(SerializedItemDTO key) throws ServiceError {
         InvoiceItemDTO [] items = this.getInvoiceItemsWithSerialNumber(key);
    
    List<Integer> sorted = DataObjectGenerator.createList();
    
    for(int index = 0; index < items.length; index++){
       
        Integer invoice = items[index].getInvoice();
        
        if(sorted.contains(invoice) == false){
            sorted.add(invoice);
        }
       
    }
    
    InvoiceDTO [] ret = new InvoiceDTO[sorted.size()];
    for(int index = 0; index < sorted.size(); index++){
 
InvoiceDTO toAdd = this.getInvoice(new BaseDTO(sorted.get(index)));
        ret[index] = toAdd;
    }
    return ret;
    }
  

    @Override
    public InvoiceItemDTO[] getInvoiceItemsWithItem(ItemDTO key) throws ServiceError {
        try {
            String query = "SELECT itm.* FROM " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.TABLE_NAME;
            
            
            query = query + " AS itm INNER JOIN " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.TABLE_NAME;
            
            query += " AS trn ON itm."+ TABLE_COLUMNS.INVOICE.INVOICE_ITEM.TRANSACTION;
            query += " = trn." + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ID;
            query += " WHERE trn." + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ITEM;
            query += " = " + key.getKey();
            
            ResultSet rs = this.getService().executeQuery(query);
            
            List<InvoiceItemDTO> items = DataObjectGenerator.createList();
            
            while(rs.next()){
                items.add(createInvoiceItemFromResultSet(rs));
                            }
            InvoiceItemDTO[] ret = new InvoiceItemDTO[items.size()];
            for(int index = 0; index < ret.length; index++){
                ret[index] = items.get(index);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex); }

    }

    @Override
    public InvoiceItemDTO[] getInvoiceItemsWithSerialNumber(SerializedItemDTO key) throws ServiceError {
     try {
            String query = "SELECT itm.* FROM " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM.TABLE_NAME;
            
            
            query = query + " AS itm INNER JOIN " + TABLE_COLUMNS.INVOICE.INVOICE_ITEM_SERIAL_NUMBER.TABLE_NAME;
            
            query += " AS trn ON itm."+ TABLE_COLUMNS.INVOICE.INVOICE_ITEM.ID;
            query += " = trn." +TABLE_COLUMNS.INVOICE.INVOICE_ITEM_SERIAL_NUMBER.INVOICE_ITEM;
            query += " WHERE trn." + TABLE_COLUMNS.INVOICE.INVOICE_ITEM_SERIAL_NUMBER.ITEM;
            query += " = " + key.getKey();
            
            ResultSet rs = this.getService().executeQuery(query);
            
            List<InvoiceItemDTO> items = DataObjectGenerator.createList();
            
            while(rs.next()){
                items.add(createInvoiceItemFromResultSet(rs));
                            }
            InvoiceItemDTO[] ret = new InvoiceItemDTO[items.size()];
            for(int index = 0; index < ret.length; index++){
                ret[index] = items.get(index);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex); }   }

}
