/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS;

import CASS.services.ServiceError;
import CASS.services.TypeService;

/**
 *
 * @author ctydi
 */
public class TypeDataSeeder {
    
    
    public static int EMP, CON, INST;
    
    
    
    
    public static void addNoteTypes(TypeService svc) throws ServiceError{
       String [] types = {"PERSON","EMPLOYEE","TRAINING"};
       
       for(int index = 0; index < types.length; index++){
           
           svc.addNoteType(types[index]);
       }
        
        
    }
    
    
    public static void addEmployeeTypes(TypeService svc){
        
        
        
        
        
    }
    
}
