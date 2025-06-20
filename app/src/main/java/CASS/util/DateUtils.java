/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctydi
 */
public class DateUtils {
    
  public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
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
    
    
    public static Date parseDate(String date){
        

      try {
          Date ret = formatter.parse(date);
          return ret;
      } catch (ParseException ex) {
         return new Date(0);   }
    }
    
    
}

