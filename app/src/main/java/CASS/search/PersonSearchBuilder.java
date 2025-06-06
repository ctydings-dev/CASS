/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.search.SearchTable;
import CASS.search.AddressSearchParameters;
import CASS.search.CompanySearchParameters;
import CASS.search.EmployeeSearchParameters;
import CASS.search.PersonSearchParameters;
import CASS.services.mysql.SearchBuilder;
import static CASS.search.AddressSearchBuilder.addCityToQuery;

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
        SearchBuilder builder = new SearchBuilder(SearchTable.getEmployeeTable());
        builder.addSearhParameter(emp);
        if (emp.isSearchByPerson()) {
              builder.addJoinParameter(SearchTable.getEmployeeTable(),emp.getPerson());
           addPersonToQuery(builder,emp.getPerson());
        }
        return builder.createQuery();
    }
    
       public static String createQueryForCompany(CompanySearchParameters comapny) {
        SearchBuilder builder = new SearchBuilder(SearchTable.getCompanyTable());
        builder.addSearhParameter(comapny);
        if (comapny.isSearchByAddress()) {
            builder.addJoinParameter(SearchTable.getCompanyTable(), comapny.getAddress());
            AddressSearchBuilder.addAddressToQuery(builder, comapny.getAddress());
        }
        return builder.createQuery();
    }
     
     
     
}
