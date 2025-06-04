/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.address;

import CASS.data.BaseSearchParameter;
import CASS.services.mysql.SearchTable;

/**
 *
 * @author ctydi
 */
public class AddressSearchParameters extends BaseSearchParameter {

    private CitySearchParameters city;

    public static final String STREET = "street";

    public static final String STREET2 = "street_2";

    public static final String POST_CODE = "post_code";

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
        this.addSearchParameter(STREET, street);
    }

    public void setStreet2(String street) {
        this.addSearchParameter(STREET2, street);
    }

    public void setPostCode(String street) {
        this.addSearchParameter(POST_CODE, street);
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

}
