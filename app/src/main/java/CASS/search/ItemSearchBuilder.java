/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;


import CASS.services.mysql.SearchBuilder;


/**
 *
 * @author ctydi
 */
public class ItemSearchBuilder {

 
     
    
    
    
       
    public static String createQueryForItem(ItemSearchParameters item){
        
         SearchBuilder builder = new SearchBuilder(SearchTable.getItemTable());
        builder.addSearhParameter(item);
        
        if(item.isSearchByCompany()){
                builder.addJoinParameter(SearchTable.getItemTable(), item.getCompany());
         
            PersonSearchBuilder.addCompanyToQuery(builder,item.getCompany());
            
        }
        
        
        
        return builder.createQuery();
    }
    
    
   }
