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
public class CountryDTO extends BaseDTO {

    private String countryName;

    private String abbreviation;

    public CountryDTO(String countryName, String abbreviation, int key) {
        super(key);
        this.countryName = countryName;

        this.abbreviation = abbreviation;
    }

    public CountryDTO(String countryName, String abbreviation) {

        this.countryName = countryName;

        this.abbreviation = abbreviation;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

}
