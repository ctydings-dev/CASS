/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cass.data.address;

import cass.data.BaseDTO;

/**
 *
 * @author ctydi
 */
public class StateDTO extends BaseDTO {

    private String stateName;

    private String abbreviation;

    private int countryID;

    public StateDTO(String name, String abbreviation, int countryID, int key) {
        super(key);
        this.stateName = name;
        this.abbreviation = abbreviation;
        this.countryID = countryID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String name) {
        this.stateName = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

}
