/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.item;

import CASS.data.BaseDTO;

/**
 *
 * @author ctydi
 */
public class SerializedItemDTO extends BaseDTO {
    
    protected String serialNumber;
    
    protected Integer item;
    
    protected boolean isForSale;

    public SerializedItemDTO(String serialNumber, Integer item,boolean isForSale, int key) {
        super(key);
        this.serialNumber = serialNumber;
        this.item = item;
        this.isForSale =isForSale;
    }

     public SerializedItemDTO( int key) {
        super(key);
      }
    
    
    
    
    public SerializedItemDTO(String serialNumber, Integer item,boolean isForSale) {
        this.serialNumber = serialNumber;
        this.item = item;
        this.isForSale = isForSale;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Integer getItem() {
        return item;
    }
    
    
    public boolean isForSale(){
        return this.isForSale == true;
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
