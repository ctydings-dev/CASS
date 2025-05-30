/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cass.data.address;

import cass.data.BaseDTO;
import cass.data.CreatedDTO;

/**
 *
 * @author ctydi
 */
public class AddressDTO extends CreatedDTO {

    private String street;

    private String street2;

    private String postCode;

    private int cityID;

    public AddressDTO(String street, String street2, String postCode, int cityID, int key) {
        super(key);
        this.street = street;
        this.street2 = street2;
        this.postCode = postCode;
        this.cityID = cityID;

    }

    public AddressDTO(String street, String postCode, int cityID, int key) {
        super(key);
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

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }
}
