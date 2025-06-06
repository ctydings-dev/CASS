/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.address;

import CASS.data.BaseDTO;
import CASS.data.CreatedDTO;

/**
 *
 * @author ctydi
 */
public class AddressDTO extends CreatedDTO {

    private String street;

    private String street2;

    private String postCode;

    private Integer cityID;

    public AddressDTO(String street, String street2, String postCode, int cityID, int key) {
        super(key);
        this.street = street;
        this.street2 = street2;
        this.postCode = postCode;
        this.cityID = cityID;

    }

    public AddressDTO(String street, String postCode, Integer cityID, Integer key) {
        super(key);
        this.street = street;
        this.postCode = postCode;
        this.cityID = cityID;
    }
    
    
        public AddressDTO(String street, String street2, String postCode, Integer cityID) {
        super();
        this.street = street;
        this.street2 = street2;
        this.postCode = postCode;
        this.cityID = cityID;

    }

    public AddressDTO(String street, String postCode, Integer cityID) {
        super();
        this.street = street;
        this.postCode = postCode;
        this.cityID = cityID;
    }
    

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }
}
