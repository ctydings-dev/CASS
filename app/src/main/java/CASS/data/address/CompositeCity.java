/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.address;

/**
 *
 * @author ctydi
 */
public class CompositeCity extends CompositeState {

    private CityDTO city;

    public CompositeCity(CityDTO city, StateDTO state, CountryDTO country) {
        super(state, country);
        this.city = city;
    }

    public CompositeCity(CityDTO city, CompositeState state) {
        super(state.getState(), state.getCountry());
        this.city = city;
    }

    public CityDTO getCity() {
        return city;
    }

}
