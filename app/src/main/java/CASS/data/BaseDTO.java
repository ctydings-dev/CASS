/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data;

/**
 *
 * @author ctydi
 */
public class BaseDTO {

    private int key;

    public BaseDTO(int key) {
        this.key = key;
    }

    public BaseDTO() {

    }

    public void setKey(int toSet) {
        this.key = toSet;
    }

    public int getKey() {
        return this.key;
    }

}
