/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.data.BaseSearchParameter;
import CASS.data.address.StateDTO;
import CASS.services.mysql.TABLE_COLUMNS;

/**
 *
 * @author ctydi
 */
public class StateSearchParameters extends BaseSearchParameter {



    private CountrySearchParameters country;

    public StateSearchParameters(String name, String abbv) {
        super(SearchTable.getStateTable());
        this.addSearchParameter(TABLE_COLUMNS.LOCATION.STATE.NAME, name);
        this.addSearchParameter(TABLE_COLUMNS.LOCATION.STATE.ABBV, abbv);
    }

    public StateSearchParameters() {
        super(SearchTable.getStateTable());

    }

    public StateSearchParameters(int key) {
        super(key, SearchTable.getStateTable());
    }

    public StateSearchParameters(String name, String abbv, CountrySearchParameters country) {
        super(SearchTable.getStateTable());
        this.addStateName(name);
        this.addStateAbbv(abbv);
        this.country = country;
    }

    public StateSearchParameters(StateDTO value) {
        super(SearchTable.getStateTable());
        this.addStateName(value.getStateName());
        this.addStateAbbv(value.getAbbreviation());

        this.country = new CountrySearchParameters(value.getCountryID());
    }

    public void addStateName(String name) {
        this.addSearchParameter(TABLE_COLUMNS.LOCATION.STATE.NAME, name);
    }

    public void addStateAbbv(String abbv) {
        this.addSearchParameter(TABLE_COLUMNS.LOCATION.STATE.ABBV, abbv);
    }

    public CountrySearchParameters getCountry() {
        return country;
    }

    public void setCountry(CountrySearchParameters country) {
        this.country = country;
    }
    
    public void setCountry(int id){
        this.setCountry(new CountrySearchParameters(id));
    }
    

    public boolean isSearchByCountry() {
        return this.hasParameter(this.getCountry());
    }
        protected String getKeyFields(){
        
            
         if(this.isSearchByCountry() == false){
                return "";
            }
               
            
        return TABLE_COLUMNS.LOCATION.STATE.COUNTRY + ", ";
        
    }
    
      protected String getKeyValues(){
        
          if(this.isSearchByCountry() == false){
                return "";
            }
            
          
          
        return this.getCountry().getKey()+ ", ";
        
    }
        @Override
    public String getSearchQuery() {
   return AddressSearchBuilder.createQueryForState(this); }
    
    
}
