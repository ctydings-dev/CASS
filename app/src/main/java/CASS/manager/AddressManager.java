/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.manager;

import CASS.data.BaseDTO;
import CASS.data.address.AddressDTO;
import CASS.data.address.CityDTO;
import CASS.data.address.CompositeAddress;
import CASS.data.address.CountryDTO;
import CASS.data.address.StateDTO;
import CASS.services.AddressService;
import CASS.services.ServiceError;
import CASS.util.DataObjectGenerator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ctydi
 */
public class AddressManager {

    private AddressService adr;

    private Map<Integer, CityDTO> cityCache;

    private Map<Integer, StateDTO> stateCache;

    private Map<Integer, CountryDTO> countryCache;

    public AddressManager(AddressService adr) {
        this.adr = adr;
        this.clearCache();
    }

    public AddressService getService() {
        return this.adr;
    }

    public List<AddressDTO> getAddresses() throws ServiceError {
        return this.getService().getAddressses();
    }

    public List<CompositeAddress> getFullAddress() throws ServiceError {
        List<AddressDTO> addrs = this.getService().getAddressses();

        List<CompositeAddress> ret = DataObjectGenerator.createList();

        for (AddressDTO entry : addrs) {
            CityDTO city = this.getCity(entry.getCityID());
            StateDTO state = this.getState(city.getStateID());
            CountryDTO con = this.getCountry(state.getCountryID());
            CompositeAddress toAdd = new CompositeAddress(entry, city, state, con);
            ret.add(toAdd);
        }

        return ret;
    }

    public CityDTO getCity(int id) throws ServiceError {

        if (this.cityCache.containsKey(id) == true) {
            return this.cityCache.get(id);
        }

        CityDTO toAdd = this.getService().getCity(new BaseDTO(id));

        this.cityCache.put(id, toAdd);

        return toAdd;

    }

    public StateDTO getState(int key) throws ServiceError {

        if (this.stateCache.containsKey(key) == true) {
            return this.stateCache.get(key);
        }

        StateDTO toAdd = this.getService().getState(new BaseDTO(key));

        this.stateCache.put(key, toAdd);

        return toAdd;
    }

    public CountryDTO getCountry(int key) throws ServiceError {

        if (this.countryCache.containsKey(key) == true) {
            return this.countryCache.get(key);
        }

        CountryDTO toAdd = this.getService().getCountry(new BaseDTO(key));

        this.countryCache.put(key, toAdd);
        return toAdd;

    }

    public AddressDTO getAddress(int id) throws ServiceError {

        return this.getService().getAddress(new BaseDTO(id));
    }

    public void clearCache() {
        this.cityCache = DataObjectGenerator.createMap();
        this.stateCache = DataObjectGenerator.createMap();
        this.countryCache = DataObjectGenerator.createMap();

    }

}
