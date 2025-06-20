/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services;

import CASS.data.BaseDTO;
import CASS.data.TypeDTO;
import CASS.data.invoice.InvoiceDTO;
import CASS.data.item.InventoryItemDTO;
import CASS.data.item.ItemDTO;
import CASS.data.item.ItemOwnershipDTO;
import CASS.data.item.PriceDTO;
import CASS.data.item.SerializedItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.AccountDTO;
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
    
    public ItemDTO getItemByAlias(String code) throws ServiceError;
    
    
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

public SerializedItemDTO addSerializedItem(ItemDTO item, String SerialNumber, boolean forSale) throws ServiceError;

public SerializedItemDTO getSerializedItem(SerializedItemDTO key) throws ServiceError;

public InventoryItemDTO [] getInventory(BaseDTO facility) throws ServiceError;


public void addSerializedItemToInventory(SerializedItemDTO toAdd, TransactionDTO entry) throws ServiceError;

public void removeSerializedItemFromInventory(SerializedItemDTO toRemove) throws ServiceError;

public SerializedItemDTO [] getAllSerializedItems() throws ServiceError;

public SerializedItemDTO [] getSerializedItems(ItemDTO item, BaseDTO facility) throws ServiceError;

public void removeSerializedItem(SerializedItemDTO toRemove) throws ServiceError;


public void addSerializedItemNote(SerializedItemDTO item,EmployeeDTO employee, TypeDTO type, String note) throws ServiceError;

public void setSerializedItemOwnership(SerializedItemDTO item,  AccountDTO owner) throws ServiceError;

public void clearSerializedItemOwnership(SerializedItemDTO item) throws ServiceError;

public void markItemAsInvalid(ItemDTO toMark) throws ServiceError;


public AccountDTO getItemOwner(SerializedItemDTO toCheck) throws ServiceError;

public ItemOwnershipDTO [] getAllOwnersForItem(SerializedItemDTO toGet) throws ServiceError;

public SerializedItemDTO getSerializedItem(ItemDTO item, String sn) throws ServiceError;

}
