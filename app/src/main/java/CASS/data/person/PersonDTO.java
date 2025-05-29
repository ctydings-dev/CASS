/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.person;

import CASS.data.BaseDTO;
import CASS.data.CreatedDTO;

/**
 *
 * @author ctydi
 */
public class PersonDTO extends CreatedDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String nickname;
    private char gender;
    private boolean is_current;
    private boolean is_active;
    private int addressID;

    private String updatedDate;
    private String birthday;

    public PersonDTO(String firstName, String middleName, String lastName, String nickname, char gender, boolean is_current, boolean is_active, int addressID, String createdDate, String updatedDate, String birthday, int key) {
        super(key, createdDate);
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.gender = gender;
        this.is_current = is_current;
        this.is_active = is_active;
        this.addressID = addressID;

        this.updatedDate = updatedDate;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public char getGender() {
        return gender;
    }

    public boolean isIs_current() {
        return is_current;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public int getAddressID() {
        return addressID;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public String getBirthday() {
        return birthday;
    }

}
