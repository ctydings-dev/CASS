/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cass.services;

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
import java.util.List;

/**
 *
 * @author ctydi
 */
public interface AddressService {

    public CountryDTO getCountry(BaseDTO key) throws ServiceError;

    public CountryDTO getCountry(CountryDTO toGet) throws ServiceError;

    public CountryDTO searchCountry(CountrySearchParameters param) throws ServiceError;

    public List<CountryDTO> searchCountries(CountrySearchParameters param) throws ServiceError;

    public List<CountryDTO> getCountries() throws ServiceError;

    public StateDTO getState(BaseDTO key) throws ServiceError;

    public StateDTO getState(StateDTO key) throws ServiceError;

    public StateDTO searchState(StateSearchParameters param) throws ServiceError;

    ;

    public List<StateDTO> searchStates(StateSearchParameters param) throws ServiceError;

    public List<StateDTO> getStates() throws ServiceError;

    public CityDTO getCity(BaseDTO key) throws ServiceError;

    public CityDTO getCity(CityDTO key) throws ServiceError;

    public List<CityDTO> getCities() throws ServiceError;

    public CityDTO searchCity(CitySearchParameters param) throws ServiceError;

    public List<CityDTO> searchCities(CitySearchParameters param) throws ServiceError;

    public AddressDTO getAddress(BaseDTO key) throws ServiceError;

    public AddressDTO getAddress(AddressDTO key) throws ServiceError;

    public CompositeAddress getFullAddress(BaseDTO key) throws ServiceError;

    public List<AddressDTO> getAddressses() throws ServiceError;

    public AddressDTO searchAddress(AddressSearchParameters param) throws ServiceError;

    public List<AddressDTO> searchAddresses(AddressSearchParameters param) throws ServiceError;

    public int addCountry(CountryDTO toAdd) throws ServiceError;

    public int addState(StateDTO toAdd) throws ServiceError;

    public int addCity(CityDTO toAdd) throws ServiceError;

    public int addAddress(AddressDTO toAdd) throws ServiceError;

    public int addAddress(AddressDTO addr, CityDTO city, StateDTO state, CountryDTO con) throws ServiceError;

}
