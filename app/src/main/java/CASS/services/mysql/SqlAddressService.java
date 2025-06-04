/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.data.BaseDTO;
import CASS.data.address.AddressDTO;
import CASS.data.address.AddressSearchParameters;
import CASS.data.address.CityDTO;
import CASS.data.address.CitySearchParameters;
import CASS.data.address.CompositeAddress;
import CASS.data.address.CompositeCity;
import CASS.data.address.CompositeState;
import CASS.data.address.CountryDTO;
import CASS.data.address.CountrySearchParameters;
import CASS.data.address.StateDTO;
import CASS.data.address.StateSearchParameters;
import CASS.services.AddressService;
import CASS.services.ServiceError;
import CASS.util.DataObjectGenerator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
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

    private StateDTO createStateFromResultSet(ResultSet rs) throws SQLException {

        int stateId = this.getService().getIntValue(rs, "state_id");
        String name = rs.getString("state_name");
        String abbv = rs.getString("state_abbv");
        int countryId = this.getService().getIntValue(rs, "country_id");
        return new StateDTO(name, abbv, countryId, stateId);

    }

    private CityDTO createCityFromResultSet(ResultSet rs) throws SQLException {

        int cityId = this.getService().getIntValue(rs, "city_id");
        String name = rs.getString("city_name");
        int stateId = this.getService().getIntValue(rs, "state_id");
        return new CityDTO(name, stateId, cityId);

    }

    private AddressDTO createAddressFromResultSet(ResultSet rs) throws SQLException {

        int id = this.getService().getIntValue(rs, "address_id");
        int city = this.getService().getIntValue(rs, "city_id");
        String street = rs.getString("street");
        String street2 = rs.getString("street_2");
        String postal = rs.getString("post_code");
        AddressDTO ret = new AddressDTO(street, street2, postal, city, id);
        return ret;
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
    public List<StateDTO> getStates() throws ServiceError {
        try {
            String query = "SELECT * FROM states;";

            ResultSet resultSet = this.getService().executeQuery(query);
            List<StateDTO> ret = DataObjectGenerator.createList();

            while (resultSet.next()) {
                StateDTO toAdd = createStateFromResultSet(resultSet);
                ret.add(toAdd);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError();
        }
    }

    @Override
    public CityDTO getCity(BaseDTO key) throws ServiceError {
        
     try {
            String query = "SELECT * FROM cities WHERE city_id = " + key.getKey() + ";";

            ResultSet resultSet = this.getService().executeQuery(query);
            resultSet.next();

            return createCityFromResultSet(resultSet);
        } catch (SQLException ex) {
            throw new ServiceError();
        }
    
    }

    @Override
    public List<CityDTO> getCities() throws ServiceError {
        try {
            String query = "SELECT * FROM states;";

            ResultSet resultSet = this.getService().executeQuery(query);
            List<CityDTO> ret = DataObjectGenerator.createList();

            while (resultSet.next()) {
                CityDTO toAdd = createCityFromResultSet(resultSet);
                ret.add(toAdd);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError();
        }
    }

    @Override
    public AddressDTO getAddress(BaseDTO key) throws ServiceError {
        try {
            String query = "SELECT * FROM addresses WHERE address_id = " + key.getKey() + ";";

            ResultSet resultSet = this.getService().executeQuery(query);
            resultSet.next();

            return createAddressFromResultSet(resultSet);
        } catch (SQLException ex) {
            throw new ServiceError();
        }

    }

    @Override
    public List<AddressDTO> getAddressses() throws ServiceError {
     try {
            String query = "SELECT * FROM addresses;";

            ResultSet resultSet = this.getService().executeQuery(query);
            List<AddressDTO> ret = DataObjectGenerator.createList();

            while (resultSet.next()) {
                AddressDTO toAdd = createAddressFromResultSet(resultSet);
                ret.add(toAdd);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError();
        }
    
    
    }

    @Override
    public CompositeAddress getFullAddress(BaseDTO key) throws ServiceError {
     
    AddressDTO adr = this.getAddress(key);
    key = new BaseDTO(adr.getCityID());
    CityDTO cty = this.getCity(key);
    key = new BaseDTO(cty.getStateID());
    StateDTO st = this.getState(key);
    key = new BaseDTO(st.getCountryID());
    CountryDTO con = this.getCountry(key);
    
  return new CompositeAddress(adr, cty,st,con);
    
    
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
String query = AddressSearchBuilder.createQueryForState(param);
    
     try {
                    ResultSet resultSet = this.getService().executeQuery(query);
            List<StateDTO> ret = DataObjectGenerator.createList();

            while (resultSet.next()) {
                StateDTO toAdd = createStateFromResultSet(resultSet);
                ret.add(toAdd);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError();
        }
    
    }

    @Override
    public CityDTO searchCity(CitySearchParameters param) throws ServiceError {
        List<CityDTO> results = this.searchCities(param);
        return this.checkForSingular(results);

    }

    @Override
    public List<CityDTO> searchCities(CitySearchParameters param) throws ServiceError {
         try {
            String query =  AddressSearchBuilder.createQueryForCity(param);

            ResultSet resultSet = this.getService().executeQuery(query);
            List<CityDTO> ret = DataObjectGenerator.createList();

            while (resultSet.next()) {
                CityDTO toAdd = createCityFromResultSet(resultSet);
                ret.add(toAdd);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError();
        }
    }
    @Override
    public AddressDTO searchAddress(AddressSearchParameters param) throws ServiceError {
        List<AddressDTO> results = this.searchAddresses(param);
        return this.checkForSingular(results);
    }

    @Override
    public List<AddressDTO> searchAddresses(AddressSearchParameters param) throws ServiceError {
      try {
            String query =  AddressSearchBuilder.createQueryForAddress(param);

            ResultSet resultSet = this.getService().executeQuery(query);
            List<AddressDTO> ret = DataObjectGenerator.createList();

            while (resultSet.next()) {
                AddressDTO toAdd = createAddressFromResultSet(resultSet);
                ret.add(toAdd);
            }
            return ret;
        } catch (SQLException ex) {
            throw new ServiceError();
        }
    }

    @Override
    public int addCountry(CountryDTO toAdd) throws ServiceError {

        String statement = "INSERT INTO countries(country_name, country_abbv) VALUES ('";
        statement += toAdd.getCountryName().trim().toUpperCase() + "','" + toAdd.getAbbreviation().trim().toUpperCase() + "');";

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
        statement += toAdd.getStateName().trim().toUpperCase() + "','" + toAdd.getAbbreviation().trim().toUpperCase() + "', " + toAdd.getCountryID() + ");";

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
        statement += toAdd.getCityName().trim().toUpperCase() + "', " + toAdd.getStateID() + ");";

        try {
            this.getService().executeStatement(statement);

            CitySearchParameters params = new CitySearchParameters();
            params.setState(new StateSearchParameters(toAdd.getStateID()));
params.addCityName(toAdd.getCityName());
            return this.searchCity(params).getKey();

        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }
    }

    @Override
    public int addAddress(AddressDTO toAdd) throws ServiceError {

        String statement = "INSERT INTO addresses(street,street_2, post_code, city_id) VALUES ('";
        statement += toAdd.getStreet().trim().toUpperCase() + "' , '" + toAdd.getStreet2().trim().toUpperCase() + "','"
                + toAdd.getPostCode().trim().toUpperCase() + "', " + toAdd.getCityID() + ");";

        try {
            this.getService().executeStatement(statement);
            AddressSearchParameters adr = new AddressSearchParameters();
            adr.setCity(toAdd.getCityID());
            adr.setPostCode(toAdd.getPostCode());
            adr.setStreet(toAdd.getStreet());
            adr.setStreet2(toAdd.getStreet2());
            

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
