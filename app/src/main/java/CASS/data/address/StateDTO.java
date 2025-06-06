/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.address;

import CASS.data.BaseDTO;

/**
 *
 * @author ctydi
 */
public class StateDTO extends BaseDTO {

    private String stateName;

    private String abbreviation;

    private Integer countryID;

    public StateDTO(String name, String abbreviation, Integer countryID, Integer key) {
        super(key);
        this.stateName = name;
        this.abbreviation = abbreviation;
        this.countryID = countryID;
    }
      public StateDTO(String name, String abbreviation, Integer countryID) {
        super();
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

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

}
