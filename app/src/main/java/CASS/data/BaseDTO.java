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
    
    private boolean keySet;

    public BaseDTO(int key) {
        this.key = key;
        this.keySet = true;
    }

    public BaseDTO() {
this.keySet = false;
    }

    public void setKey(int toSet) {
        this.key = toSet;
        this.keySet = true;
    }

    public int getKey() {
        return this.key;
    }
    
    public boolean hasKeySet(){
        return this.keySet == true;
    }
    

}
