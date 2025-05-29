/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.address;

/**
 *
 * @author ctydi
 */
public class CompositeState {

    private StateDTO state;

    private CountryDTO country;

    public CompositeState(StateDTO state, CountryDTO country) {
        this.state = state;
        this.country = country;
    }

    public StateDTO getState() {
        return state;
    }

    public void setState(StateDTO state) {
        this.state = state;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

}
