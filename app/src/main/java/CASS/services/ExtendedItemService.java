/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services;

import CASS.data.item.TransactionDTO;
import CASS.data.person.AccountDTO;
import CASS.data.person.EmployeeDTO;
import java.sql.SQLException;

/**
 *
 * @author ctydi
 */
public interface ExtendedItemService extends ItemService {
    
     void updateInventoryItem(TransactionDTO entry, int qty) throws SQLException, ServiceError;
     
     
        boolean inventoryContains(Integer item, Integer facility) throws SQLException, ServiceError;
       
        void addInventory(TransactionDTO entry) throws SQLException, ServiceError;
        
        int getMultiplyer(int id) throws SQLException, ServiceError;
 
     
        void reverseTransactionTable(TransactionDTO toReverse, EmployeeDTO reverser) throws SQLException, ServiceError;
        
        
        TransactionDTO getLatestTransaction()throws SQLException, ServiceError;
        
        
     





}
