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
import CASS.data.person.AccountDTO;
import CASS.data.person.CompanyDTO;
import CASS.search.CompanySearchParameters;
import CASS.data.person.EmployeeDTO;
import CASS.search.EmployeeSearchParameters;
import CASS.data.person.PersonDTO;
import CASS.search.CountrySearchParameters;
import CASS.search.PersonSearchParameters;
import CASS.search.TypeAssignmentTable;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import CASS.util.DataObjectGenerator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctydi
 */
public class SqlPersonService implements PersonService {

    SqlService svc;

    public SqlPersonService(SqlService svc) {
        this.svc = svc;
    }

    private SqlService getService() {
        return svc;
    }

    private EmployeeDTO createEmployeeFromResultSet(ResultSet rs) throws SQLException {

        int key = rs.getInt(TABLE_COLUMNS.PEOPLE.EMPLOYEE.ID);
        String created = rs.getString(TABLE_COLUMNS.PEOPLE.EMPLOYEE.CREATED_DATE);

        int person = rs.getInt(TABLE_COLUMNS.PEOPLE.EMPLOYEE.PERSON);

        String hire = rs.getString(TABLE_COLUMNS.PEOPLE.EMPLOYEE.HIRE_DATE);

        String code = rs.getString(TABLE_COLUMNS.PEOPLE.EMPLOYEE.EMPLOYEE_CODE);

        int type = rs.getInt(TABLE_COLUMNS.PEOPLE.EMPLOYEE.EMPLOYEE_TYPE);

        boolean isActive = rs.getBoolean(TABLE_COLUMNS.PEOPLE.EMPLOYEE.IS_ACTIVE);

        return new EmployeeDTO(person, hire, type, isActive, code, key, created);

    }

    private CompanyDTO createCompanyFromResultSet(ResultSet rs) throws SQLException {

        int key = rs.getInt(TABLE_COLUMNS.PEOPLE.COMPANY.ID);
        int address = rs.getInt(TABLE_COLUMNS.PEOPLE.COMPANY.ADDRESS);

        boolean isActive = rs.getBoolean(TABLE_COLUMNS.PEOPLE.COMPANY.IS_ACTIVE);
        boolean isCurrent = rs.getBoolean(TABLE_COLUMNS.PEOPLE.COMPANY.IS_CURRENT);
        String name = rs.getString(TABLE_COLUMNS.PEOPLE.COMPANY.NAME);
        String code = rs.getString(TABLE_COLUMNS.PEOPLE.COMPANY.CODE);
        String notes = rs.getString(TABLE_COLUMNS.PEOPLE.COMPANY.NOTES);
        return new CompanyDTO(name, code, address, isActive, isCurrent, notes, key);

    }
    
    
    private AccountDTO createAccountFromResultSet(ResultSet rs) throws SQLException{
          int key = rs.getInt(TABLE_COLUMNS.PEOPLE.ACCOUNT.ID);
        int person= rs.getInt(TABLE_COLUMNS.PEOPLE.ACCOUNT.PERSON);
        int  type = rs.getInt(TABLE_COLUMNS.PEOPLE.ACCOUNT.TYPE);
        String name = rs.getString(TABLE_COLUMNS.PEOPLE.ACCOUNT.NAME);
           String number = rs.getString(TABLE_COLUMNS.PEOPLE.ACCOUNT.NUMBER);
              String closed = rs.getString(TABLE_COLUMNS.PEOPLE.ACCOUNT.CLOSED);
              String created = rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.CREATED_DATE);
              
       return new AccountDTO(name,number,person,closed,type,key,created);
    }
    
    

    private PersonDTO createPersonFromResultSet(ResultSet rs) throws SQLException {

        int key = rs.getInt(TABLE_COLUMNS.PEOPLE.PERSON.ID);
        String fName = rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.FIRST_NAME);

        String mName = rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.MIDDLE_NAME);

        String lName = rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.LAST_NAME);

        String nickname = rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.NICKNAME);
        String alias = rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.ALIAS);

        boolean is_active = rs.getBoolean(TABLE_COLUMNS.PEOPLE.PERSON.IS_ACTIVE);

        boolean is_current = rs.getBoolean(TABLE_COLUMNS.PEOPLE.PERSON.IS_CURRENT);

        int address = rs.getInt(TABLE_COLUMNS.PEOPLE.PERSON.ADDRESS);

        char gender = (rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.GENDER) + "").charAt(0);

        String created = rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.CREATED_DATE);
        String updated = rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.UPDATED_DATE);

        String birthday = rs.getString(TABLE_COLUMNS.PEOPLE.PERSON.BIRTHDAY);

        PersonDTO ret = new PersonDTO(fName, mName, lName, nickname, alias, gender, is_current, is_active, address, created, updated, birthday, key);;

        return ret;

    }

    @Override
    public PersonDTO getPerson(BaseDTO key) throws ServiceError {
        try {
            ResultSet rs = this.getService().getForId(TABLE_COLUMNS.PEOPLE.PERSON.TABLE_NAME, TABLE_COLUMNS.PEOPLE.PERSON.ID, key.getKey());

            rs.next();
            return this.createPersonFromResultSet(rs);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public List<PersonDTO> getPersons() throws ServiceError {
        try {
            ResultSet rs = this.getService().getAllForTable(TABLE_COLUMNS.PEOPLE.PERSON.TABLE_NAME);
            List<PersonDTO> ret = DataObjectGenerator.createList();

            while (rs.next()) {
                ret.add(this.createPersonFromResultSet(rs));

            }

            return ret;

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }
    }

    @Override
    public int addPerson(PersonDTO toAdd) throws ServiceError {

        try {
            BaseSearchParameter toInsert = new PersonSearchParameters(toAdd);

            return this.getService().insertAndGet(toInsert);
        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }
    }

    @Override
    public List<PersonDTO> searchPersons(PersonSearchParameters params) throws ServiceError {

        try {
            String query = PersonSearchBuilder.createQueryForPerson(params);

            ResultSet rs = this.getService().executeQuery(query);

            List<PersonDTO> ret = DataObjectGenerator.createList();

            while (rs.next()) {

                ret.add(this.createPersonFromResultSet(rs));
            }

            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public EmployeeDTO getEmployee(BaseDTO key) throws ServiceError {
        try {
            ResultSet rs = this.getService().getForId(TABLE_COLUMNS.PEOPLE.EMPLOYEE.TABLE_NAME, TABLE_COLUMNS.PEOPLE.EMPLOYEE.ID, key.getKey());

            rs.next();
            return this.createEmployeeFromResultSet(rs);

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public List<EmployeeDTO> getEmployees() throws ServiceError {

        try {
            ResultSet rs = this.getService().getAllForTable(TABLE_COLUMNS.PEOPLE.EMPLOYEE.TABLE_NAME);
            List<EmployeeDTO> ret = DataObjectGenerator.createList();

            while (rs.next()) {
                ret.add(this.createEmployeeFromResultSet(rs));

            }

            return ret;

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }

    }

    @Override
    public int addEmployee(EmployeeDTO toAdd) throws ServiceError {
        try {
            BaseSearchParameter toInsert = new EmployeeSearchParameters(toAdd);

            return this.getService().insertAndGet(toInsert);
        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }

    }

    @Override
    public List<EmployeeDTO> searchEmployees(EmployeeSearchParameters params) throws ServiceError {
        try {
            String query = PersonSearchBuilder.createQueryForEmployee(params);

            ResultSet rs = this.getService().executeQuery(query);

            List<EmployeeDTO> ret = DataObjectGenerator.createList();

            while (rs.next()) {

                ret.add(this.createEmployeeFromResultSet(rs));
            }

            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }
    }

    private <BaseDTO> BaseDTO checkForSingular(List<BaseDTO> toCheck) throws ServiceError {
        if (toCheck.size() > 1) {
            throw new ServiceError("MULTIPLE RESULTS FOUND");
        }

        if (toCheck.size() < 1) {
            return null;
        }

        return toCheck.get(0);
    }

    @Override
    public int addCompany(CompanyDTO toAdd) throws ServiceError {
        try {
            BaseSearchParameter toInsert = new CompanySearchParameters(toAdd);

            return this.getService().insertAndGet(toInsert);
        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }

    }

    @Override
    public int addCompanyRep(CompanyDTO comp, PersonDTO person) throws ServiceError {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CompanyDTO> getCompanies() throws ServiceError {
        try {
            ResultSet rs = this.getService().getAllForTable(TABLE_COLUMNS.PEOPLE.COMPANY.TABLE_NAME);
            List<CompanyDTO> ret = DataObjectGenerator.createList();

            while (rs.next()) {
                ret.add(this.createCompanyFromResultSet(rs));

            }

            return ret;

        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }
    }

    @Override
    public List<CompanyDTO> searchCompanies(CompanySearchParameters params) throws ServiceError {
        try {
            String query = PersonSearchBuilder.createQueryForCompany(params);

            ResultSet rs = this.getService().executeQuery(query);

            List<CompanyDTO> ret = DataObjectGenerator.createList();

            while (rs.next()) {

                ret.add(this.createCompanyFromResultSet(rs));
            }

            return ret;
        } catch (SQLException ex) {
            throw new ServiceError(ex);
        }
    }

    
    

    @Override
    public List<TypeAssignmentDTO> getRolesForEmployee(BaseDTO employee) throws ServiceError {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
     public List<EmployeeDTO> getEmployeesIDsWithAssignment(TypeDTO target) throws ServiceError{
         try {
             
             
             String query = " SELECT per.* FROM " + TABLE_COLUMNS.PEOPLE.EMPLOYEE.TABLE_NAME + " AS per INNER JOIN ";
             
             query = query + TABLE_COLUMNS.PEOPLE.EMPLOYEE_ROLES.TABLE_NAME + " AS emp";
             
             query = query + " ON per." +TABLE_COLUMNS.PEOPLE.PERSON.ID + " = emp." + TABLE_COLUMNS.PEOPLE.EMPLOYEE_ROLES.EMPLOYEE;
             query = query + " INNER JOIN " + TABLE_COLUMNS.TYPE.EMPLOYEE_ROLE.TABLE_NAME + " AS er ON emp." + TABLE_COLUMNS.PEOPLE.EMPLOYEE_ROLES.ROLE;
             query = query + " = " + TABLE_COLUMNS.TYPE.EMPLOYEE_ROLE.ID + " WHERE ";
             if(target.hasKeySet()== true){
                 query = query + TABLE_COLUMNS.TYPE.EMPLOYEE_ROLE.ID + " = " + target.getTypeID();
             }
             else
             {
                 query = query + TABLE_COLUMNS.TYPE.EMPLOYEE_ROLE.NAME + " = '" + target.getTypeName() + "'";
             }
             query = query + ";";
             
             
             
             ResultSet rs = this.getService().executeQuery(query);
          
             
             List<EmployeeDTO> ret = DataObjectGenerator.createList();
             
             while(rs.next()){
                 
                 ret.add(this.createEmployeeFromResultSet(rs));
                 
             }
             
             
         return ret;
         
         } catch (SQLException ex) {
         throw new ServiceError(ex);    } }
    

    
        
        
        @Override
    public Integer addRoleForEmployee(EmployeeDTO employee, TypeDTO type)throws ServiceError {
   
        
        try {
          return  this.getService().addAssignment(TypeAssignmentTable.getEmployeeTypeTable(),employee, type);
          } catch (SQLException ex) {
         throw new ServiceError(ex);    }
        
    
    }

        @Override
      public Integer addPersonToCompany(PersonDTO person, CompanyDTO company) throws ServiceError{
   
        try {
       
            String stmt = "INSERT INTO " + TABLE_COLUMNS.PEOPLE.COMPANY_PERSON.TABLE_NAME;
            
            stmt = stmt+ " (" +TABLE_COLUMNS.PEOPLE.COMPANY_PERSON.PERSON + " , " ;
            stmt =stmt +  " " +TABLE_COLUMNS.PEOPLE.COMPANY_PERSON.COMPANY + ") " ;
            stmt = stmt + " VALUES (" + person.getKey() + " , " + company.getKey() + ");";
                   
            
            this.getService().executeStatement(stmt);
        
            
            
            String query = "SELECT " + TABLE_COLUMNS.PEOPLE.COMPANY_PERSON.ID;
            query = query + " FROM " + TABLE_COLUMNS.PEOPLE.COMPANY_PERSON.TABLE_NAME;
            query = query + " WHERE " + TABLE_COLUMNS.PEOPLE.COMPANY_PERSON.PERSON;
            query = query + "  = " + person.getKey() + " AND ";
            query = query + TABLE_COLUMNS.PEOPLE.COMPANY_PERSON.COMPANY + " = " ;
            query =query + company.getKey() + ";";
            
            ResultSet rs = this.getService().executeQuery(query);
            rs.next();
            return rs.getInt(TABLE_COLUMNS.PEOPLE.COMPANY_PERSON.ID);
        
        
        
        } catch (SQLException ex) {
         throw new ServiceError(ex);    }
        
    
    }

    @Override
    public List<PersonDTO> getPeopleForCompany(CompanyDTO company) throws ServiceError {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
@Override
      public CompanyDTO getCompany(BaseDTO key) throws ServiceError{
        try {
            String query = "SELECT * FROM " + TABLE_COLUMNS.PEOPLE.COMPANY.TABLE_NAME;
            
            query = query + " WHERE " + TABLE_COLUMNS.PEOPLE.COMPANY.ID +" = " + key.getKey();
            
            query = query + ";";
            
            ResultSet rs = this.getService().executeQuery(query);
            rs.next();
            
            return this.createCompanyFromResultSet(rs);
        } catch (SQLException ex) {
        throw new ServiceError(ex);}
       
       
          
      }

    @Override
    public List<AccountDTO> getAccountsForPerson(PersonDTO person) throws ServiceError {
      
        try {
            String query = "SELECT * FROM " + TABLE_COLUMNS.PEOPLE.ACCOUNT.TABLE_NAME;
            query = query + " WHERE " + TABLE_COLUMNS.PEOPLE.ACCOUNT.PERSON + " = ";
            
            query = query + person.getKey() + " ORDER BY " + TABLE_COLUMNS.PEOPLE.ACCOUNT.ID;
            
            query = query + " DESC;";
            ResultSet rs = this.getService().executeQuery(query);
            Date curr = new Date(System.currentTimeMillis());
            List<AccountDTO> ret  = DataObjectGenerator.createList();
            while(rs.next()){
                
                AccountDTO toAdd = this.createAccountFromResultSet(rs);
                
                
              
                        if(toAdd.getClosedDate() == null){
                            ret.add(toAdd);
                            
                        }
                        else
                        {
                            Date check = new Date(toAdd.getClosedDate());
                            if(check.after(curr)){
                                ret.add(toAdd);
                            }
                          
                        }
            }
            
            return ret;
            
        } catch (SQLException ex) {
       throw new ServiceError(ex);   }
    
    
    
    
    }

    @Override
    public AccountDTO addAccount(AccountDTO toAdd) throws ServiceError {
      
        try {
            String stmt = "INSERT INTO " + TABLE_COLUMNS.PEOPLE.ACCOUNT.TABLE_NAME;
            stmt = stmt + "(" + TABLE_COLUMNS.PEOPLE.ACCOUNT.CLOSED;
      
            
            stmt = stmt + ", " + TABLE_COLUMNS.PEOPLE.ACCOUNT.NAME;
             stmt = stmt + ", " + TABLE_COLUMNS.PEOPLE.ACCOUNT.NUMBER;
            
            stmt = stmt + ", " + TABLE_COLUMNS.PEOPLE.ACCOUNT.PERSON;
            
            stmt = stmt + ", " + TABLE_COLUMNS.PEOPLE.ACCOUNT.TYPE;
            
            String closed = toAdd.getClosedDate();
            
            
           
            if(closed == null){
                closed = "NULL";
            }
            else
            {
                closed = "'" + closed + "'";
            }
            
            
        
            
            stmt = stmt + ") VALUES (" + closed ;
            stmt = stmt + ",'" + toAdd.getAccountName() + "','";
               stmt = stmt +  toAdd.getAccountName() + "',";
            stmt = stmt + toAdd.getPersonId() + "," + toAdd.getAccountType() + ");";
            
            this.getService().executeStatement(stmt);
            return this.getAccountsForPerson(new PersonDTO(toAdd.getPersonId())).get(0);
            
        } catch (SQLException ex) {
        throw new ServiceError(ex);  }
    
    
    
            
    
    
    
    
    }

    @Override
    public AccountDTO getAccount(BaseDTO key) throws ServiceError {
      try {
            String query = "SELECT * FROM " + TABLE_COLUMNS.PEOPLE.ACCOUNT.TABLE_NAME;
            query = query + " WHERE " + TABLE_COLUMNS.PEOPLE.ACCOUNT.ID + " = ";
            
            query = query + key.getKey() +";";
            ResultSet rs = this.getService().executeQuery(query);
            
            List<AccountDTO> ret  = DataObjectGenerator.createList();
            rs.next();
            return this.createAccountFromResultSet(rs);
                } catch (SQLException ex) {
       throw new ServiceError(ex);   } }
    
      
      
      
      
      
   
    
    
    
    
}
