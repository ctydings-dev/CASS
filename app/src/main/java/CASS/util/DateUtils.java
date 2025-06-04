/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.util;

/**
 *
 * @author ctydi
 */
public class DateUtils {
    
    
    
    public static String createDate(int day, int month, int year){
        String monthStr = ""+month;
        String dayStr = "" + day;
        
        if(month < 10){
            monthStr = "0" + monthStr;
        }
        if(day < 10){
            
            dayStr = "0" + dayStr;
        }
        
        
        
        return year + "-" + monthStr + "-" + dayStr;
        
    }
    
    
}

