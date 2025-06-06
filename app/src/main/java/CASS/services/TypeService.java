/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package CASS.services;

import CASS.data.TypeDTO;
import java.util.List;

/**
 *
 * @author ctydi
 */
public interface TypeService {
 
    
    public List<TypeDTO> getNoteTypes() throws ServiceError;
    
    public List<TypeDTO> getEmployeeTypes() throws ServiceError;
    
     
    public List<TypeDTO> getCertificationTypes() throws ServiceError;
    
    public List<TypeDTO> getCertificationRequirementTypes() throws ServiceError;
    
  public List<TypeDTO> getItemTypes() throws ServiceError;
  
  public List<TypeDTO> getInvoiceItemTypes() throws ServiceError;
  
   public List<TypeDTO> getTransactionTypes() throws ServiceError;
  
  
  
  
     
    public int getNoteType(String type) throws ServiceError;
    
    public int getEmployeeType(String type) throws ServiceError;
    
     public int getEmployeeRoleType(String type) throws ServiceError;
    
       public int getTransactionType(String type) throws ServiceError;
    
   
        public int getCertificatioType(String type) throws ServiceError;
   
    
    
    public int getCertificationRequirementType(String type) throws ServiceError;
    
  public int getItemType(String type) throws ServiceError;
  
  public int getInvoiceItemType(String type) throws ServiceError;
  
  
  
  
  
  
  
  
  
  
  public int addNoteType(String toAdd)throws ServiceError;
  
    public int addEmployeeType(String toAdd) throws ServiceError;
    
     public int addCertificationType(String toAdd) throws ServiceError;
    
  public int addCertificationRequirmentType(String toAdd) throws ServiceError;
    
    public int addItemType(String toAdd) throws ServiceError;
    
  public int addInvoiceItemType(String toAdd) throws ServiceError;
  
    public int addTransactionItemType(String toAdd) throws ServiceError;
    
}