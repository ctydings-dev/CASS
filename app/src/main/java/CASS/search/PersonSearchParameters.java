/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.data.address.*;
import CASS.data.BaseSearchParameter;
import CASS.data.person.PersonDTO;
import CASS.services.mysql.TABLE_COLUMNS;
import java.util.HashSet;

/**
 *
 * @author ctydi
 */
public class PersonSearchParameters extends BaseSearchParameter {

 
    
    
    private AddressSearchParameters adr;
    
    
  
    public PersonSearchParameters() {
        super(SearchTable.getPersonTable());
    }


    public PersonSearchParameters(int key) {
        super(key, SearchTable.getPersonTable());
    }

    
    public PersonSearchParameters(PersonDTO toUse){
        super(SearchTable.getPersonTable());
        this.setDTO(toUse);
    }

    
    
    public void setDTO(PersonDTO toSet){
        this.setFirstName(toSet.getFirstName());
        this.setMiddleName(toSet.getMiddleName());
        this.setLastName(toSet.getLastName());
        this.setNickName(toSet.getNickname());
        this.setAlias(toSet.getAlias());
        this.setGender(toSet.getGender());
        this.setAddress(toSet.getAddressID());
        this.setIsActive(toSet.isIs_active());
        this.setIsCurrent(toSet.isIs_current());
        this.setBirthday(toSet.getBirthday());     
    }
    
    
    public void setAddress(int id){
        this.setAddress(new AddressSearchParameters(id));
        
    }
    
    public void setFirstName(String value){
        this.addSearchParameter(TABLE_COLUMNS.PEOPLE.PERSON.FIRST_NAME, value);
    }
    
    
     public void setMiddleName(String value){
        this.addSearchParameter(TABLE_COLUMNS.PEOPLE.PERSON.MIDDLE_NAME, value);
    }
     public void setLastName(String value){
        this.addSearchParameter(TABLE_COLUMNS.PEOPLE.PERSON.LAST_NAME, value);
    }
    
      public void setNickName(String value){
        this.addSearchParameter(TABLE_COLUMNS.PEOPLE.PERSON.NICKNAME, value);
    }
      public void setAlias(String value){
        this.addSearchParameter(TABLE_COLUMNS.PEOPLE.PERSON.ALIAS, value);
    }
      
      public void setIsCurrent(boolean toSet){
          this.addSearchParameter(TABLE_COLUMNS.PEOPLE.PERSON.IS_ACTIVE, new BooleanSearchValue(toSet) );
          
      }
      
       public void setIsActive(boolean toSet){
          this.addSearchParameter(TABLE_COLUMNS.PEOPLE.PERSON.IS_CURRENT, new BooleanSearchValue(toSet) );
          
      }
      
         public void setGender(char toSet){
          this.addSearchParameter(TABLE_COLUMNS.PEOPLE.PERSON.GENDER, "" + toSet );
          
      }
         
         public void setBirthday(String birthday){
             this.addSearchParameter(TABLE_COLUMNS.PEOPLE.PERSON.BIRTHDAY, birthday);
         }
      
      
    public void setAddress(AddressSearchParameters toSet){
        this.adr = toSet;
    }
    
    public AddressSearchParameters getAddress(){
        return this.adr;
    }
    

    public boolean isSearchByAddress() {
        return this.hasParameter(this.getAddress());
    }
    
   
        @Override
    public String getSearchQuery() {
   return PersonSearchBuilder.createQueryForPerson(this); }
    
       protected String getKeyFields(){
        if(this.isSearchByAddress() == false){
                return "";
            }
            
            
            
        return TABLE_COLUMNS.PEOPLE.PERSON.ADDRESS + ", ";
        
    }
    
      protected String getKeyValues(){
        
          if(this.isSearchByAddress() == false){
                return "";
            }
            
        return ""+this.getAddress().getKey() + ", ";
        
    }

    

}
