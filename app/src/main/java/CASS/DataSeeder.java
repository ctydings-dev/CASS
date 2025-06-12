/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.address.AddressDTO;
import CASS.data.address.CityDTO;
import CASS.data.address.CountryDTO;
import CASS.data.address.StateDTO;
import CASS.data.person.EmployeeDTO;
import CASS.services.AddressService;
import CASS.services.ItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import CASS.services.ServiceProvider;
import CASS.services.TypeService;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ctydi
 */
public class DataSeeder {

  

    public static void main(String[] args) throws ServiceError, SQLException {
        AddressService addrSvc = ServiceProvider.getAddressService();
        PersonService prsnSvc = ServiceProvider.getPersonService();
          ItemService itmSvc = ServiceProvider.getItemService();
            TypeService typeSvc = ServiceProvider.getTypeService();

        TypeRepository.setupRepo(typeSvc);
clearDBs();
    AddressDataSeeder.seedAddresses(addrSvc);
   PersonDataSeeder.seedPersonData(prsnSvc);
   ItemDataSeed.seedItemData(itmSvc);
   
   TypeDTO target =TypeRepository.getTypeDTO(TypeRepository.EMPLOYEE_ROLE.INSTRUCTOR);
   
   //List<EmployeeDTO> test = prsnSvc.getEmployeesIDsWithAssignment(target);
   //System.out.println(test);
        
    }
    
    
    private static void clearDBs() throws SQLException{
         String query ="";
           query = "DELETE FROM company_parents;";
       ServiceProvider.getMySql().executeStatement(query);
           query = "DELETE FROM inventory;";
       ServiceProvider.getMySql().executeStatement(query);
       
               query = "DELETE FROM accounts;";
       ServiceProvider.getMySql().executeStatement(query);
       
       
           query = "DELETE FROM inventory_transactions;";
       ServiceProvider.getMySql().executeStatement(query);
            query = "DELETE FROM items;";
       ServiceProvider.getMySql().executeStatement(query);
       
             query = "DELETE FROM company_persons;";
       ServiceProvider.getMySql().executeStatement(query);
        
            query = "DELETE FROM employee_roles;";
       ServiceProvider.getMySql().executeStatement(query);
       
             query = "DELETE FROM items;";
       ServiceProvider.getMySql().executeStatement(query);
       
       
          query = "DELETE FROM companies;";
       ServiceProvider.getMySql().executeStatement(query);
        
     query = "DELETE FROM employees;";
       ServiceProvider.getMySql().executeStatement(query);
       query = "DELETE FROM persons;";
       ServiceProvider.getMySql().executeStatement(query); 
       query = "DELETE FROM addresses;";
       ServiceProvider.getMySql().executeStatement(query);
       query = "DELETE FROM cities;";
       ServiceProvider.getMySql().executeStatement(query);
       query = "DELETE FROM states;";
       ServiceProvider.getMySql().executeStatement(query);
       query = "DELETE FROM countries;";
       ServiceProvider.getMySql().executeStatement(query);
    }
    
}
