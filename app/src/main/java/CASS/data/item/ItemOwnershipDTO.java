/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.item;

import CASS.data.CreatedDTO;

/**
 *
 * @author ctydi
 */
public class ItemOwnershipDTO extends CreatedDTO{
    
    private Integer item;
    
    private Integer account;
    
    private Boolean isValid;

    public ItemOwnershipDTO(Integer key, Integer item, Integer account, Boolean isValid, String date) {
        super(key,date);
        this.item = item;
        this.account = account;
        this.isValid = isValid;
    }

    public Integer getItem() {
        return item;
    }

    public Integer getAccount() {
        return account;
    }

    public Boolean getIsValid() {
        return isValid;
    }
    
    
    
}
