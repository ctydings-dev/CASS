/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.data.TypeDTO;
import CASS.services.ServiceError;
import CASS.services.TypeService;
import CASS.util.DataObjectGenerator;
import java.rmi.ServerError;
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
public class SqlTypeService implements TypeService {

    
       private SqlService service;

    public SqlTypeService(SqlService service) {
        this.service = service;
    }

    private SqlService getService() {
        return this.service;
    }
    
    
    
    private Map<String,Integer> getTypes(String tableName) throws ServiceError{
        
           try {
               Map<String,Integer> ret = DataObjectGenerator.createMap();
               String query = "SELECT * FROM " + tableName + "s;";
               
               ResultSet rs = this.getService().executeQuery(query);
               
               while(rs.next()){
                   
                   String value = rs.getString("type_name");
                   int id = rs.getInt(tableName + "_id");
                   ret.put(value,id);
                   
               }
               return ret;
           } catch (SQLException ex) {
             throw new ServiceError(ex.getLocalizedMessage());
           }
    }
    
    
    
    private void addType(String tableName, String type) throws ServiceError{
        
        
 String stmt = "INSERT INTO " + tableName +"s(type_name) VALUES ('" + type + "');";       
        
           try {
               this.getService().executeStatement(stmt);
           } catch (SQLException ex) {
              throw new ServiceError(ex.getLocalizedMessage());
           }
}
    
    
    
    private int get(String table, String name) throws  ServiceError{
        
        Map<String,Integer> values = this.getTypes(table);
        
        if(values.containsKey(name)){
            return values.get(name);
        }
        
        addType(table,name);
        
        return get(table,name);
        
        
    }
    
    
    private List<TypeDTO> getTypesList(String tableName) throws ServiceError{
        
        Map<String,Integer> data = this.getTypes(tableName);
        List<TypeDTO> ret = DataObjectGenerator.createList();
        
        data.keySet().forEach(name -> {
        
            int id = data.get(name);
            
            TypeDTO toAdd = new TypeDTO(name,id);
            ret.add(toAdd);
            
        });
        
        return ret;
    }
    
    
    @Override
    public List<TypeDTO> getNoteTypes() throws ServiceError {
       return this.getTypesList("note_type");   }

    @Override
    public List<TypeDTO> getEmployeeTypes() throws ServiceError {
      return this.getTypesList("employee_type");   }

    @Override
    public List<TypeDTO> getCertificationRequirementTypes() throws ServiceError {
   return this.getTypesList("certification_requirement_type");}

    @Override
    public List<TypeDTO> getItemTypes() throws ServiceError {
      return this.getTypesList("item_type");   }

    @Override
    public List<TypeDTO> getInvoiceItemTypes() throws ServiceError {
      return this.getTypesList("invoice_item_type"); }

    @Override
    public int getNoteType(String type) throws ServiceError {
       return this.get("note_type", type);  }

    @Override
    public int getEmployeeType(String type) throws ServiceError {
   return this.get("employee_type", type);  }

    @Override
    public int getCertificationRequirementType(String type) throws ServiceError {
       return this.get("certification_requirement_type", type);   }

    @Override
    public int getItemType(String type) throws ServiceError {
       return this.get("item_type", type);}

    @Override
    public int getInvoiceItemType(String type) throws ServiceError {
        return this.get("invoice_item_type", type);  }

    @Override
    public int addNoteType(String toAdd) throws ServiceError {
       return this.get("note_type", toAdd);  }

    @Override
    public int addEmployeeType(String toAdd) throws ServiceError {
     return this.get("employee_type", toAdd); }

    @Override
    public int addCertificationRequirmentType(String toAdd) throws ServiceError {
       return this.get("certification_requirement_type", toAdd);   }

    @Override
    public int addItemType(String toAdd) throws ServiceError {
       return this.get("item_type", toAdd);  }

    @Override
    public int addInvoiceItemType(String toAdd) throws ServiceError {
       return this.get("invoice_item_type", toAdd);   }

    @Override
    public List<TypeDTO> getCertificationTypes() throws ServiceError {
      return this.getTypesList("certification_type");   }

    @Override
    public int getCertificatioType(String type) throws ServiceError {
    return this.get("certification_type", type);  }

    @Override
    public int addCertificationType(String type) throws ServiceError {
     return this.get("certification_type", type);  }

    
}
