/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services;

import CASS.data.BaseDTO;
import CASS.data.TypeDTO;
import CASS.data.item.ItemDTO;
import CASS.data.item.PriceDTO;
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

     public TransactionDTO addInventoryTransaction(TransactionDTO entry) throws ServiceError;
     
     
     public TransactionDTO getTransaction(BaseDTO key) throws ServiceError;

public int addInventoryTransactionNote(TransactionDTO entry, TypeDTO type, String note) throws ServiceError;

public void reverseTransaction(TransactionDTO toReverse, EmployeeDTO reverser) throws ServiceError;

public List<PriceDTO> getAllPrices(ItemDTO item) throws ServiceError;

public PriceDTO getPrice(BaseDTO key) throws ServiceError;

public List<PriceDTO> getActivePrices(ItemDTO item) throws ServiceError;


public PriceDTO getStandardPrice(ItemDTO item) throws ServiceError;

public PriceDTO getCurrentPrice(ItemDTO item) throws ServiceError;

public PriceDTO addItemPrice(PriceDTO toAdd) throws ServiceError;


public Integer getItemQty(ItemDTO item, BaseDTO facility) throws ServiceError;
}
