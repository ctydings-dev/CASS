/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.person;

import CASS.data.BaseDTO;

/**
 *
 * @author ctydi
 */
public class CompanyDTO extends BaseDTO{
    
  
    private final String companyName;
    
    
    private final String companyCode;
    
    private final int addressId;
    
    private final boolean isActive;
            
            private final boolean isCurrent;
            
            private String notes;
            
            
            public CompanyDTO(String name, String code, int address, boolean isActive, boolean isCurrent, String notes, int id){
                super(id);
                this.companyName = name;
                this.companyCode = code;
                
                this.addressId = address;
                this.isActive = isActive;
                this.isCurrent = isCurrent;
                this.notes = notes;
                
            }
    
    
    
}
