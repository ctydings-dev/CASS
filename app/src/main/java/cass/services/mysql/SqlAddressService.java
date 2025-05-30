/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cass.services.mysql;

import cass.data.BaseDTO;
import cass.data.address.AddressDTO;
import cass.data.address.AddressSearchParameters;
import cass.data.address.CityDTO;
import cass.data.address.CitySearchParameters;
import cass.data.address.CompositeAddress;
import cass.data.address.CountryDTO;
import cass.data.address.CountrySearchParameters;
import cass.data.address.StateDTO;
import cass.data.address.StateSearchParameters;
import cass.services.AddressService;
import cass.services.ServiceError;
import cass.util.DataObjectGenerator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctydi
 */
public class SqlAddressService implements AddressService {

    private SqlService service;

    public SqlAddressService(SqlService service) {
        this.service = service;
    }

    private SqlService getService() {
        return this.service;
    }

    private CountryDTO createCountryFromResultSet(ResultSet rs) throws SQLException {

        int ID = this.getService().getIntValue(rs, "country_id");
        String name = rs.getString("country_name");
        String abbv = rs.getString("country_abbv");

        return new CountryDTO(name, abbv, ID);

    }

    private <BaseDTO> BaseDTO checkForSingular(List<BaseDTO> toCheck) throws ServiceError {
        if (toCheck.size() > 1) {
            throw new ServiceError("MULTIPLE RESULTS FOUND");
        }

        if (toCheck.size() < 1) {
            return null;
        }

        return toCheck.get(0);
    }

    @Override
    public CountryDTO getCountry(BaseDTO key) throws ServiceError {

        CountrySearchParameters con = new CountrySearchParameters(key.getKey());

        List<CountryDTO> results = this.searchCountries(con);
        if (results.size() < 1) {
            throw new ServiceError("NO COUNTRY WITH ID: " + key + "FOUND!");
        }
        return results.get(0);
    }

    @Override
    public List<CountryDTO> getCountries() throws ServiceError {
        try {
            String query = "SELECT * FROM countries;";

            ResultSet resultSet = this.getService().executeQuery(query);
            List<CountryDTO> ret = DataObjectGenerator.createList();

            while (resultSet.next()) {
                CountryDTO toAdd = createCountryFromResultSet(resultSet);
                ret.add(toAdd);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError();
        }

    }

    @Override
    public StateDTO getState(BaseDTO key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<StateDTO> getStates() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CityDTO getCity(BaseDTO key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<CityDTO> getCities() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AddressDTO getAddress(BaseDTO key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<AddressDTO> getAddressses() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CompositeAddress getFullAddress(BaseDTO key) throws ServiceError {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CountryDTO searchCountry(CountrySearchParameters param) throws ServiceError {
        List<CountryDTO> results = this.searchCountries(param);
        return this.checkForSingular(results);

    }

    @Override
    public List<CountryDTO> searchCountries(CountrySearchParameters param) throws ServiceError {
        List<CountryDTO> ret = DataObjectGenerator.createList();

        try {
            String query = AddressSearchBuilder.createQueryForCountry(param);
            ResultSet resultSet = this.getService().executeQuery(query);

            while (resultSet.next()) {
                ret.add(createCountryFromResultSet(resultSet));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServiceError(ex.getLocalizedMessage());
        }

        return ret;
    }

    @Override
    public StateDTO searchState(StateSearchParameters param) throws ServiceError {
        List<StateDTO> results = this.searchStates(param);
        return this.checkForSingular(results);
    }

    @Override
    public List<StateDTO> searchStates(StateSearchParameters param) throws ServiceError {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CityDTO searchCity(CitySearchParameters param) throws ServiceError {
        List<CityDTO> results = this.searchCities(param);
        return this.checkForSingular(results);

    }

    @Override
    public List<CityDTO> searchCities(CitySearchParameters param) throws ServiceError {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AddressDTO searchAddress(AddressSearchParameters param) throws ServiceError {
        List<AddressDTO> results = this.searchAddresses(param);
        return this.checkForSingular(results);
    }

    @Override
    public List<AddressDTO> searchAddresses(AddressSearchParameters param) throws ServiceError {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }

    @Override
    public int addCountry(CountryDTO toAdd) throws ServiceError {

        String statement = "INSERT INTO countries(country_name, country_abbv) VALUES ('";
        statement += toAdd.getCountryName() + "','" + toAdd.getAbbreviation() + "');";

        try {
            CountryDTO toCheck = this.getCountry(toAdd);

            if (toCheck != null) {
                return toCheck.getKey();
            }

            this.getService().executeStatement(statement);
            CountrySearchParameters params = new CountrySearchParameters(toAdd);
            return this.searchCountry(params).getKey();
        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }

    }

    @Override
    public int addState(StateDTO toAdd) throws ServiceError {
        String statement = "INSERT INTO states(state_name, state_abbv, country_id) VALUES ('";
        statement += toAdd.getStateName() + "','" + toAdd.getAbbreviation() + "', " + toAdd.getCountryID() + ");";

        try {
            this.getService().executeStatement(statement);

            StateSearchParameters params = new StateSearchParameters(toAdd);

            return this.searchState(params).getKey();

        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }

    }

    @Override
    public int addCity(CityDTO toAdd) throws ServiceError {
        String statement = "INSERT INTO cities(city_name, state_id) VALUES ('";
        statement += toAdd.getCityName() + "', " + toAdd.getStateID() + ");";

        try {
            this.getService().executeStatement(statement);

            CitySearchParameters params = new CitySearchParameters(toAdd);

            return this.searchCity(params).getKey();

        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }
    }

    @Override
    public int addAddress(AddressDTO toAdd) throws ServiceError {

        String statement = "INSERT INTO addresses(street,street_2, post_code, city_id) VALUES ('";
        statement += toAdd.getStreet() + "' , '" + toAdd.getStreet2() + " ',' "
                + toAdd.getPostCode() + "', " + toAdd.getCityID() + ");";

        try {
            this.getService().executeStatement(statement);
            AddressSearchParameters adr = new AddressSearchParameters(toAdd);

            return this.searchAddress(adr).getKey();

        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }
    }

    @Override
    public int addAddress(AddressDTO addr, CityDTO city, StateDTO state, CountryDTO con) throws ServiceError {

        int country = this.addCountry(con);

        state.setCountryID(country);

        int stateId = this.addState(state);

        city.setStateID(stateId);

        int cityId = this.addCity(city);

        addr.setCityID(cityId);

        return this.addAddress(addr);

    }

    @Override
    public CountryDTO getCountry(CountryDTO toGet) throws ServiceError {
        CountrySearchParameters con = new CountrySearchParameters(toGet);

        return this.searchCountry(con);
    }

    @Override
    public StateDTO getState(StateDTO toGet) throws ServiceError {
        StateSearchParameters params = new StateSearchParameters(toGet);
        return this.searchState(params);
    }

    @Override
    public CityDTO getCity(CityDTO toGet) throws ServiceError {
        CitySearchParameters params = new CitySearchParameters(toGet);
        return this.searchCity(params);
    }

    @Override
    public AddressDTO getAddress(AddressDTO key) throws ServiceError {
        AddressSearchParameters param = new AddressSearchParameters(key);
        return this.searchAddress(param);
    }

}
