/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ctydi
 */
public class DataObjectGenerator {

    public static <E> List createList() {
        return new ArrayList();
    }

    public static <E, F> Map createMap() {
        return new HashMap();
    }
    
    


}
