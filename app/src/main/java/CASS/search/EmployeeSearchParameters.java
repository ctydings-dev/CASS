/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.data.address.*;
import CASS.data.BaseSearchParameter;
import CASS.data.TypeRepository;
import CASS.data.TypeRepository.EMPLOYEE_TYPE;
import CASS.data.person.EmployeeDTO;
import CASS.services.mysql.SearchParameter;
import CASS.services.mysql.TABLE_COLUMNS;
import java.util.Date;
import java.util.HashSet;

/**
 *
 * @author ctydi
 */
public class EmployeeSearchParameters extends BaseSearchParameter {


  
    

    PersonSearchParameters person;
    

  
    public EmployeeSearchParameters() {
        super(SearchTable.getEmployeeTable());

    }

    
     public EmployeeSearchParameters(EmployeeDTO toSet) {
        super(SearchTable.getEmployeeTable());
        this.setDTO(toSet);
         

    }

    
    
    public EmployeeSearchParameters(int key) {
        super(key, SearchTable.getEmployeeTable());
    }

   
    public void setPerson(PersonSearchParameters toSet){
        this.person = toSet;
    }
    
    public void setPerson(int id){
        this.setPerson(new PersonSearchParameters(id));
    }
    
    public void setEmployeeCode(String code){
        this.addSearchParameter(TABLE_COLUMNS.PEOPLE.EMPLOYEE.EMPLOYEE_CODE,code );
    }
    
public void setEmployeeTypeID(EMPLOYEE_TYPE toSet){
    int id = TypeRepository.getKey(toSet);
    
    this.addSearchParameter(TABLE_COLUMNS.PEOPLE.EMPLOYEE.EMPLOYEE_TYPE, new SearchValue(id));
    }

public void setEmployeeTypeID(int toSet){
   
    this.addSearchParameter(TABLE_COLUMNS.PEOPLE.EMPLOYEE.EMPLOYEE_TYPE, new SearchValue(toSet));
    }



public void setDTO(EmployeeDTO toUse){
    this.setHireDate(toUse.getHireDate());
    this.setEmployeeTypeID(toUse.getEmployeeTypeId());
    this.setEmployeeCode(toUse.getEmployeeCode());
    this.setPerson(toUse.getPersonID());
    this.setIsActive(toUse.isIsActive());
    
}


public void setIsActive(boolean toSet){
    
    this.addSearchParameter(TABLE_COLUMNS.PEOPLE.EMPLOYEE.IS_ACTIVE, toSet);
    
}

public void setHireDate(String toSet){
    
    
    this.addSearchParameter(TABLE_COLUMNS.PEOPLE.EMPLOYEE.HIRE_DATE, new SearchValue(toSet));
    
}
    public PersonSearchParameters getPerson(){
        return this.person;
    }

public boolean isSearchByPerson(){
    
    return this.person != null;
}


   
        @Override
    public String getSearchQuery() {
   return PersonSearchBuilder.createQueryForEmployee(this); }

    
    
       protected String getKeyFields(){
        if(this.isSearchByPerson() == false){
                return "";
            }
            
            
            
        return TABLE_COLUMNS.PEOPLE.EMPLOYEE.PERSON + ", ";
        
    }
    
      protected String getKeyValues(){
        
          if(this.isSearchByPerson() == false){
                return "";
            }
            
        return ""+this.getPerson().getKey() + ", ";
        
    }

      
      
    
    
    
    
}
