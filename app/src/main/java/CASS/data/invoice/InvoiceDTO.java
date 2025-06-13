/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.invoice;

import CASS.data.CreatedDTO;

/**
 *
 * @author ctydi
 */
public class InvoiceDTO extends CreatedDTO{
 
    private Integer accountID;
    

    private Integer invoiceType;
    

    private Integer employeeID;

    public Integer getAccountID() {
        return accountID;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public InvoiceDTO(Integer accountID, Integer invoiceType, Integer employeeID, String createdDate) {
        super(createdDate);
        this.accountID = accountID;
        this.invoiceType = invoiceType;
        this.employeeID = employeeID;
    }

    public InvoiceDTO(Integer accountID, Integer employeeID, Integer invoiceType ) {
        this.accountID = accountID;
        this.invoiceType = invoiceType;
        this.employeeID = employeeID;
    }
    
    
    
}
