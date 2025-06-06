/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.services.mysql.TABLE_COLUMNS;

/**
 *
 * @author ctydi
 */
public class TypeAssignmentTable extends SearchTable {
    
    private final String targetName, typeName;
    
    
    public TypeAssignmentTable(String name, String alias, String idName, String targetName, String typeName) {
        super(name, alias, idName);
        this.targetName = targetName;
        this.typeName= typeName;
        
    }
    
    
     public static TypeAssignmentTable getEmployeeTypeTable(){   
         return new TypeAssignmentTable(TABLE_COLUMNS.PEOPLE.EMPLOYEE_ROLES.TABLE_NAME,"empRoles",TABLE_COLUMNS.PEOPLE.EMPLOYEE_ROLES.ID,TABLE_COLUMNS.PEOPLE.EMPLOYEE_ROLES.EMPLOYEE, TABLE_COLUMNS.PEOPLE.EMPLOYEE_ROLES.ROLE);
     }

    public String getTargetName() {
        return targetName;
    }

    public String getTypeName() {
        return typeName;
    }
    
    
    
    
    
    
    
}
