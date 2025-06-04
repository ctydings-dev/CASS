/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data;

import CASS.services.ServiceError;
import CASS.services.TypeService;
import CASS.util.DataObjectGenerator;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctydi
 */
public class TypeRepository {
 
    private static Map<NOTE_TYPE,Integer> noteTypes;
    
    private static Map<EMPLOYEE_TYPE,Integer> employeeTypes;
    
        private static Map<ITEM_TYPE,Integer> itemTypes;
    
    
    
    public static void setupRepo(TypeService svc) throws ServiceError{
        setupNoteRepo(svc);
        setupEmployeeRepo(svc);
       setupItemRepo(svc);
    }
    
    
    private static void setupNoteRepo(TypeService svc) throws ServiceError{
        noteTypes = DataObjectGenerator.createMap();
     
      for(int index= 0; index < NOTE_TYPE.values().length; index++){
            NOTE_TYPE type = NOTE_TYPE.values()[index];
                        
             noteTypes.put(type,svc.getNoteType(type.name()));
        }

    }
    
        private static void setupEmployeeRepo(TypeService svc) throws ServiceError{
        employeeTypes = DataObjectGenerator.createMap();
     
        
    
        for(int index= 0; index < EMPLOYEE_TYPE.values().length; index++){
            EMPLOYEE_TYPE type = EMPLOYEE_TYPE.values()[index];
                        
             employeeTypes.put(type,svc.getEmployeeType(type.name()));
        }

    }
    
       private static void setupItemRepo(TypeService svc) throws ServiceError{
        itemTypes = DataObjectGenerator.createMap();
     
        
    
        for(int index= 0; index < ITEM_TYPE.values().length; index++){
            ITEM_TYPE type = ITEM_TYPE.values()[index];
                        
             itemTypes.put(type,svc.getItemType(type.name()));
        }

    }
    
    
    
    
    
    
    
    public static Integer getKey(NOTE_TYPE type){
        return noteTypes.get(type);
    }
    
     public static Integer getKey(EMPLOYEE_TYPE type){
        return employeeTypes.get(type);
    }
    
    
    
    
    
    
    public enum NOTE_TYPE{
                PERSON, EMPLOYMENT,EDUCATION,SERVICE,MAINTENANCE;
                   }
    
    
        public enum EMPLOYEE_TYPE{
                EMPLOYEE,CONSULTANT,INSTRUCTOR,CONTRACTOR, TECHNICIAN,MANAGER;
                   }
    
        
        public enum ITEM_TYPE{
            REGULATOR,BCD;
        }
    
}
