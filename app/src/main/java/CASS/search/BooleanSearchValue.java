/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.search.SearchValue;

/**
 *
 * @author ctydi
 */
public class BooleanSearchValue extends SearchValue {
    
    public BooleanSearchValue(boolean value) {
        super(value + "");
    }
    
     public boolean isString() {
        return false;
    }
    
}
