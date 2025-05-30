/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data;

/**
 *
 * @author ctydi
 */
public class TypeAssignmentDTO extends TypeDTO {

    private int targetKey;

    public TypeAssignmentDTO(String roleName, int roleKey, String targetKey) {
        super(roleName, roleKey);
    }

    public int getTargetKey() {
        return targetKey;
    }

}
