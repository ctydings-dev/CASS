/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.data.BaseSearchParameter;
import CASS.data.address.CityDTO;
import CASS.services.mysql.TABLE_COLUMNS;

/**
 *
 * @author ctydi
 */
public class CitySearchParameters extends BaseSearchParameter {

    private StateSearchParameters state;



    public CitySearchParameters(int key) {
        super(key, SearchTable.getCityTable());
        this.state = null;

    }

    public CitySearchParameters(String name) {
        super(SearchTable.getCityTable());
        this.addCityName(name);
        this.state = null;
    }

    public CitySearchParameters(CityDTO value) {
        super(SearchTable.getCityTable());
        this.addCityName(value.getCityName());
        this.state = new StateSearchParameters(value.getStateID());
    }

    public CitySearchParameters() {
        super(SearchTable.getCityTable());
        this.state = null;
    }

    public void setState(StateSearchParameters toAdd) {
        this.state = toAdd;
    }

    
    public void setState(int id){
        this.setState(new StateSearchParameters(id));
    }
    
    
    
    
    
    public StateSearchParameters getState() {
        return this.state;
    }

    public void addCityName(String name) {
        this.addSearchParameter(TABLE_COLUMNS.LOCATION.CITY.NAME, name);
    }

    public boolean isSearchByState() {
        return this.hasParameter(this.getState());
    }
    
    
        protected String getKeyFields(){
            
            if(this.isSearchByState() == false){
                return "";
            }
            
        
        return TABLE_COLUMNS.LOCATION.CITY.STATE + ", ";
        
    }
    
      protected String getKeyValues(){
          
          if(this.isSearchByState() == false){
                return "";
            }
            
        
        return this.getState().getKey()+ ", ";
        
    }
    
          @Override
    public String getSearchQuery() {
   return AddressSearchBuilder.createQueryForCity(this); }
      

}
