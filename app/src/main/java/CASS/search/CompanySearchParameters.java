/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.data.BaseSearchParameter;
import CASS.data.person.CompanyDTO;
import CASS.services.mysql.TABLE_COLUMNS;

/**
 *
 * @author ctydi
 */
public class CompanySearchParameters extends BaseSearchParameter {

      
     
    private AddressSearchParameters adr;
    
    public CompanySearchParameters(int key) {
        super(key, SearchTable.getCompanyTable());
    }
      public CompanySearchParameters() {
        super( SearchTable.getCompanyTable());
    }
      
      
          public CompanySearchParameters(CompanyDTO toSet) {
        super( SearchTable.getCompanyTable());
        setValues(toSet);
    }
      
          
          public void setValues(CompanyDTO toSet){
              this.setCompanyName(toSet.getCompanyName());
              this.setCompanyCode(toSet.getCompanyCode());
              this.setIsActive(toSet.isIsActive());
              this.setIsCurrent(toSet.isIsCurrent());
              this.setAddress(toSet.getAddressId());
              this.setNotes(toSet.getNotes());
          }
          
         
      
      
      
      public void setCompanyName(String value){
          this.addSearchParameter(TABLE_COLUMNS.PEOPLE.COMPANY.NAME, value);
      }
      
      
      public void setCompanyCode(String value){
          this.addSearchParameter(TABLE_COLUMNS.PEOPLE.COMPANY.CODE, value);
      }
      
      
      public void setNotes(String value){
          SearchValue search = new SearchValue(value);
          search.setIsExact(false);
          this.addSearchParameter(TABLE_COLUMNS.PEOPLE.COMPANY.NOTES, search);
      }
      
      public void setIsActive(boolean value){
          SearchValue search = new SearchValue(value);
          this.addSearchParameter(TABLE_COLUMNS.PEOPLE.COMPANY.IS_ACTIVE, search);
      }
      
        public void setIsCurrent(boolean value){
          SearchValue search = new SearchValue(value);
          this.addSearchParameter(TABLE_COLUMNS.PEOPLE.COMPANY.IS_CURRENT, search);
      }

        
         public void setAddress(Integer toSet){
        this.setAddress(new AddressSearchParameters(toSet));
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
   return PersonSearchBuilder.createQueryForCompany(this); }
      
    
    
       protected String getKeyFields(){
        if(this.isSearchByAddress() == false){
                return "";
            }
            
            
            
        return TABLE_COLUMNS.PEOPLE.COMPANY.ADDRESS + ", ";
        
    }
    
      protected String getKeyValues(){
        
          if(this.isSearchByAddress() == false){
                return "";
            }
            
        return ""+this.getAddress().getKey() + ", ";
        
    }
    
    
    
    
}




