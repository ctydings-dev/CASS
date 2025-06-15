/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.util;

/**
 *
 * @author ctydi
 */
public class Utils {
    
    
    
    
    public static int parseInt(String in, Integer failValue){
        try{
            return Integer.parseInt(in);
        }
        catch(Throwable e){
            return failValue;
        }
        
        
    }
    
    
    
    
}
