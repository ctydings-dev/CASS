/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.item;

import CASS.data.BaseDTO;
import CASS.data.CreatedDTO;

/**
 *
 * @author ctydi
 */
public class ItemDTO extends CreatedDTO{
    
 
     
     protected String itemName;
             
             protected String itemAlias;
             protected Integer itemType;
             protected boolean isSerialized;
             protected boolean isForSale;
             protected Integer company;
             
             
    
    
    
    public ItemDTO(Integer key){
        super(key);
    }
 public ItemDTO(){
        super();
    }
    
    public ItemDTO(Integer key, String name, String alias, Integer type, Integer company, boolean isForSale, boolean isSerialized){
        super(key);
        this.itemName = name;
        this.itemAlias = alias;
        this.itemType = type;
        this.company = company;
        this.isForSale = isForSale == true;
        this.isSerialized = isSerialized;
        
    }
    
    
    
    public boolean isForSale(){
        return this.isForSale == true;
    }
    public String getItemName() {
        return itemName;
    }

    public String getItemAlias() {
        return itemAlias;
    }

    public Integer getItemType() {
        return itemType;
    }

    public Integer getCompany() {
        return company;
    }
    
    
    
    
    
    
    
    
}
