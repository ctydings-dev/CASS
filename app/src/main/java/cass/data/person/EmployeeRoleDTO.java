/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cass.data.person;

import cass.data.TypeAssignmentDTO;

/**
 *
 * @author ctydi
 */
public class EmployeeRoleDTO extends TypeAssignmentDTO {

    public EmployeeRoleDTO(String roleName, int roleKey, String targetKey) {
        super(roleName, roleKey, targetKey);
    }

    public int getEmployeeID() {
        return this.getTargetKey();
    }

}
