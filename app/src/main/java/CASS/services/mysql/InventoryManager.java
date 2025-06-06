/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.data.BaseDTO;
import CASS.data.TypeDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.EmployeeDTO;
import CASS.data.person.PersonDTO;
import CASS.services.ExtendedItemService;
import CASS.services.ItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author ctydi
 */
public class InventoryManager {

    ExtendedItemService svc;

    PersonService prsnSvc;

    public InventoryManager(ExtendedItemService svc, PersonService prsnSvc) {
        this.svc = svc;
        this.prsnSvc = prsnSvc;
    }

    protected ExtendedItemService getItemService() {
        return this.svc;

    }

    protected PersonService getPersonService() {
        return this.prsnSvc;
    }


    
    public TransactionDTO updateInventory(TransactionDTO entry, boolean reverse) throws SQLException, ServiceError {

        if (this.getItemService().inventoryContains(entry.getItem(), entry.getFacility()) == false) {
            this.getItemService().addInventory(entry);
        }

        int multi = this.getItemService().getMultiplyer(entry.getType());
        int qty = entry.getQuantity() * multi;
        if (reverse) {
            qty = qty * -1;
        }

        this.getItemService().updateInventoryItem(entry, qty);
           return this.getItemService().getLatestTransaction();

    }

 

    public void reverseTransaction(TransactionDTO toReverse, EmployeeDTO reverser) throws SQLException, ServiceError {

        this.addTransactionReversalNote(toReverse, reverser);
        this.getItemService().reverseTransactionTable(toReverse, reverser);
        
        this.updateInventory(toReverse, true);
      

    }

    private void addTransactionReversalNote(TransactionDTO toReverse, EmployeeDTO reverser) throws ServiceError, SQLException {

        TypeDTO type = new TypeDTO("INVENTORY");

        reverser = this.getPersonService().getEmployee(reverser);
        PersonDTO person = this.getPersonService().getPerson(new BaseDTO(reverser.getPersonID()));

        String note = "TRANSACTION REVERSED BY " + person.getFirstName() + " " + person.getLastName();

        note = note + " (" + reverser.getEmployeeCode() + ")";
        note = note + " AT " + new Date(System.currentTimeMillis()) + ".";

        this.getItemService().addInventoryTransactionNote(toReverse, type, note);
        
    }

 
   

 
    

}
