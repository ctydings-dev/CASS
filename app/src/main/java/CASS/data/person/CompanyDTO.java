/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.person;

import CASS.data.BaseDTO;
import CASS.data.CreatedDTO;

/**
 *
 * @author ctydi
 */
public class CompanyDTO extends CreatedDTO{
    
  
    private final String companyName;
    
    
    private final String companyCode;
    
    private final Integer addressId;
    
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
            
            
                public CompanyDTO(String name, String code, int address, boolean isActive, boolean isCurrent, String notes){
                super();
                this.companyName = name;
                this.companyCode = code;
                
                this.addressId = address;
                this.isActive = isActive;
                this.isCurrent = isCurrent;
                this.notes = notes;
                
            }
            
                public CompanyDTO(int id){
                super(id);
         this.companyName = null;
                this.companyCode =  null;
                
                this.addressId =  null;
                this.isActive = false;
                this.isCurrent =  false;
                this.notes =  null;
                
                
            }
                
                
            

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public int getAddressId() {
        return addressId;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public boolean isIsCurrent() {
        return isCurrent;
    }

    public String getNotes() {
        return notes;
    }
    
    
    
}
