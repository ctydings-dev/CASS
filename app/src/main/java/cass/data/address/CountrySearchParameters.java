/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cass.data.address;

import cass.data.BaseSearchParameter;
import cass.services.mysql.SearchTable;

/**
 *
 * @author ctydi
 */
public class CountrySearchParameters extends BaseSearchParameter {

    public static final String COUNTRY_NAME = "country_name";

    public static final String COUNTRY_ABBV = "country_abbv";

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
        this.addSearchParameter(COUNTRY_NAME, name);
    }

    public void addCountryAbbv(String abbv) {
        this.addSearchParameter(COUNTRY_ABBV, abbv);
    }

}
