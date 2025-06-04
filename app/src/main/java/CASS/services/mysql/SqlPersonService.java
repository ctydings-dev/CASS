/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.data.BaseDTO;
import CASS.data.CASSConstants;
import CASS.data.person.EmployeeDTO;
import CASS.data.person.EmployeeSearchParameters;
import CASS.data.person.PersonDTO;
import CASS.data.person.PersonSearchParameters;
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
public class SqlPersonService implements PersonService {

    
    SqlService svc;
    
    
    
    
    public SqlPersonService(SqlService svc){
        this.svc = svc;
    }
    
    
    private SqlService getService(){
        return svc;
    }
    
    
    private EmployeeDTO createEmployeeFromResultSet(ResultSet rs) throws SQLException{
      
        int key = rs.getInt("employee_id");
         String created = rs.getString(CASSConstants.CREATED_DATE);
      
        
        int  person = rs.getInt("person_id");
        
        String hire = rs.getString(EmployeeSearchParameters.HIRE_DATE);
        
        
        String code  = rs.getString(EmployeeSearchParameters.EMPLOYEE_CODE);
        
        
        int type = rs.getInt(EmployeeSearchParameters.EMPLOYEE_TYPE_ID);
        
        boolean isActive = rs.getBoolean(EmployeeSearchParameters.IS_ACTIVE);
        
        return new EmployeeDTO(person,hire,type,isActive,code,key,created);
       
        
    }
    
    
    
    
    
    private PersonDTO createPersonFromResultSet(ResultSet rs) throws SQLException{
        
          
        int  key = rs.getInt("person_id");
        String fName = rs.getString(PersonSearchParameters.FIRST_NAME);
        
        String mName = rs.getString(PersonSearchParameters.MIDDLE_NAME);
        
        String lName = rs.getString(PersonSearchParameters.LAST_NAME);
        
        String nickname = rs.getString(PersonSearchParameters.NICKNAME);
        String alias = rs.getString(PersonSearchParameters.ALIAS);
        
        boolean is_active = rs.getBoolean(PersonSearchParameters.IS_ACTIVE);
        
        boolean is_current = rs.getBoolean(PersonSearchParameters.IS_CURRENT);
        
        int address = rs.getInt("address_id");
        
        char gender = (rs.getString(PersonSearchParameters.GENDER) + "").charAt(0);
  
        String created = rs.getString(CASSConstants.CREATED_DATE);
        String updated = rs.getString(CASSConstants.UPDATED_DATE);
        
        String birthday = rs.getString(PersonSearchParameters.BIRTHDAY);
      
        
        PersonDTO ret = new PersonDTO(fName, mName, lName, nickname,alias, gender, is_current, is_active, address,created,updated,birthday, key); ;
        
        return ret;
        
    }
    
    
    
    
    
    
    @Override
    public PersonDTO getPerson(BaseDTO key) throws ServiceError {
        try {
            String query = "SELECT * FROM persons WHERE person_id="+ key.getKey() + ";";
            
     ResultSet rs =       this.getService().executeQuery(query);
     rs.next();
  return this.createPersonFromResultSet(rs);
     
        } catch (SQLException ex) {
       throw new ServiceError(ex);   }
        
        
        
     }

    @Override
    public List<PersonDTO> getPersons() throws ServiceError {
        try {
            String query = "SELECT * FROM persons;";
            
     ResultSet rs =       this.getService().executeQuery(query);
     
     List<PersonDTO> ret = DataObjectGenerator.createList();
     
     while(rs.next()){
         
         ret.add(this.createPersonFromResultSet(rs));
     }
     
     return ret;
        } catch (SQLException ex) {
       throw new ServiceError(ex);   }
          }

    @Override
    public int addPerson(PersonDTO toAdd) throws ServiceError {
   
        try {
            String stmt = this.createPersonInsert(toAdd);
            this.getService().executeStatement(stmt);
            
            
            PersonSearchParameters params = new PersonSearchParameters(toAdd);
            
            
          return this.searchPersons(params).get(0).getKey();
             } catch (SQLException ex) {
           throw new ServiceError(ex);
             }
    }

    private String createPersonInsert(PersonDTO entry){
        String ret = "INSERT INTO persons(" + PersonSearchParameters.FIRST_NAME + "," + PersonSearchParameters.MIDDLE_NAME + "," + PersonSearchParameters.LAST_NAME + "," + PersonSearchParameters.NICKNAME + "," + PersonSearchParameters.ALIAS + "," +PersonSearchParameters.GENDER + "," + PersonSearchParameters.BIRTHDAY + "," + PersonSearchParameters.IS_ACTIVE + "," + PersonSearchParameters.IS_CURRENT +", address_id)  VALUES (";
        
        ret = ret +"'" +entry.getFirstName().trim().toUpperCase() + "','" + entry.getMiddleName().trim().toUpperCase() + "','"+ entry.getLastName().trim().toUpperCase() +"','" + entry.getNickname().trim().toUpperCase() + "','" + entry.getAlias().trim().toUpperCase() + "','" + entry.getGender() + "','" + entry.getBirthday() + "',true,true," + entry.getAddressID() + ");";
        
        
        return ret;
    }
    
    
       private String createEmployeeInsert(EmployeeDTO entry){
        String ret = "INSERT INTO employees(" + EmployeeSearchParameters.EMPLOYEE_TYPE_ID+ "," +EmployeeSearchParameters.EMPLOYEE_CODE + "," + EmployeeSearchParameters.HIRE_DATE + "," + EmployeeSearchParameters.IS_ACTIVE + ",person_id) VALUES (";
        ret =ret  + entry.getEmployeeTypeId() + ",'" + entry.getEmployeeCode().trim().toUpperCase()  +"','" + entry.getHireDate()  +"'," + entry.isIsActive() + "," + entry.getPersonID() + ");";
        
        return ret;
    }
    
    
    
    
    
    
    @Override
    public List<PersonDTO> searchPersons(PersonSearchParameters params) throws ServiceError {
       
        try {
            String query = PersonSearchBuilder.createQueryForPerson(params);
            
            ResultSet rs =       this.getService().executeQuery(query);
            
            List<PersonDTO> ret = DataObjectGenerator.createList();
            
            while(rs.next()){
                
                ret.add(this.createPersonFromResultSet(rs));
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new ServiceError(ex);
        }
    
    }

    @Override
    public EmployeeDTO getEmployee(BaseDTO key) throws ServiceError {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<EmployeeDTO> getEmployees() throws ServiceError {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int addEmployee(EmployeeDTO toAdd) throws ServiceError {
        try {
            String stmt = this.createEmployeeInsert(toAdd);
            this.getService().executeStatement(stmt);
            String query = "SELECT employee_id  FROM employees WHERE person_id =  " + toAdd.getPersonID() + ";";
            ResultSet rs = this.getService().executeQuery(query);
            rs.next();
            return rs.getInt("employee_id");
            
             } catch (SQLException ex) {
           throw new ServiceError(ex);
             }
    
    
    }

    @Override
    public List<EmployeeDTO> searchEmployees(EmployeeSearchParameters params) throws ServiceError {
         try {
            String query = PersonSearchBuilder.createQueryForEmployee(params);
            
            ResultSet rs =       this.getService().executeQuery(query);
            
            List<EmployeeDTO> ret = DataObjectGenerator.createList();
            
            while(rs.next()){
                
                ret.add(this.createEmployeeFromResultSet(rs));
            }
            
            return ret;
        } catch (SQLException ex) {
           throw new ServiceError(ex);
        } }
    
    private <BaseDTO> BaseDTO checkForSingular(List<BaseDTO> toCheck) throws ServiceError {
        if (toCheck.size() > 1) {
            throw new ServiceError("MULTIPLE RESULTS FOUND");
        }

        if (toCheck.size() < 1) {
            return null;
        }

        return toCheck.get(0);
    }
    
    
    
    
    
    
    
}
