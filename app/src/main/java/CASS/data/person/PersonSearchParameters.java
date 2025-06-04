/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.person;

import CASS.data.address.*;
import CASS.data.BaseSearchParameter;
import CASS.services.mysql.BooleanSearchValue;
import CASS.services.mysql.SearchTable;
import java.util.HashSet;

/**
 *
 * @author ctydi
 */
public class PersonSearchParameters extends BaseSearchParameter {

 
   public static final String FIRST_NAME = "first_name";
    
    
   public static final String MIDDLE_NAME = "middle_name";
    
    
    
   public static final String LAST_NAME = "last_name";
    
    
    public static final String IS_ACTIVE = "is_active";
    
    public static final String IS_CURRENT = "is_current";
    
    
    public static final String NICKNAME = "nickname";
    
    public static final String ALIAS = "alias";
    
     public static final String GENDER = "sex";
        
     public static final String BIRTHDAY = "birthday";
    
    
    
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
        this.addSearchParameter(FIRST_NAME, value);
    }
    
    
     public void setMiddleName(String value){
        this.addSearchParameter(MIDDLE_NAME, value);
    }
     public void setLastName(String value){
        this.addSearchParameter(LAST_NAME, value);
    }
    
      public void setNickName(String value){
        this.addSearchParameter(NICKNAME, value);
    }
      public void setAlias(String value){
        this.addSearchParameter(ALIAS, value);
    }
      
      public void setIsCurrent(boolean toSet){
          this.addSearchParameter(IS_CURRENT, new BooleanSearchValue(toSet) );
          
      }
      
       public void setIsActive(boolean toSet){
          this.addSearchParameter(IS_ACTIVE, new BooleanSearchValue(toSet) );
          
      }
      
         public void setGender(char toSet){
          this.addSearchParameter(GENDER, "" + toSet );
          
      }
         
         public void setBirthday(String birthday){
             this.addSearchParameter(BIRTHDAY, birthday);
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
    
   

}
