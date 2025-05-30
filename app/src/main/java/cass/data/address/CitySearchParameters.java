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
public class CitySearchParameters extends BaseSearchParameter {

    private StateSearchParameters state;

    public static final String CITY_NAME = "city_name";

    public CitySearchParameters(int key) {
        super(key, SearchTable.getStateTable());
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

    public void addState(StateSearchParameters toAdd) {
        this.state = toAdd;
    }

    public StateSearchParameters getState() {
        return this.state;
    }

    public void addCityName(String name) {
        this.addSearchParameter(CITY_NAME, name);
    }

    public boolean isSearchByState() {
        return this.hasParameter(this.getState());
    }

}
