/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import java.util.Date;

/**
 *
 * @author ctydi
 */
public class SearchValue {

    private final String value;

    private boolean isExact;

    private boolean isString;

    public SearchValue(String value) {
        this.isString = true;
        this.value = value;
        this.isExact = true;
    }

    public SearchValue(int value) {
        this.isString = false;
        this.value = "" + value;
        this.isExact = true;
    }
    
    
       public SearchValue(boolean value) {
        this.isString = false;
        this.value = "" + value;
        this.isExact = true;
    }

    

    public SearchValue(Date date) {
        this.isString = true;
        this.value = date.toString();
        this.isExact = true;
    }

    public String getValue() {
        return value;
    }

    public boolean isString() {
        return isString;
    }

    public boolean isExact() {
        return this.isExact;
    }

    public void setIsExact(boolean toSet) {
        this.isExact = toSet;
    }

    public void setIsString(boolean toSet) {
        this.isString = toSet;
    }

    public String getComparator() {

        if (this.isString()) {
            if (this.isExact()) {
                return " = ";
            }

            return " LIKE ";

        }
        return " = ";
    }

    private String format(String in){
        return in.trim().toUpperCase();
    }
    
    public String getSqlValue( boolean considerExact) {
        if (this.isString()) {

            if (this.isExact() || considerExact == false) {

                return "'" + this.format(this.getValue()) + "'";
            }

            return "'%" + this.format(this.getValue()) + "%'";

        }
        return this.getValue();
    }
    
    
    
    
      public String getSqlValue() {
       return getSqlValue(true);
    }

}
