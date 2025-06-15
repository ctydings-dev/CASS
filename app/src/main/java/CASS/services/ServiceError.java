/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services;

/**
 *
 * @author ctydi
 */
public class ServiceError extends Throwable {

    private String msg;
    
    
    
    public ServiceError() {

    }

    public ServiceError(String error) {
this.msg = error;
    }

    
    public ServiceError(Throwable e){
        this.msg = e.getLocalizedMessage();
    }
    
    
    public void setError(String msg){
        this.msg = msg;
    }
    
    public String getMessage(){
        return this.msg;
    }
    
    
}




