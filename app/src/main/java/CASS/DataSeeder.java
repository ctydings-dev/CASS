/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import CASS.data.TypeRepository;
import CASS.data.address.AddressDTO;
import CASS.data.address.CityDTO;
import CASS.data.address.CountryDTO;
import CASS.data.address.StateDTO;
import CASS.services.AddressService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import CASS.services.ServiceProvider;
import CASS.services.TypeService;
import java.sql.SQLException;

/**
 *
 * @author ctydi
 */
public class DataSeeder {

  

    public static void main(String[] args) throws ServiceError, SQLException {
        AddressService addrSvc = ServiceProvider.getAddressService();
        PersonService prsnSvc = ServiceProvider.getPersonService();
        
            TypeService typeSvc = ServiceProvider.getTypeService();

        TypeRepository.setupRepo(typeSvc);
clearDBs();
    AddressDataSeeder.seedAddresses(addrSvc);
    PersonDataSeeder.seedPersonData(prsnSvc);
        
    }
    
    
    private static void clearDBs() throws SQLException{
        
        String query = "DELETE FROM employees;";
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
