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
public class TransactionDTO extends CreatedDTO{
    
 private Integer employee;
    
    private Integer item;
    
    private Boolean isValid;
    
    private Integer quantity;
    
    private Integer type;
    
    private Integer facility;
    
    
    public TransactionDTO(Integer key, Integer item, Integer emp, Integer qty, Integer type, boolean isValid, String date){
        super(key,date);
        this.item = item;
        this.employee = emp;
        this.quantity = qty;
         this.type = type;
        this.isValid = isValid;
        this.facility = 1;
        
    }
    
      public TransactionDTO( Integer item, Integer emp, Integer qty, Integer type, boolean isValid){
        super();
        this.item = item;
        this.employee = emp;
        this.quantity = qty;
       this.type = type;
        this.isValid = isValid;
        this.facility = 1; 
    }
      public TransactionDTO(Integer key){
        super(key);
     }
    
    

    public Integer getEmployee() {
        return employee;
    }

    public Integer getItem() {
        return item;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getType() {
        return type;
    }
    
    
  public Integer getFacility(){
      return this.facility;
  }
    
    
    
}
