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
public class CityDTO extends BaseDTO {

    private String cityName;

    private int stateID;

    public CityDTO(String cityName, int stateID, int key) {
        super(key);
        this.cityName = cityName;
        this.stateID = stateID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

}
