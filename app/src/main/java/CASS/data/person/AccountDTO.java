/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.person;

import CASS.data.CreatedDTO;

/**
 *
 * @author ctydi
 */
public class AccountDTO extends CreatedDTO{
    
    
private String accountName;
    
    private String accountNumber;

    public Integer getAccountType() {
        return accountType;
    }
    

    
    private Integer personId;
    
    private String closedDate;

private Integer accountType;

    public AccountDTO(String accountName, String accountNumber, Integer personId, String closedDate, Integer accountType, int key, String createdDate) {
        super(key, createdDate);
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.personId = personId;
        this.closedDate = closedDate;
        this.accountType = accountType;
    }

    public AccountDTO(String accountName, String accountNumber, Integer personId, String closedDate, Integer accountType, int key) {
        super(key);
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.personId = personId;
        this.closedDate = closedDate;
        this.accountType = accountType;
    }

    
      public AccountDTO(String accountName, String accountNumber, Integer personId, Integer accountType, int key, String createdDate) {
        super(key, createdDate);
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.personId = personId;
 
        this.accountType = accountType;
    }

    public AccountDTO(String accountName, String accountNumber, Integer personId, Integer type) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.personId = personId;
        this.accountType =type ;
    }
    
    
    public AccountDTO(Integer key){
        super(key);
    }
    
    
 

    public String getAccountNumber() {
        return accountNumber;
    }

  
    public Integer getPersonId() {
        return personId;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public String getAccountName() {
        return accountName;
    }
    
    
    
    
}
