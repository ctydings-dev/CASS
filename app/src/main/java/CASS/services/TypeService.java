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
 
    
    public TypeDTO [] getNoteTypes() throws ServiceError;
    
    public TypeDTO [] getEmployeeTypes() throws ServiceError;
    
     
    public TypeDTO [] getCertificationTypes() throws ServiceError;
    
    public TypeDTO [] getCertificationRequirementTypes() throws ServiceError;
    
  public TypeDTO [] getItemTypes() throws ServiceError;
  
  public TypeDTO [] getInvoiceItemTypes() throws ServiceError;
  
   public TypeDTO [] getTransactionTypes() throws ServiceError;
  
     public TypeDTO [] getInvoiceTypes() throws ServiceError;
   
   public TypeDTO [] getCurrencyTypes() throws ServiceError;
  
     public TypeDTO [] getAccountTypes() throws ServiceError;
  
  
     
    public int getNoteType(String type) throws ServiceError;
    
    public int getEmployeeType(String type) throws ServiceError;
    
     public int getEmployeeRoleType(String type) throws ServiceError;
    
       public int getTransactionType(String type) throws ServiceError;
    
   public int getInvoiceType(String type) throws ServiceError;
    
   
       
       
       
        public int getCertificatioType(String type) throws ServiceError;
   
    
    
    public int getCertificationRequirementType(String type) throws ServiceError;
    
  public int getItemType(String type) throws ServiceError;
  
  public int getInvoiceItemType(String type) throws ServiceError;
  
  
   public int getCurrencyType(String type) throws ServiceError;
  
  public int getAccountType(String type) throws ServiceError;
  
  
  
  
  
  
  
  
  public int addNoteType(String toAdd)throws ServiceError;
  
    public int addEmployeeType(String toAdd) throws ServiceError;
    
     public int addCertificationType(String toAdd) throws ServiceError;
    
  public int addCertificationRequirmentType(String toAdd) throws ServiceError;
    
    public int addItemType(String toAdd) throws ServiceError;
    
  public int addInvoiceItemType(String toAdd) throws ServiceError;
  
    public int addTransactionItemType(String toAdd) throws ServiceError;
    
    public int addCurrencyType(String toAdd) throws ServiceError;
    
    public int addInvoiceType(String toAdd) throws ServiceError;
    
    
       public int addAccountType(String toAdd) throws ServiceError;
    
    
}