/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data;

/**
 *
 * @author ctydi
 */
public class CreatedDTO extends BaseDTO {

    private String createdDate;

    public CreatedDTO(int key, String createdDate) {
        super(key);
        this.createdDate = createdDate;
    }

    public CreatedDTO(int key) {
        super(key);

    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String date) {
        this.createdDate = date;
    }

}
