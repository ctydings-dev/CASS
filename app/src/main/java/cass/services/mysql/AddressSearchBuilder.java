/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cass.services.mysql;

import cass.data.address.AddressSearchParameters;
import cass.data.address.CitySearchParameters;
import cass.data.address.CountrySearchParameters;
import cass.data.address.StateSearchParameters;
import java.util.List;

/**
 *
 * @author ctydi
 */
public class AddressSearchBuilder {

    public static String createQueryForCountry(CountrySearchParameters country) {

        SearchBuilder builder = new SearchBuilder(SearchTable.getCountryTable());

        builder.addSearhParameter(country);
        return builder.createQuery();
    }

    private static void addCountryToQuery(SearchBuilder builder, CountrySearchParameters country) {

        builder.addJoinParameter(SearchTable.getStateTable(), country);
    }

    public static String addStateToQuery(SearchBuilder builder, StateSearchParameters state) {

        if (state.isSearchByCountry()) {
            addCountryToQuery(builder, state.getCountry());
        }
        return null;
    }

    public static void addCityToQuery(SearchBuilder builder, CitySearchParameters city) {
        builder.addSearhParameter(city);
        if (city.isSearchByState()) {
            builder.addJoinParameter(SearchTable.getCityTable(), city.getState());
            addStateToQuery(builder, city.getState());
        }

    }

    public static String createQueryForState(StateSearchParameters state) {
        SearchBuilder builder = new SearchBuilder(SearchTable.getStateTable());
        builder.addSearhParameter(state);
        addStateToQuery(builder, state);
        return builder.createQuery();
    }

    public static String createQueryForCity(CitySearchParameters city) {
        SearchBuilder builder = new SearchBuilder(SearchTable.getCityTable());
        addCityToQuery(builder, city);
        return builder.createQuery();
    }

    public static String createQueryForAddress(AddressSearchParameters address) {
        SearchBuilder builder = new SearchBuilder(SearchTable.getAddressTable());
        builder.addSearhParameter(address);
        if (address.isSearchByCity()) {
            addCityToQuery(builder, address.getCity());
        }
        return builder.createQuery();
    }

}
