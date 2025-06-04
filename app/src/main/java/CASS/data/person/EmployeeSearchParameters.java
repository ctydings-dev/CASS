/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.person;

import CASS.data.address.*;
import CASS.data.BaseSearchParameter;
import CASS.data.TypeRepository;
import CASS.data.TypeRepository.EMPLOYEE_TYPE;
import CASS.services.mysql.SearchParameter;
import CASS.services.mysql.SearchTable;
import CASS.services.mysql.SearchValue;
import java.util.Date;

/**
 *
 * @author ctydi
 */
public class EmployeeSearchParameters extends BaseSearchParameter {


  
    
    
    public static final String HIRE_DATE = "hire_date";
    
    public static final String EMPLOYEE_TYPE_ID = "employee_type_id";
    
    public static final String IS_ACTIVE = "is_active";
    
    public static final String EMPLOYEE_CODE = "employee_code";
    
    
    PersonSearchParameters person;
    

  
    public EmployeeSearchParameters() {
        super(SearchTable.getEmployeeTable());

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
        this.addSearchParameter(EMPLOYEE_CODE,code );
    }
    
public void setEmployeeTypeID(EMPLOYEE_TYPE toSet){
    int id = TypeRepository.getKey(toSet);
    
    this.addSearchParameter(EMPLOYEE_TYPE_ID, new SearchValue(id));
    }

public void setEmployeeTypeID(int toSet){
   
    this.addSearchParameter(EMPLOYEE_TYPE_ID, new SearchValue(toSet));
    }



public void setDTO(EmployeeDTO toUse){
    this.setHireDate(toUse.getHireDate());
    this.setEmployeeTypeID(toUse.getEmployeeTypeId());
    this.setEmployeeCode(toUse.getEmployeeCode());
    this.setPerson(toUse.getPersonID());
    
}



public void setHireDate(String toSet){
    
    
    this.addSearchParameter(HIRE_DATE, new SearchValue(toSet));
    
}
    public PersonSearchParameters getPerson(){
        return this.person;
    }

public boolean isSearchByPerson(){
    
    return this.person != null;
}

    
}
