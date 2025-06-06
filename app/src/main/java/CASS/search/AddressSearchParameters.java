/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.data.BaseSearchParameter;
import CASS.data.address.AddressDTO;
import CASS.services.mysql.TABLE_COLUMNS;

/**
 *
 * @author ctydi
 */
public class AddressSearchParameters extends BaseSearchParameter {

    private CitySearchParameters city;



    public AddressSearchParameters() {
        super(SearchTable.getAddressTable());
    }

    public AddressSearchParameters(CitySearchParameters city) {
        super(SearchTable.getAddressTable());
        this.city = city;

    }
    
    
       public AddressSearchParameters(int id) {
        super(id, SearchTable.getAddressTable());
      

    }
    
    

    public AddressSearchParameters(AddressDTO value) {
        super(SearchTable.getAddressTable());
        this.setStreet(value.getStreet());
        this.setStreet2(value.getStreet2());
        this.setPostCode(value.getPostCode());
        this.setCity(new CitySearchParameters(value.getCityID()));

    }

    public void setStreet(String street) {
        this.addSearchParameter(TABLE_COLUMNS.LOCATION.ADDRESS.STREET, street);
    }

    public void setStreet2(String street) {
        this.addSearchParameter(TABLE_COLUMNS.LOCATION.ADDRESS.STREET_2, street);
    }

    public void setPostCode(String street) {
        this.addSearchParameter(TABLE_COLUMNS.LOCATION.ADDRESS.POST_CODE, street);
    }

    public CitySearchParameters getCity() {
        return this.city;
    }

    
    public void setCity(int id){
        this.setCity(new CitySearchParameters(id));
    }
    
    
    public void setCity(CitySearchParameters city) {
        this.city = city;
    }

    public boolean isSearchByCity() {
        return this.hasParameter(this.getCity());
    }
    
        protected String getKeyFields(){
        if(this.isSearchByCity() == false){
                return "";
            }
            
            
            
        return TABLE_COLUMNS.LOCATION.ADDRESS.CITY + ", ";
        
    }
    
      protected String getKeyValues(){
        
          if(this.isSearchByCity() == false){
                return "";
            }
            
        return ""+this.getCity().getKey() + ", ";
        
    }

    @Override
    public String getSearchQuery() {
   return AddressSearchBuilder.createQueryForAddress(this); }
    
    

}
