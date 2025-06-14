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
    
    private String name;
    

    private Integer invoiceType;
    

    private Integer employeeID;
    
    private String finishedDate;

    public Integer getAccountID() {
        return accountID;
    }

    public Integer getEmployeeID() {
        return employeeID;
    }

    public InvoiceDTO(String name,Integer accountID, Integer invoiceType, Integer employeeID, String createdDate) {
        super(createdDate);
        this.accountID = accountID;
        this.invoiceType = invoiceType;
        this.employeeID = employeeID;
        this.name = name;
    }

    public InvoiceDTO(String name,Integer accountID, Integer employeeID, Integer invoiceType ) {
        this.accountID = accountID;
        this.invoiceType = invoiceType;
        this.employeeID = employeeID;
        this.name = name;
    }

    public InvoiceDTO(String name, Integer accountID, Integer invoiceType, Integer employeeID, String finishedDate, int key, String createdDate) {
        super(key, createdDate);
        this.accountID = accountID;
        this.invoiceType = invoiceType;
        this.employeeID = employeeID;
        this.finishedDate = finishedDate;
        this.name = name;
    }

    public InvoiceDTO(String name,Integer accountID, Integer invoiceType, Integer employeeID, String finishedDate, String createdDate) {
        super(createdDate);
        this.accountID = accountID;
        this.invoiceType = invoiceType;
        this.employeeID = employeeID;
        this.finishedDate = finishedDate;
        this.name = name;
    }
    
    
    public String getFinishedDate(){
        return this.finishedDate;
    }
    
    public boolean hasFinishedDate(){
        return this.getFinishedDate() != null;
    }

    public String getName() {
        return name;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }
    
    
}
