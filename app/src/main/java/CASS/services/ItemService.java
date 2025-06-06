/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services;

import CASS.data.BaseDTO;
import CASS.data.TypeDTO;
import CASS.data.item.ItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.EmployeeDTO;
import CASS.search.ItemSearchParameters;
import java.util.List;

/**
 *
 * @author ctydi
 */
public interface ItemService {
    
    public List<ItemDTO> getAllItems() throws ServiceError;
    
    
    public ItemDTO getItem(BaseDTO key) throws ServiceError;
    
    
    public List<ItemDTO> searchItems(ItemSearchParameters params) throws ServiceError;


    public int addItem(ItemDTO toAdd) throws ServiceError;

     public int addInventoryTransaction(TransactionDTO entry) throws ServiceError;
}
