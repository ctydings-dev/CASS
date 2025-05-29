/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data;

/**
 *
 * @author ctydi
 */
public class TypeDTO extends BaseDTO {

    private String roleName;

    public TypeDTO(String roleName, int key) {
        super(key);
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public int getTypeID() {
        return this.getKey();
    }
}
