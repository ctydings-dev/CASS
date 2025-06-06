/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.data.BaseSearchParameter;
import CASS.data.address.CountryDTO;
import CASS.services.mysql.TABLE_COLUMNS;

/**
 *
 * @author ctydi
 */
public class CountrySearchParameters extends BaseSearchParameter {

  
    public CountrySearchParameters(String name, String abbv) {
        super(SearchTable.getCountryTable());
        this.addCountryName(name);
        this.addCountryAbbv(abbv);

    }

    public CountrySearchParameters(CountryDTO value) {
        super(SearchTable.getCountryTable());
        this.addCountryName(value.getCountryName());
        this.addCountryAbbv(value.getAbbreviation());

    }

    public CountrySearchParameters() {
        super(SearchTable.getCountryTable());
    }

    public CountrySearchParameters(int key) {
        super(key, SearchTable.getCountryTable());

    }

    public void addCountryName(String name) {
        this.addSearchParameter(TABLE_COLUMNS.LOCATION.COUNTRY.NAME, name);
    }

    public void addCountryAbbv(String abbv) {
        this.addSearchParameter(TABLE_COLUMNS.LOCATION.COUNTRY.ABBV, abbv);
    }
    
        @Override
    public String getSearchQuery() {
   return AddressSearchBuilder.createQueryForCountry(this); }

}
