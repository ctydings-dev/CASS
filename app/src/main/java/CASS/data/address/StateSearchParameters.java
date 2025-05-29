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
public class StateSearchParameters extends BaseSearchParameter {

    public static final String STATE_NAME = "state_name";

    public static final String STATE_ABBV = "state_abbv";

    private CountrySearchParameters country;

    public StateSearchParameters(String name, String abbv) {
        super(SearchTable.getStateTable());
        this.addSearchParameter("state_name", name);
        this.addSearchParameter("abbv", abbv);
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
        this.addSearchParameter(STATE_NAME, name);
    }

    public void addStateAbbv(String abbv) {
        this.addSearchParameter(STATE_ABBV, abbv);
    }

    public CountrySearchParameters getCountry() {
        return country;
    }

    public void addCountry(CountrySearchParameters country) {
        this.country = country;
    }

    public boolean isSearchByCountry() {
        return this.hasParameter(this.getCountry());
    }

}
