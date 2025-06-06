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
public class CityDTO extends BaseDTO {

    private String cityName;

    private Integer stateID;

    public CityDTO(String cityName, Integer stateID, Integer key) {
        super(key);
        this.cityName = cityName;
        this.stateID = stateID;
    }
    
        public CityDTO(String cityName, Integer stateID) {
        super();
        this.cityName = cityName;
        this.stateID = stateID;
    }
    

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getStateID() {
        return stateID;
    }

    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }

}
