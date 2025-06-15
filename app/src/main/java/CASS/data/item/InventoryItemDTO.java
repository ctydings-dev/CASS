/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.item;

/**
 *
 * @author ctydi
 */
public class InventoryItemDTO extends ItemDTO{

    private int qty;
    public InventoryItemDTO(Integer key, String name, String alias, Integer type, Integer company, boolean isForSale, boolean isSerialized, Integer qty) {
        super(key, name, alias, type, company, isForSale, isSerialized);
    this.qty = qty;
    }
    
       public InventoryItemDTO(ItemDTO item, Integer qty){
super(item.getKey(), item.getItemName(), item.getItemAlias(), item.getItemType(), item.getCompany(), item.isForSale(), item.isSerialized());
    this.qty = qty;
    }
    
    public Integer getQuantity(){
        return this.qty;
    }
    
}



