/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.search.PersonSearchBuilder;
import CASS.data.BaseDTO;
import CASS.data.BaseSearchParameter;
import CASS.data.CASSConstants;
import CASS.data.TypeAssignmentDTO;
import CASS.data.TypeDTO;
import CASS.data.item.InventoryItemDTO;
import CASS.data.item.ItemDTO;
import CASS.data.item.PriceDTO;
import CASS.data.item.SerializedItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.AccountDTO;
import CASS.data.person.CompanyDTO;
import CASS.search.CompanySearchParameters;
import CASS.data.person.EmployeeDTO;
import CASS.search.EmployeeSearchParameters;
import CASS.data.person.PersonDTO;
import CASS.search.CountrySearchParameters;
import CASS.search.ItemSearchParameters;
import CASS.search.PersonSearchParameters;
import CASS.search.TypeAssignmentTable;
import CASS.services.ExtendedItemService;
import CASS.services.ItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import CASS.util.DataObjectGenerator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctydi
 */
public class SqlItemService implements ItemService, ExtendedItemService {

    SqlService svc;

    public SqlItemService(SqlService svc) {
        this.svc = svc;

    }

    private SqlService getService() {
        return svc;
    }

    private ItemDTO createItemFromResultSet(ResultSet rs) throws SQLException {

        int key = rs.getInt(TABLE_COLUMNS.INVENTORY.ITEM.ID);
        int company = rs.getInt(TABLE_COLUMNS.INVENTORY.ITEM.COMPANY);
        int type = rs.getInt(TABLE_COLUMNS.INVENTORY.ITEM.TYPE);

        String name = rs.getString(TABLE_COLUMNS.INVENTORY.ITEM.NAME);
        String alias = rs.getString(TABLE_COLUMNS.INVENTORY.ITEM.ALIAS);
        boolean isForSale = rs.getBoolean(TABLE_COLUMNS.INVENTORY.ITEM.IS_FOR_SALE);
        boolean isSerialized = rs.getBoolean(TABLE_COLUMNS.INVENTORY.ITEM.IS_SERIALIZED);

        return new ItemDTO(key, name, alias, type, company, isForSale, isSerialized);

    }
    
    
      private InventoryItemDTO createInvItemFromResultSet(ResultSet rs) throws SQLException {
String item = "itm.";
        int key = rs.getInt(item + TABLE_COLUMNS.INVENTORY.ITEM.ID);
        int company = rs.getInt(item + TABLE_COLUMNS.INVENTORY.ITEM.COMPANY);
        int type = rs.getInt(item + TABLE_COLUMNS.INVENTORY.ITEM.TYPE);

        String name = rs.getString(item + TABLE_COLUMNS.INVENTORY.ITEM.NAME);
        String alias = rs.getString(item + TABLE_COLUMNS.INVENTORY.ITEM.ALIAS);
        boolean isForSale = rs.getBoolean(item + TABLE_COLUMNS.INVENTORY.ITEM.IS_FOR_SALE);
        boolean isSerialized = rs.getBoolean(item + TABLE_COLUMNS.INVENTORY.ITEM.IS_SERIALIZED);

        ItemDTO base =  new ItemDTO(key, name, alias, type, company, isForSale, isSerialized);
Integer qty = rs.getInt("inv."+ TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.STOCK);
        
        return new InventoryItemDTO(base,qty);
    }
    
    

    private TransactionDTO createTransactionFromResultSet(ResultSet rs) throws SQLException {

        int key = rs.getInt(TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ID);
        int item = rs.getInt(TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ITEM);
        int emp = rs.getInt(TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.EMPLOYEE);
        int qty = rs.getInt(TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.AMMOUNT);

        int type = rs.getInt(TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.TYPE);
        int facility = rs.getInt(TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.FACILITY);
        String date = rs.getString(TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.DATE);
        boolean isValid = rs.getBoolean(TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.IS_VALID);

        return new TransactionDTO(key, item, emp, qty, type, isValid, date, facility);

    }

    private SerializedItemDTO createSerializedItemFromResultSet(ResultSet rs) throws SQLException {
        int key = rs.getInt(TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ID);
        int item = rs.getInt(TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ITEM);
        String number = rs.getString(TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.SERIAL_NUMBER);
        Boolean isForSale = rs.getBoolean(TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.IS_FOR_SALE);
        return new SerializedItemDTO(number, item, isForSale, key);
    }

    private PriceDTO createPriceFromResultSet(ResultSet rs) throws SQLException {

        int key = rs.getInt(TABLE_COLUMNS.INVENTORY.PRICE.ID);

        Integer item = rs.getInt(TABLE_COLUMNS.INVENTORY.PRICE.ITEM);
        Integer employee = rs.getInt(TABLE_COLUMNS.INVENTORY.PRICE.EMPLOYEE);

        Integer currency = rs.getInt(TABLE_COLUMNS.INVENTORY.PRICE.CURRENCY);
        boolean sale = rs.getBoolean(TABLE_COLUMNS.INVENTORY.PRICE.IS_SALE);
        boolean isSpecial = rs.getBoolean(TABLE_COLUMNS.INVENTORY.PRICE.IS_SPECIAL);
        Double purchase = rs.getDouble(TABLE_COLUMNS.INVENTORY.PRICE.BUY);
        Double salePrice = rs.getDouble(TABLE_COLUMNS.INVENTORY.PRICE.SELL);

        String started = rs.getString(TABLE_COLUMNS.INVENTORY.PRICE.STARTED);
        String ended = rs.getString(TABLE_COLUMNS.INVENTORY.PRICE.ENDED);
        String code = rs.getString(TABLE_COLUMNS.INVENTORY.PRICE.CODE);
        String created = rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.CREATED_DATE);

        return new PriceDTO(key, item, purchase, salePrice, started, ended, sale, isSpecial, currency, employee, code);

    }

    @Override
    public List<ItemDTO> getAllItems() throws ServiceError {

        try {
            List<ItemDTO> ret = DataObjectGenerator.createList();
            
            String query =  "SELECT * FROM " + TABLE_COLUMNS.INVENTORY.ITEM.NAME;
            
            query = query + " WHERE " + TABLE_COLUMNS.INVENTORY.ITEM.VALID;
            query = query + " = TRUE;";
            
            
            ResultSet rs = this.getService().executeQuery(query);
            while (rs.next()) {
                ret.add(this.createItemFromResultSet(rs));
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }
    }

    @Override
    public ItemDTO getItem(BaseDTO key) throws ServiceError {

        try {
            ResultSet rs = this.getService().getForId(TABLE_COLUMNS.INVENTORY.ITEM.TABLE_NAME, TABLE_COLUMNS.INVENTORY.ITEM.ID, key.getKey());
            rs.next();
            return this.createItemFromResultSet(rs);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public List<ItemDTO> searchItems(ItemSearchParameters params) throws ServiceError {
        String query = params.getSearchQuery();
        try {
            ResultSet rs = this.getService().executeQuery(query);
            List<ItemDTO> ret = DataObjectGenerator.createList();
            while (rs.next()) {
                ret.add(this.createItemFromResultSet(rs));
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public int addItem(ItemDTO toAdd) throws ServiceError {
        try {
            ItemSearchParameters params = new ItemSearchParameters(toAdd);

            int ret = this.getService().insertAndGet(params);

            return ret;

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public TransactionDTO addInventoryTransaction(TransactionDTO entry) throws ServiceError {
        try {
            String stmt = this.getTransactionInsert(entry);
            this.getService().executeStatement(stmt);
            return this.updateInventory(entry, false);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    private TransactionDTO updateInventory(TransactionDTO entry, boolean reverse) throws SQLException, ServiceError {
        if (this.inventoryContains(entry.getItem(), entry.getFacility()) == false) {
            this.addInventory(entry);
        }

        int multi = this.getMultiplyer(entry.getType());
        int qty = entry.getQuantity() * multi;
        if (reverse) {
            qty = qty * -1;
        }

        this.updateInventoryItem(entry, qty);
        return this.getLatestTransaction();
    }

    @Override
    public int addInventoryTransactionNote(TransactionDTO entry, TypeDTO type, String note) throws ServiceError {
        try {
            String stmt = "INSERT INTO " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION_NOTE.TABLE_NAME;
            stmt = stmt + " (" + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION_NOTE.TRANSACTION;
            stmt = stmt + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION_NOTE.TYPE;
            stmt = stmt + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION_NOTE.NOTE;
            stmt = stmt + ") VALUES (" + entry.getKey() + ", ";
            stmt = stmt + type.getTypeID() + ", '";

            stmt = stmt + note + "');";

            this.getService().executeStatement(stmt);
            return 0;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public void reverseTransaction(TransactionDTO toReverse, EmployeeDTO reverser) throws ServiceError {

        try {
            this.reverseTransactionTable(toReverse, reverser);
            this.updateInventory(toReverse, true);
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    public List<PriceDTO> getAllPrices(ItemDTO item) throws ServiceError {

        try {
            String query = "SELECT * FROM " + TABLE_COLUMNS.INVENTORY.PRICE.TABLE_NAME;

            query = query + " WHERE " + TABLE_COLUMNS.INVENTORY.PRICE.ITEM + " = ";

            query = query + item.getKey() + ";";

            ResultSet rs = this.getService().executeQuery(query);
            List<PriceDTO> ret = DataObjectGenerator.createList();

            while (rs.next()) {
                ret.add(this.createPriceFromResultSet(rs));

            }

            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    public List<PriceDTO> getActivePrices(ItemDTO item) throws ServiceError {
        List<PriceDTO> prices = this.getAllPrices(item);

        List<PriceDTO> ret = DataObjectGenerator.createList();

        Date curr = new Date(System.currentTimeMillis());

        prices.forEach(price -> {

            Date end = new Date(price.getEndDate());
            Date start = new Date(price.getStartDate());

            if (curr.after(start) && curr.before(end)) {
                if (price.isSpecial() == false) {
                    ret.add(price);
                }

            }

        });

        return ret;

    }

    @Override
    public PriceDTO getStandardPrice(ItemDTO item) throws ServiceError {

        List<PriceDTO> options = this.getActivePrices(item);

        for (PriceDTO price : options) {
            if (price.isSpecial() == false && price.isSale() == false) {
                return price;
            }
        }

        throw new ServiceError("NO PRICES FOUND FOR ITEM " + item.getKey());

    }

    @Override
    public PriceDTO getCurrentPrice(ItemDTO item) throws ServiceError {
        List<PriceDTO> options = this.getActivePrices(item);
        PriceDTO ret = null;

        for (PriceDTO price : options) {
            if (price.isSpecial() == false) {

                if (ret == null) {
                    ret = price;
                }

                if (price.getPurchasePrice() < ret.getPurchasePrice()) {
                    ret = price;
                }

            }
        }

        if (ret != null) {
            return ret;
        }

        throw new ServiceError("NO PRICES FOUND FOR ITEM " + item.getKey());

    }

    @Override
    public PriceDTO getPrice(BaseDTO key) throws ServiceError {

        try {
            String query = "SELECT *";

            query = query + " FROM " + TABLE_COLUMNS.INVENTORY.PRICE.TABLE_NAME;
            query = query + " WHERE " + TABLE_COLUMNS.INVENTORY.PRICE.ID;
            query = query + " = " + key.getKey() + ";";

            ResultSet rs = this.getService().executeQuery(query);

            rs.next();
            return this.createPriceFromResultSet(rs);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public PriceDTO addItemPrice(PriceDTO toAdd) throws ServiceError {
        try {
            try {
                if (toAdd.isSpecial() == false && toAdd.isSale() == false) {

                    ItemDTO item = new ItemDTO(toAdd.getItem());

                    PriceDTO std = this.getStandardPrice(item);

                    String end = std.getEndDate();

                    Date begin = new Date(toAdd.getStartDate());
                    boolean replace = false;

                    if (end == null) {
                        replace = true;
                    } else {
                        if (begin.before(new Date(end))) {
                            replace = true;
                        }

                    }

                    if (replace = true) {

                        String stmt = "UPDATE " + TABLE_COLUMNS.INVENTORY.PRICE.TABLE_NAME;

                        stmt = stmt + " SET " + TABLE_COLUMNS.INVENTORY.PRICE.ENDED;

                        stmt = stmt + " = " + toAdd.getStartDate() + " WHERE ";
                        stmt = stmt + TABLE_COLUMNS.INVENTORY.PRICE.ID + " = ";
                        stmt = stmt + std.getKey() + ";";

                        this.getService().executeStatement(stmt);
                    }
                }
            } catch (ServiceError err) {

            }

            String code = toAdd.getCode();

            if (code == null) {
                code = "ITEM_PRICE_" + toAdd.getItem() + "_STARTING_" + toAdd.getStartDate();
            }

            String stmt = this.createPriceInsert(toAdd);
            this.getService().executeStatement(stmt);

            String query = "SELECT " + TABLE_COLUMNS.INVENTORY.PRICE.ID;

            query = query + " FROM " + TABLE_COLUMNS.INVENTORY.PRICE.TABLE_NAME;
            query = query + " ORDER BY " + TABLE_COLUMNS.INVENTORY.PRICE.ID;

            query = query + " DESC;";

            ResultSet rs = this.getService().executeQuery(query);
            rs.next();
            int id = rs.getInt(TABLE_COLUMNS.INVENTORY.PRICE.ID);
            return this.getPrice(new BaseDTO(id));

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    private String createPriceInsert(PriceDTO toAdd) {
        String stmt = "INSERT INTO " + TABLE_COLUMNS.INVENTORY.PRICE.TABLE_NAME;
        stmt = stmt + " (" + TABLE_COLUMNS.INVENTORY.PRICE.ITEM;
        stmt = stmt + " ," + TABLE_COLUMNS.INVENTORY.PRICE.EMPLOYEE;
        if (toAdd.getStartDate() != null) {
            stmt = stmt + " ," + TABLE_COLUMNS.INVENTORY.PRICE.STARTED;
        }

        stmt = stmt + " ," + TABLE_COLUMNS.INVENTORY.PRICE.ENDED;

        stmt = stmt + " ," + TABLE_COLUMNS.INVENTORY.PRICE.IS_SALE;
        stmt = stmt + " ," + TABLE_COLUMNS.INVENTORY.PRICE.IS_SPECIAL;
        stmt = stmt + " ," + TABLE_COLUMNS.INVENTORY.PRICE.CODE;
        stmt = stmt + " ," + TABLE_COLUMNS.INVENTORY.PRICE.SELL;
        stmt = stmt + " ," + TABLE_COLUMNS.INVENTORY.PRICE.BUY;
        stmt = stmt + " ," + TABLE_COLUMNS.INVENTORY.PRICE.CURRENCY;
        stmt = stmt + " ," + TABLE_COLUMNS.INVENTORY.PRICE.NAME;
        stmt = stmt + ") VALUES (";

        stmt = stmt + toAdd.getItem();
        stmt = stmt + ", " + toAdd.getEmployee();
        if (toAdd.getStartDate() != null) {
            stmt = stmt + ", '" + toAdd.getStartDate() + "'";
        }
        if (toAdd.getEndDate() != null) {
            stmt = stmt + ", '" + toAdd.getEndDate() + "'";
        } else {
            stmt = stmt + ", NULL";

        }

        stmt = stmt + ", " + toAdd.isSale();
        stmt = stmt + ", " + toAdd.isSpecial();
        stmt = stmt + ", '" + toAdd.getCode() + "'";
        stmt = stmt + ", " + toAdd.getSalePrice();
        stmt = stmt + ", " + toAdd.getPurchasePrice();
        stmt = stmt + ", " + toAdd.getCurrency();
        stmt = stmt + ", '" + toAdd.getCode() + "'";
        stmt = stmt + ");";

        return stmt;

    }

    @Override
    public void updateInventoryItem(TransactionDTO entry, int qty) throws SQLException {
        String stmt = "UPDATE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.TABLE_NAME;

        stmt = stmt + " SET " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.STOCK;
        stmt = stmt + " = " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.STOCK + " + ";
        stmt = stmt + qty + " WHERE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.ITEM;
        stmt = stmt + " = " + entry.getItem() + " AND " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.FACILITY;
        stmt = stmt + " = " + entry.getFacility() + ";";

        this.getService().executeStatement(stmt);

    }

    @Override
    public boolean inventoryContains(Integer item, Integer facility) throws SQLException, ServiceError {
        String query = "SELECT COUNT(*) FROM " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.TABLE_NAME;

        query = query + " WHERE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.ITEM;
        query = query + " = " + item + " AND " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.FACILITY;

        query = query + " = " + facility + ";";

        ResultSet rs = this.getService().executeQuery(query);
        rs.next();

        return rs.getInt(1) > 0;
    }

    @Override
    public void addInventory(TransactionDTO entry) throws SQLException, ServiceError {
        String sub = "INSERT INTO " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.TABLE_NAME;
        sub = sub + " (" + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.ITEM;
        sub = sub + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.FACILITY;
        sub = sub + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.STOCK;
        sub = sub + ") VALUES (" + entry.getItem() + ", " + entry.getFacility();
        sub = sub + ", 0);";
        this.getService().executeStatement(sub);
    }

    @Override
    public int getMultiplyer(int id) throws SQLException, ServiceError {
        String query = "SELECT " + TABLE_COLUMNS.TYPE.TRANSACTION.MULTIPLYER;

        query = query + " FROM " + TABLE_COLUMNS.TYPE.TRANSACTION.TABLE_NAME + " WHERE ";
        query = query + TABLE_COLUMNS.TYPE.TRANSACTION.ID + " = " + id + ";";
        ResultSet rs = this.getService().executeQuery(query);
        rs.next();
        return rs.getInt(TABLE_COLUMNS.TYPE.TRANSACTION.MULTIPLYER);
    }

    @Override
    public void reverseTransactionTable(TransactionDTO toReverse, EmployeeDTO reverser) throws SQLException, ServiceError {
        String stmt = "UPDATE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.TABLE_NAME;
        stmt = stmt + " SET " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.IS_VALID;
        stmt = stmt + " = FALSE WHERE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ID;
        stmt = stmt + " = " + toReverse.getKey();
        this.getService().executeStatement(stmt);
    }

    @Override
    public TransactionDTO getLatestTransaction() throws SQLException, ServiceError {
        String query = " SELECT " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ID;
        query = query + " FROM " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.TABLE_NAME;
        query = query + " ORDER BY " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ID;
        query = query + " DESC";
        ResultSet rs = this.getService().executeQuery(query);

        rs.next();

        int id = rs.getInt(TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ID);

        return this.getTransaction(new BaseDTO(id));

    }

    @Override
    public TransactionDTO getTransaction(BaseDTO key) throws ServiceError {

        try {
            String query = " SELECT *";
            query = query + " FROM " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.TABLE_NAME;

            query = query + " WHERE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ID;

            query = query + " = " + key.getKey() + ";";

            ResultSet rs = this.getService().executeQuery(query);
            rs.next();

            return this.createTransactionFromResultSet(rs);
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    private String getTransactionInsert(TransactionDTO entry) {
        String query = "INSERT INTO " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.TABLE_NAME;

        query = query + " (" + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ITEM;

        query = query + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.EMPLOYEE;

        query = query + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.AMMOUNT;

        query = query + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.TYPE;

        query = query + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.IS_VALID;
        query = query + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.FACILITY;

        query = query + ") VALUES (" + entry.getItem();
        query = query + ", " + entry.getEmployee();
        query = query + ", " + entry.getQuantity();
        query = query + ", " + entry.getType();
        query = query + ", " + entry.getIsValid();
        query = query + ", " + entry.getFacility() + ");";
        return query;
    }

    @Override
    public Integer getItemQty(ItemDTO item, BaseDTO facility) throws ServiceError {
        String stmt = "SELECT " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.STOCK;
        stmt = stmt + " FROM " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.TABLE_NAME;

        stmt = stmt + " WHERE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.ITEM;
        stmt = stmt + " = " + item.getKey() + " AND ";

        stmt = stmt + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.FACILITY + " = ";

        stmt = stmt + facility.getKey() + ";";
        try {
            ResultSet rs = this.getService().executeQuery(stmt);
            if (rs.next() == false) {
                return 0;
            }

            return rs.getInt(TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.STOCK);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public SerializedItemDTO addSerializedItem(ItemDTO item, String serialNumber, boolean forSale) throws ServiceError {

        try {
            String stmt = "INSERT INTO " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.TABLE_NAME;

            stmt = stmt + " (" + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ITEM;
            stmt = stmt + ", " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.IS_FOR_SALE;
            stmt = stmt + ", " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.SERIAL_NUMBER;
            stmt = stmt + ") VALUES(" + item.getKey() + "," + forSale + ",'" + serialNumber;
            stmt = stmt + "');";
            this.getService().executeStatement(stmt);
            stmt = "SELECT * FROM " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.TABLE_NAME;
            stmt = stmt + " WHERE " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ITEM;
            stmt = stmt + " = " + item.getKey() + " AND ";
            stmt = stmt + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.SERIAL_NUMBER;
            stmt = stmt + " = '" + serialNumber + "';";

            ResultSet rs = this.getService().executeQuery(stmt);
            rs.next();
            return this.createSerializedItemFromResultSet(rs);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public void addSerializedItemToInventory(SerializedItemDTO toAdd, TransactionDTO entry) throws ServiceError {

        try {
            this.removeSerializedItemFromInventory(toAdd);

            String stmt = "INSERT INTO " + TABLE_COLUMNS.INVENTORY.SERIALIZED_INVENTORY.TABLE_NAME;
            stmt = stmt + " (" + TABLE_COLUMNS.INVENTORY.SERIALIZED_INVENTORY.ITEM;
            stmt = stmt + ", " + TABLE_COLUMNS.INVENTORY.SERIALIZED_INVENTORY.TRANSACTION;
            stmt = stmt + ") VALUES (" + toAdd.getKey() + ", " + entry.getKey();

            stmt = stmt + ");";
            this.getService().executeStatement(stmt);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public void removeSerializedItemFromInventory(SerializedItemDTO toRemove) throws ServiceError {
        String stmt = "DELETE FROM " + TABLE_COLUMNS.INVENTORY.SERIALIZED_INVENTORY.TABLE_NAME;

        stmt = stmt + " WHERE " + TABLE_COLUMNS.INVENTORY.SERIALIZED_INVENTORY.ITEM + " = " + toRemove.getKey();
        stmt = stmt + ";";
        try {
            this.getService().executeStatement(stmt);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public SerializedItemDTO[] getAllSerializedItems() throws ServiceError {
        try {
            String query = "SELECT  * FROM " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.TABLE_NAME;
            
            query = query + " WHERE "+ TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.PRESENT;
            query = query + " = TRUE";
            
            query = query + ";";
            List<SerializedItemDTO> items = DataObjectGenerator.createList();
            ResultSet rs = this.getService().executeQuery(query);
            while (rs.next()) {
                items.add(this.createSerializedItemFromResultSet(rs));

            }
            SerializedItemDTO[] ret = new SerializedItemDTO[items.size()];
            for (int index = 0; index < ret.length; index++) {
                ret[index] = items.get(index);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }
    }

    @Override
    public SerializedItemDTO[] getSerializedItems(ItemDTO item, BaseDTO facility) throws ServiceError {
        try {
            String query = "SELECT  itm.* FROM " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.TABLE_NAME;
            query = query + " AS itm INNER JOIN " + TABLE_COLUMNS.INVENTORY.SERIALIZED_INVENTORY.TABLE_NAME;
            query = query + " AS inv ON itm." + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ID;
            query = query + " = inv." + TABLE_COLUMNS.INVENTORY.SERIALIZED_INVENTORY.ITEM;
            query = query + " INNER JOIN " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.TABLE_NAME;
            query = query + " AS trans ON inv." + TABLE_COLUMNS.INVENTORY.SERIALIZED_INVENTORY.TRANSACTION;
            query = query + " = trans." + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ID;
            query = query + " WHERE itm." + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ITEM;
            query = query + " = " + item.getKey() + " AND trans." + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.FACILITY;
            query = query + " = " + facility.getKey() + ";";
            List<SerializedItemDTO> items = DataObjectGenerator.createList();
            ResultSet rs = this.getService().executeQuery(query);
            while (rs.next()) {
                items.add(this.createSerializedItemFromResultSet(rs));

            }
            SerializedItemDTO[] ret = new SerializedItemDTO[items.size()];
            for (int index = 0; index < ret.length; index++) {
                ret[index] = items.get(index);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }
    }
    
    @Override
    public void removeSerializedItem(SerializedItemDTO toRemove) throws ServiceError{
        
        try {
            String stmt = "UPDATE " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.TABLE_NAME;
            stmt = stmt + " SET " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.PRESENT;
            stmt = stmt + " = FALSE WHERE " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ID;
            stmt = stmt + " = " + toRemove.getKey() + ";";
            this.getService().executeStatement(stmt);
        } catch (SQLException ex) {
          throw new ServiceError(ex);     }
        
        
        
    }
    @Override
    public void addSerializedItemNote(SerializedItemDTO item, EmployeeDTO employee, TypeDTO type, String note) throws ServiceError{
        try {
            String stmt = "INSERT INTO " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_NOTE.TABLE_NAME;
            stmt += " (" + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_NOTE.ITEM;
            stmt += ", " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_NOTE.EMPLOYEE;
            stmt += ", " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_NOTE.TYPE;
            stmt += ", " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_NOTE.NOTE;
            stmt +=") VALUES (" + item.getKey() + ", " + employee.getKey() + ", ";
            stmt += type.getKey() + ", '" + note + "');";
            
            this.getService().executeStatement(stmt);
        } catch (SQLException ex) {
       throw new ServiceError(ex);  }
  
    }
    
    @Override
    public SerializedItemDTO getSerializedItem(SerializedItemDTO key) throws ServiceError{
        
        try {
            String stmt = "SELECT * FROM " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.TABLE_NAME;
            stmt = stmt + " WHERE " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ID;
            stmt = stmt + " = " + key.getKey() + ";";
            ResultSet rs = this.getService().executeQuery(stmt);
            
            rs.next();
            return this.createSerializedItemFromResultSet(rs);
        } catch (SQLException ex) {
     throw new ServiceError(ex);    }
        
        
        
    }
    @Override
    public void clearSerializedItemOwnership(SerializedItemDTO item) throws ServiceError
    {
         try {
            String stmt = "UPDATE " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_OWNERSHIP.TABLE_NAME;
            
            stmt +=  " SET " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_OWNERSHIP.ACTIVE;
            stmt += " = FALSE WHERE " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_OWNERSHIP.ITEM;
            
            stmt += " = " + item.getKey() + ";";
            
            this.getService().executeStatement(stmt);
                   
        } catch (SQLException ex) {
         throw new ServiceError(ex); }
        
        
    }
    
    

    @Override
    public void setSerializedItemOwnership(SerializedItemDTO item, AccountDTO owner) throws ServiceError {
    
        try {
           this.clearSerializedItemOwnership(item);
            
            String stmt = "INSERT INTO " + TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_OWNERSHIP.TABLE_NAME;
            stmt += "("+   TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_OWNERSHIP.ITEM;
            stmt += ", " +   TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_OWNERSHIP.OWNER;
            stmt += ", " +   TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM_OWNERSHIP.ACTIVE;
            stmt += ") VALUES (" + item.getKey() + ", " + owner.getKey();
            stmt += ", TRUE);";
            
            this.getService().executeStatement(stmt);
            
            
            
            
        } catch (SQLException ex) {
         throw new ServiceError(ex); }
        
        
    
    
    
    }

   @Override
  public InventoryItemDTO [] getInventory(BaseDTO facility) throws ServiceError{
        try {
            String stmt  = "SELECT * FROM " + TABLE_COLUMNS.INVENTORY.ITEM.TABLE_NAME;
            stmt += " AS itm INNER JOIN " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.TABLE_NAME;
            stmt += " AS inv ON itm." + TABLE_COLUMNS.INVENTORY.ITEM.ID ;
            stmt += " = inv."+ TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.ITEM;
            stmt += " WHERE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.FACILITY;
            stmt += " = " + facility.getKey() + ";";
            
            
            ResultSet rs = this.getService().executeQuery(stmt);
            List<InventoryItemDTO> items = DataObjectGenerator.createList();
            while(rs.next()){
                items.add(this.createInvItemFromResultSet(rs));
                   }
            InventoryItemDTO [] ret = new InventoryItemDTO[items.size()];
            for(int index = 0; index < ret.length; index++){
                ret[index] = items.get(index);
            }
           return ret;
        } catch (SQLException ex) {
         throw new ServiceError(ex);  }
    }

    
    
    

}
