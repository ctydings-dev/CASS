/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.search.AddressSearchBuilder;
import CASS.data.BaseDTO;
import CASS.data.BaseSearchParameter;
import CASS.data.address.AddressDTO;
import CASS.search.AddressSearchParameters;
import CASS.data.address.CityDTO;
import CASS.search.CitySearchParameters;
import CASS.data.address.CompositeAddress;
import CASS.data.address.CompositeCity;
import CASS.data.address.CompositeState;
import CASS.data.address.CountryDTO;
import CASS.search.CountrySearchParameters;
import CASS.data.address.StateDTO;
import CASS.search.StateSearchParameters;
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

        int ID = this.getService().getIntValue(rs, TABLE_COLUMNS.LOCATION.COUNTRY.ID);
        String name = rs.getString(TABLE_COLUMNS.LOCATION.COUNTRY.NAME);
        String abbv = rs.getString(TABLE_COLUMNS.LOCATION.COUNTRY.ABBV);

        return new CountryDTO(name, abbv, ID);

    }

    private StateDTO createStateFromResultSet(ResultSet rs) throws SQLException {

        int stateId = this.getService().getIntValue(rs, TABLE_COLUMNS.LOCATION.STATE.ID);
        String name = rs.getString(TABLE_COLUMNS.LOCATION.STATE.NAME);
        String abbv = rs.getString(TABLE_COLUMNS.LOCATION.STATE.ABBV);
        int countryId = this.getService().getIntValue(rs, TABLE_COLUMNS.LOCATION.STATE.COUNTRY);
        return new StateDTO(name, abbv, countryId, stateId);

    }

    private CityDTO createCityFromResultSet(ResultSet rs) throws SQLException {

        int cityId = this.getService().getIntValue(rs, TABLE_COLUMNS.LOCATION.CITY.ID);
        String name = rs.getString(TABLE_COLUMNS.LOCATION.CITY.NAME);
        int stateId = this.getService().getIntValue(rs, TABLE_COLUMNS.LOCATION.CITY.STATE);
        return new CityDTO(name, stateId, cityId);

    }

    private AddressDTO createAddressFromResultSet(ResultSet rs) throws SQLException {

        int id = this.getService().getIntValue(rs, TABLE_COLUMNS.LOCATION.ADDRESS.ID);
        int city = this.getService().getIntValue(rs, TABLE_COLUMNS.LOCATION.ADDRESS.CITY);
        String street = rs.getString(TABLE_COLUMNS.LOCATION.ADDRESS.STREET);
        String street2 = rs.getString(TABLE_COLUMNS.LOCATION.ADDRESS.STREET_2);
        String postal = rs.getString(TABLE_COLUMNS.LOCATION.ADDRESS.POST_CODE);
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
         ResultSet resultSet = this.getService().getAllForTable(TABLE_COLUMNS.LOCATION.COUNTRY.TABLE_NAME);
           
            
            
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
         ResultSet resultSet = this.getService().getAllForTable(TABLE_COLUMNS.LOCATION.STATE.TABLE_NAME);
           
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
                 ResultSet resultSet = this.getService().getForId(TABLE_COLUMNS.LOCATION.CITY.TABLE_NAME, TABLE_COLUMNS.LOCATION.CITY.ID, key.getKey());
           
            resultSet.next();

            return createCityFromResultSet(resultSet);
        } catch (SQLException ex) {
            throw new ServiceError();
        }
    
    }

    @Override
    public List<CityDTO> getCities() throws ServiceError {
        try {
          ResultSet resultSet = this.getService().getAllForTable(TABLE_COLUMNS.LOCATION.CITY.TABLE_NAME);
           
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
                 ResultSet resultSet = this.getService().getForId(TABLE_COLUMNS.LOCATION.ADDRESS.TABLE_NAME, TABLE_COLUMNS.LOCATION.ADDRESS.ID, key.getKey());
            resultSet.next();

            return createAddressFromResultSet(resultSet);
        } catch (SQLException ex) {
            throw new ServiceError();
        }

    }

    @Override
    public List<AddressDTO> getAddressses() throws ServiceError {
     try {
       ResultSet resultSet = this.getService().getAllForTable(TABLE_COLUMNS.LOCATION.ADDRESS.TABLE_NAME);
           
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

           try {
              BaseSearchParameter toInsert = new CountrySearchParameters(toAdd);
           
return            this.getService().insertAndGet(toInsert);
        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }

    }

    @Override
    public int addState(StateDTO toAdd) throws ServiceError {
       try {
           
           StateSearchParameters toInsert = new StateSearchParameters(toAdd);
           
return            this.getService().insertAndGet(toInsert);

            

        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }

    }

    @Override
    public int addCity(CityDTO toAdd) throws ServiceError {
       try {
              BaseSearchParameter toInsert = new CitySearchParameters(toAdd);
           
return            this.getService().insertAndGet(toInsert);
        } catch (SQLException ex) {
            throw new ServiceError(ex.getLocalizedMessage());
        }
    }

    @Override
    public int addAddress(AddressDTO toAdd) throws ServiceError {

      

             try {
              BaseSearchParameter toInsert = new AddressSearchParameters(toAdd);
           
return            this.getService().insertAndGet(toInsert);
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
