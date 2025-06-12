/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.data.TypeDTO;
import CASS.search.TypeAssignmentTable;
import CASS.services.ServiceError;
import CASS.services.TypeService;
import CASS.util.DataObjectGenerator;
import java.rmi.ServerError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctydi
 */
public class SqlTypeService implements TypeService {

    
       private SqlService service;

    public SqlTypeService(SqlService service) {
        this.service = service;
    }

    private SqlService getService() {
        return this.service;
    }
    
    
    
    private Map<String,Integer> getTypes(String tableName, String idName, String typeName) throws ServiceError{
        
           try {
               Map<String,Integer> ret = DataObjectGenerator.createMap();
               String query = "SELECT * FROM " + tableName + ";";
               
               ResultSet rs = this.getService().executeQuery(query);
               
               while(rs.next()){
                   
                   String value = rs.getString(typeName);
                   int id = rs.getInt(idName);
                   ret.put(value,id);
                   
               }
               return ret;
           } catch (SQLException ex) {
             throw new ServiceError(ex.getLocalizedMessage());
           }
    }
    
    
    
    private void addType(String tableName, String typeName, String type) throws ServiceError{
        
        
 String stmt = "INSERT INTO " + tableName +"(" + typeName + ") VALUES ('" + type + "');";       
        
           try {
               this.getService().executeStatement(stmt);
           } catch (SQLException ex) {
              throw new ServiceError(ex.getLocalizedMessage());
           }
}
    
    
    
    private int get(String table,String id, String typeName, String name) throws  ServiceError{
        
        Map<String,Integer> values = this.getTypes(table,id, typeName);
        
        if(values.containsKey(name)){
            return values.get(name);
        }
        
        addType(table,typeName, name);
        
        return get(table,id,typeName, name);
        
        
    }
    
    
    private TypeDTO [] getTypesList(String tableName, String idName, String typeName) throws ServiceError{
        
        Map<String,Integer> data = this.getTypes(tableName, idName, typeName);
         TypeDTO [] ret = new TypeDTO[data.size()];
        
        AtomicInteger counter = new AtomicInteger(0);
        
        data.keySet().forEach(name -> {
        
            int id = data.get(name);
            
            TypeDTO toAdd = new TypeDTO(name,id);
            ret[counter.get()] = toAdd;
            counter.addAndGet(1);
        });
        
        
        
      
        
        return ret;
    }
    
    
  
    
    
    @Override
    public TypeDTO [] getNoteTypes() throws ServiceError {
       return this.getTypesList(TABLE_COLUMNS.TYPE.NOTE.TABLE_NAME, TABLE_COLUMNS.TYPE.NOTE.ID,TABLE_COLUMNS.TYPE.NOTE.NAME);   }

    @Override
    public TypeDTO [] getEmployeeTypes() throws ServiceError {
      return this.getTypesList(TABLE_COLUMNS.TYPE.EMPLOYEE.TABLE_NAME, TABLE_COLUMNS.TYPE.EMPLOYEE.ID,TABLE_COLUMNS.TYPE.EMPLOYEE.NAME);   }

    @Override
    public TypeDTO [] getCertificationRequirementTypes() throws ServiceError {
   return this.getTypesList("certification_requirement_type", TABLE_COLUMNS.TYPE.NOTE.ID,TABLE_COLUMNS.TYPE.NOTE.NAME);}

    @Override
    public TypeDTO [] getItemTypes() throws ServiceError {
      return this.getTypesList(TABLE_COLUMNS.TYPE.ITEM.TABLE_NAME, TABLE_COLUMNS.TYPE.ITEM.ID, TABLE_COLUMNS.TYPE.ITEM.NAME);   }

    @Override
    public TypeDTO [] getInvoiceItemTypes() throws ServiceError {
      return this.getTypesList("invoice_item_type",null,null); }

    @Override
    public int getNoteType(String type) throws ServiceError {
       return this.get(TABLE_COLUMNS.TYPE.NOTE.TABLE_NAME, TABLE_COLUMNS.TYPE.NOTE.ID,TABLE_COLUMNS.TYPE.NOTE.NAME, type);  }

    @Override
    public int getEmployeeType(String type) throws ServiceError {
   return this.get(TABLE_COLUMNS.TYPE.EMPLOYEE.TABLE_NAME, TABLE_COLUMNS.TYPE.EMPLOYEE.ID,TABLE_COLUMNS.TYPE.EMPLOYEE.NAME, type);  }

    @Override
    public int getCertificationRequirementType(String type) throws ServiceError {
       return this.get("certification_requirement_type", null,null ,type);   }

    @Override
    public int getItemType(String type) throws ServiceError {
       return this.get(TABLE_COLUMNS.TYPE.ITEM.TABLE_NAME,TABLE_COLUMNS.TYPE.ITEM.ID,TABLE_COLUMNS.TYPE.ITEM.NAME, type);}

    @Override
    public int getInvoiceItemType(String type) throws ServiceError {
        return this.get("invoice_item_type",null,null, type);  }

    @Override
    public int addNoteType(String toAdd) throws ServiceError {
       return this.get(TABLE_COLUMNS.TYPE.NOTE.TABLE_NAME,TABLE_COLUMNS.TYPE.NOTE.ID,TABLE_COLUMNS.TYPE.NOTE.NAME, toAdd);  }

    @Override
    public int addEmployeeType(String toAdd) throws ServiceError {
     return this.get(TABLE_COLUMNS.TYPE.EMPLOYEE.TABLE_NAME,TABLE_COLUMNS.TYPE.EMPLOYEE.ID,TABLE_COLUMNS.TYPE.EMPLOYEE.NAME, toAdd); }

    @Override
    public int addCertificationRequirmentType(String toAdd) throws ServiceError {
       return this.get("certification_requirement_type",null,null, toAdd);   }

    @Override
    public int addItemType(String toAdd) throws ServiceError {
       return this.get(TABLE_COLUMNS.TYPE.ITEM.TABLE_NAME,TABLE_COLUMNS.TYPE.ITEM.ID,TABLE_COLUMNS.TYPE.ITEM.NAME,toAdd);  }

    @Override
    public int addInvoiceItemType(String toAdd) throws ServiceError {
       return this.get("invoice_item_type",null,null, toAdd);   }

    @Override
    public TypeDTO [] getCertificationTypes() throws ServiceError {
      return this.getTypesList("certification_type",null,null);   }

    @Override
    public int getCertificatioType(String type) throws ServiceError {
    return this.get("certification_type", null,null,type);  }

    @Override
    public int addCertificationType(String type) throws ServiceError {
     return this.get("certification_type", null,null,type);  }

    @Override
    public int getEmployeeRoleType(String type) throws ServiceError {
       return this.get(TABLE_COLUMNS.TYPE.EMPLOYEE_ROLE.TABLE_NAME,TABLE_COLUMNS.TYPE.EMPLOYEE_ROLE.ID,TABLE_COLUMNS.TYPE.EMPLOYEE_ROLE.NAME, type); }

    @Override
    public TypeDTO [] getTransactionTypes() throws ServiceError {
       return this.getTypesList(TABLE_COLUMNS.TYPE.TRANSACTION.TABLE_NAME, TABLE_COLUMNS.TYPE.TRANSACTION.ID,TABLE_COLUMNS.TYPE.TRANSACTION.NAME);   }


    @Override
    public int getTransactionType(String type) throws ServiceError {
       return this.get(TABLE_COLUMNS.TYPE.TRANSACTION.TABLE_NAME,TABLE_COLUMNS.TYPE.TRANSACTION.ID,TABLE_COLUMNS.TYPE.TRANSACTION.NAME, type);
}

    @Override
    public int addTransactionItemType(String toAdd) throws ServiceError {
   return this.get(TABLE_COLUMNS.TYPE.ITEM.TABLE_NAME,TABLE_COLUMNS.TYPE.TRANSACTION.ID,TABLE_COLUMNS.TYPE.TRANSACTION.NAME,toAdd);  }


    
    @Override
    public TypeDTO [] getCurrencyTypes() throws ServiceError {
        return this.getTypesList(TABLE_COLUMNS.TYPE.CURRENCY.TABLE_NAME, TABLE_COLUMNS.TYPE.CURRENCY.ID, TABLE_COLUMNS.TYPE.CURRENCY.NAME);   
    }


    @Override
    public int getCurrencyType(String type) throws ServiceError {
      return this.get(TABLE_COLUMNS.TYPE.CURRENCY.TABLE_NAME,TABLE_COLUMNS.TYPE.CURRENCY.ID,TABLE_COLUMNS.TYPE.CURRENCY.NAME, type);
  }

    @Override
    public int addCurrencyType(String toAdd) throws ServiceError {
       return this.get(TABLE_COLUMNS.TYPE.TRANSACTION.TABLE_NAME,TABLE_COLUMNS.TYPE.CURRENCY.ID,TABLE_COLUMNS.TYPE.CURRENCY.NAME, toAdd); }

    @Override
    public TypeDTO [] getInvoiceTypes() throws ServiceError {
        return this.getTypesList(TABLE_COLUMNS.TYPE.INVOICE.TABLE_NAME, TABLE_COLUMNS.TYPE.INVOICE.ID, TABLE_COLUMNS.TYPE.INVOICE.NAME);   
    }

    @Override
    public int getInvoiceType(String type) throws ServiceError {
       return this.get(TABLE_COLUMNS.TYPE.INVOICE.TABLE_NAME,TABLE_COLUMNS.TYPE.INVOICE.ID,TABLE_COLUMNS.TYPE.INVOICE.NAME, type);
    }

    @Override
    public int addInvoiceType(String toAdd) throws ServiceError {
    return this.get(TABLE_COLUMNS.TYPE.INVOICE.TABLE_NAME,TABLE_COLUMNS.TYPE.INVOICE.ID,TABLE_COLUMNS.TYPE.INVOICE.NAME,toAdd);  }

    @Override
    public TypeDTO [] getAccountTypes() throws ServiceError {
       return this.getTypesList(TABLE_COLUMNS.TYPE.ACCOUNT.TABLE_NAME, TABLE_COLUMNS.TYPE.ACCOUNT.ID, TABLE_COLUMNS.TYPE.ACCOUNT.NAME);   
   }

    @Override
    public int getAccountType(String type) throws ServiceError {
      return this.get(TABLE_COLUMNS.TYPE.ACCOUNT.TABLE_NAME,TABLE_COLUMNS.TYPE.ACCOUNT.ID,TABLE_COLUMNS.TYPE.ACCOUNT.NAME, type);
     }

    @Override
    public int addAccountType(String toAdd) throws ServiceError {
    return this.get(TABLE_COLUMNS.TYPE.ACCOUNT.TABLE_NAME,TABLE_COLUMNS.TYPE.ACCOUNT.ID,TABLE_COLUMNS.TYPE.ACCOUNT.NAME,toAdd);  }
 

 

    
}
