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
import CASS.data.item.ItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.CompanyDTO;
import CASS.search.CompanySearchParameters;
import CASS.data.person.EmployeeDTO;
import CASS.search.EmployeeSearchParameters;
import CASS.data.person.PersonDTO;
import CASS.search.CountrySearchParameters;
import CASS.search.ItemSearchParameters;
import CASS.search.PersonSearchParameters;
import CASS.search.TypeAssignmentTable;
import CASS.services.ItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import CASS.util.DataObjectGenerator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctydi
 */
public class SqlItemService implements ItemService {

    SqlService svc;

    public SqlItemService(SqlService svc) {
        this.svc = svc;
    }

    private SqlService getService() {
        return svc;
    }
    
    
    private ItemDTO createItemFromResultSet(ResultSet rs) throws SQLException{
        
        int key = rs.getInt(TABLE_COLUMNS.INVENTORY.ITEM.ID);
        int company = rs.getInt(TABLE_COLUMNS.INVENTORY.ITEM.COMPANY);
        int type = rs.getInt(TABLE_COLUMNS.INVENTORY.ITEM.TYPE);
 
        String name = rs.getString(TABLE_COLUMNS.INVENTORY.ITEM.NAME);
        String alias = rs.getString(TABLE_COLUMNS.INVENTORY.ITEM.ALIAS);
        boolean isForSale=  rs.getBoolean(TABLE_COLUMNS.INVENTORY.ITEM.IS_FOR_SALE);
            boolean isSerialized=  rs.getBoolean(TABLE_COLUMNS.INVENTORY.ITEM.IS_SERIALIZED);
        
        return new ItemDTO(key,name,alias,type,company,isForSale, isSerialized);
        
            }
    
    

    @Override
    public List<ItemDTO> getAllItems() throws ServiceError {
   
        try {
            List<ItemDTO> ret = DataObjectGenerator.createList();
            ResultSet rs = this.getService().getAllForTable(TABLE_COLUMNS.INVENTORY.ITEM.TABLE_NAME );
            while(rs.next()){
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
        throw new ServiceError(ex);}
    
    
    }

    @Override
    public List<ItemDTO> searchItems(ItemSearchParameters params) throws ServiceError {
    String query = params.getSearchQuery();
    try{
     ResultSet rs = this.getService().executeQuery(query);
      List<ItemDTO> ret = DataObjectGenerator.createList();
    while(rs.next()){
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
            
                  
      int ret =   this.getService().insertAndGet(params);
        
    return ret;  
      
      
      
        } catch (SQLException ex) {
       throw new ServiceError(ex);   }
        
        
    
    }
    
    
    private boolean inventoryContains(Integer item, Integer facility) throws SQLException{
        
        String query = "SELECT COUNT(*) FROM " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.TABLE_NAME;
                
                query = query + " WHERE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.ITEM;
        query = query + " = " + item + " AND " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.FACILITY;
        
        query = query + " = " + facility + ";";
        
        ResultSet rs = this.getService().executeQuery(query);
        rs.next();
        
        return rs.getInt(1) > 0;
       
    }
    
    
    
    
    
    private void updateInventory(TransactionDTO entry) throws SQLException{
        
        if(entry.getIsValid() == false){
            return;
        }
        
        if(this.inventoryContains(entry.getItem(), entry.getFacility()) == false){
            
            String sub = "INSERT INTO " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.TABLE_NAME;
            
            sub = sub + " ("+ TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.ITEM;
            sub = sub + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.FACILITY;
            sub = sub + ", " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.STOCK;
            sub = sub + ") VALUES (" + entry.getItem() + ", " + entry.getFacility();
            sub = sub + ", 0);";
            
            this.getService().executeStatement(sub);
            
        }
        
        
        
        
        
        
        String query ="SELECT "+  TABLE_COLUMNS.TYPE.TRANSACTION.MULTIPLYER;
        
        query = query + " FROM "+ TABLE_COLUMNS.TYPE.TRANSACTION.TABLE_NAME + " WHERE ";
        query = query + TABLE_COLUMNS.TYPE.TRANSACTION.ID + " = " + entry.getType() +";";
        
        ResultSet rs = this.getService().executeQuery(query);
        rs.next();
        int multi = rs.getInt(TABLE_COLUMNS.TYPE.TRANSACTION.MULTIPLYER);
        int qty = entry.getQuantity() * multi;
        
        String stmt = "UPDATE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.TABLE_NAME;
        
        stmt = stmt + " SET " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.STOCK;
        stmt = stmt + " = " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.STOCK  + " + ";
        stmt = stmt + qty + " WHERE " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.ITEM;
        stmt = stmt + " = " + entry.getItem()+ " AND " + TABLE_COLUMNS.INVENTORY.INVENTORY_TABLE.FACILITY;
        stmt = stmt + " = "+ entry.getFacility() + ";";
        
        
        this.getService().executeStatement(stmt);
        
        
        
    }
    

    @Override
    public int addInventoryTransaction(TransactionDTO entry) throws ServiceError {
        try {
            String query = "INSERT INTO " + TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.TABLE_NAME;
            
            
            query = query + " ("+TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ITEM;
            
            query = query + ", "+TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.EMPLOYEE;
            
            query = query + ", "+TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.AMMOUNT;
            
            query = query + ", "+TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.TYPE;
            
            
            query = query + ", "+TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.IS_VALID;
             query = query + ", "+TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.FACILITY;
            
            
            query = query + ") VALUES (" + entry.getItem();
            query = query + ", " + entry.getEmployee();
            query = query + ", " + entry.getQuantity();
            query = query + ", " + entry.getType();
            query = query + ", " + entry.getIsValid() ;
            query = query + ", " + entry.getFacility() + ");";
            
            
            
            this.getService().executeStatement(query);
            
            this.updateInventory(entry);
            
            
            
            
            
            return 0;
        } catch (SQLException ex) {
        throw new ServiceError(ex);}
    
    
    
    }

   
}
