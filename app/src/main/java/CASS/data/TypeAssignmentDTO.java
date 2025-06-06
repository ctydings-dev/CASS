/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data;

/**
 *
 * @author ctydi
 */
public class TypeAssignmentDTO extends CreatedDTO {


    public Integer target;
    
    public TypeDTO type;
    
    
    
    public TypeAssignmentDTO(Integer target, TypeDTO type, Integer key, String date){
        super(key,date);
        this.target =target;
        this.type = type;
        
    }
    
        public TypeAssignmentDTO(Integer key, String date){
        super();
        
    }
    
          public TypeAssignmentDTO(Integer target, TypeDTO type){
        super();
        this.target =target;
        this.type = type;
        
    }

    public Integer getTarget() {
        return target;
    }

    public TypeDTO getType() {
        return type;
    }
        
        
        
    
    
}
