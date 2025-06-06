/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.data.address.*;
import CASS.data.BaseSearchParameter;
import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.TypeRepository.EMPLOYEE_TYPE;
import CASS.data.item.ItemDTO;
import CASS.data.person.EmployeeDTO;
import CASS.services.mysql.SearchParameter;
import CASS.services.mysql.TABLE_COLUMNS;
import java.util.Date;
import java.util.HashSet;

/**
 *
 * @author ctydi
 */
public class ItemSearchParameters extends BaseSearchParameter {


  
    
CompanySearchParameters company;

  
    public ItemSearchParameters() {
        super(SearchTable.getItemTable());

    }

    
     public ItemSearchParameters(ItemDTO toSet) {
        super(SearchTable.getItemTable());
        this.setDTO(toSet);
         

    }

    
    
    public ItemSearchParameters(int key) {
        super(key, SearchTable.getEmployeeTable());
    }

  
    public void setCompany(CompanySearchParameters toSet){
        this.company = toSet;
    }
    
    public void setCompany(int id){
        this.setCompany(new CompanySearchParameters(id));
    }
    
    public CompanySearchParameters getCompany(){
        return this.company;
    }


public void setDTO(ItemDTO toUse){
 
    this.setCompany(toUse.getCompany());
    this.setItemName(toUse.getItemName());
    this.setItemAlias(toUse.getItemAlias());
    this.setItemType(toUse.getItemType());
    this.setIsForSale(toUse.isForSale());
    
}


public void setIsSerialized(boolean toSet){
          this.addSearchParameter(TABLE_COLUMNS.INVENTORY.ITEM.IS_SERIALIZED, toSet);
        }

public void setItemName(String name)
{
    this.addSearchParameter(TABLE_COLUMNS.INVENTORY.ITEM.NAME, name);
}
public void setItemAlias(String name)
{
    this.addSearchParameter(TABLE_COLUMNS.INVENTORY.ITEM.ALIAS, name);
}

public void setItemType(TypeDTO toSet)
{
this.setItemType(toSet.getKey());
}


public void setIsForSale(Boolean toSet){
    
    this.addSearchParameter(TABLE_COLUMNS.INVENTORY.ITEM.IS_FOR_SALE, toSet);
    
}


public void setItemType(Integer toSet)
{
    this.addSearchParameter(TABLE_COLUMNS.INVENTORY.ITEM.TYPE, toSet);
}



public boolean isSearchByCompany(){
    
    return this.company != null;
}


   
        @Override
    public String getSearchQuery() {
  return ItemSearchBuilder.createQueryForItem(this);
    }

    
    
       protected String getKeyFields(){
        if(this.isSearchByCompany() == false){
                return "";
            }
            
            
            
        return TABLE_COLUMNS.INVENTORY.ITEM.COMPANY + ", ";
        
    }
    
      protected String getKeyValues(){
        
          if(this.isSearchByCompany() == false){
                return "";
            }
            
       return ""+this.getCompany().getKey() + ", ";
        
    }

      
      
    
    
    
    
}
