/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.data.address.AddressSearchParameters;
import CASS.data.person.EmployeeSearchParameters;
import CASS.data.person.PersonSearchParameters;
import static CASS.services.mysql.AddressSearchBuilder.addCityToQuery;

/**
 *
 * @author ctydi
 */
public class PersonSearchBuilder {
    
     public static String createQueryForPerson(PersonSearchParameters person) {
        SearchBuilder builder = new SearchBuilder(SearchTable.getPersonTable());
        addPersonToQuery(builder,person);
        return builder.createQuery();
    }
     
     
     private static  void addPersonToQuery(SearchBuilder builder, PersonSearchParameters person){
        builder.addSearhParameter(person);
        if (person.isSearchByAddress()) {
            builder.addJoinParameter(SearchTable.getPersonTable(), person.getAddress());
            AddressSearchBuilder.addAddressToQuery(builder, person.getAddress());
        }
         
         
     }
     
     
     
    
       public static String createQueryForEmployee(EmployeeSearchParameters emp) {
        SearchBuilder builder = new SearchBuilder(SearchTable.getPersonTable());
        builder.addSearhParameter(emp);
        if (emp.isSearchByPerson()) {
           addPersonToQuery(builder,emp.getPerson());
        }
        return builder.createQuery();
    }
    
     
     
     
     
}
