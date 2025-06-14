/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import CASS.data.item.SerializedItemDTO;
import CASS.manager.InventoryManager;
import CASS.manager.InvoiceManager;
import CASS.services.ServiceError;
import java.util.List;

/**
 *
 * @author ctydi
 */
public class InvoiceDataSeeder {
    
    
    
    public static void seedInvoiceData(InvoiceManager mngr, InventoryManager inven) throws ServiceError{
        
        seedAbyssSalesData(mngr,inven);
        
    }
    
    
    private static void seedAbyssSalesData(InvoiceManager mngr, InventoryManager inv) throws ServiceError{
        
    List<SerializedItemDTO> items =    inv.getSerializedItemsForSale(ItemDataSeed.ABYSS);
    Integer invoice = createInvoice(mngr, PersonDataSeeder.ME_ACC, PersonDataSeeder.JD_EMP);    
    SerializedItemDTO toSell = items.get(0);

    mngr.sellSerializedItem(toSell.getKey(),ItemDataSeed.ABYSS_PRICE, invoice);
    
    
        
        
    }
    
    
    private static Integer createInvoice(InvoiceManager mngr, int account, int emp) throws ServiceError{
        
        
       return mngr.createSalesInvoice(account, emp).getKey();
        
        
    }
    
    
}
