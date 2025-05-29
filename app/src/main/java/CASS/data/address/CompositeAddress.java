/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.address;

/**
 *
 * @author ctydi
 */
public class CompositeAddress extends CompositeCity {

    private AddressDTO address;

    public CompositeAddress(AddressDTO address, CityDTO city, StateDTO state, CountryDTO country) {
        super(city, state, country);
        this.address = address;

    }

    public CompositeAddress(AddressDTO address, CompositeCity city) {
        super(city.getCity(), city);
        this.address = address;

    }

    public AddressDTO getAddress() {
        return address;
    }

}
