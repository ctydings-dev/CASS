/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data;

import CASS.services.ServiceError;
import CASS.services.TypeService;
import CASS.util.DataObjectGenerator;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ctydi
 */
public class TypeRepository {

    private static Map<NOTE_TYPE, Integer> noteTypes;

    private static Map<EMPLOYEE_TYPE, Integer> employeeTypes;

    private static Map<EMPLOYEE_ROLE, Integer> employeeRoles;

    private static Map<ITEM_TYPE, Integer> itemTypes;
    private static Map<TRANSACTION_TYPE, Integer> transactionTypes;
    private static Map<CURRENCY, Integer> currencies;
    private static Map<INVOICE_TYPE, Integer> invoiceTypes;
    private static Map<ACCOUNT_TYPE, Integer> accountTypes;

    public static void setupRepo(TypeService svc) throws ServiceError {
        setupNoteRepo(svc);
        setupEmployeeRepo(svc);
        setupItemRepo(svc);
        setupEmployeeRoleRepo(svc);
        setupTransactionRepo(svc);
        setupCurrencyRepo(svc);
        setupInvoiceRepo(svc);
        setupAccountRepo(svc);
    }

    private static void setupNoteRepo(TypeService svc) throws ServiceError {
        noteTypes = DataObjectGenerator.createMap();

        for (int index = 0; index < NOTE_TYPE.values().length; index++) {
            NOTE_TYPE type = NOTE_TYPE.values()[index];

            noteTypes.put(type, svc.getNoteType(type.name()));
        }

    }

    private static void setupEmployeeRepo(TypeService svc) throws ServiceError {
        employeeTypes = DataObjectGenerator.createMap();

        for (int index = 0; index < EMPLOYEE_TYPE.values().length; index++) {
            EMPLOYEE_TYPE type = EMPLOYEE_TYPE.values()[index];

            employeeTypes.put(type, svc.getEmployeeType(type.name()));
        }

    }

    private static void setupEmployeeRoleRepo(TypeService svc) throws ServiceError {
        employeeRoles = DataObjectGenerator.createMap();

        for (int index = 0; index < EMPLOYEE_ROLE.values().length; index++) {
            EMPLOYEE_ROLE type = EMPLOYEE_ROLE.values()[index];

            employeeRoles.put(type, svc.getEmployeeRoleType(type.name()));
        }

    }

    private static void setupItemRepo(TypeService svc) throws ServiceError {
        itemTypes = DataObjectGenerator.createMap();

        for (int index = 0; index < ITEM_TYPE.values().length; index++) {
            ITEM_TYPE type = ITEM_TYPE.values()[index];

            itemTypes.put(type, svc.getItemType(type.name()));
        }

    }

    private static void setupTransactionRepo(TypeService svc) throws ServiceError {
        transactionTypes = DataObjectGenerator.createMap();

        for (int index = 0; index < TRANSACTION_TYPE.values().length; index++) {
            TRANSACTION_TYPE type = TRANSACTION_TYPE.values()[index];

            transactionTypes.put(type, svc.getTransactionType(type.name()));
        }

    }

    private static void setupInvoiceRepo(TypeService svc) throws ServiceError {
        invoiceTypes = DataObjectGenerator.createMap();

        for (int index = 0; index < INVOICE_TYPE.values().length; index++) {
            INVOICE_TYPE type = INVOICE_TYPE.values()[index];

            invoiceTypes.put(type, svc.getInvoiceType(type.name()));
        }

    }

    private static void setupCurrencyRepo(TypeService svc) throws ServiceError {
        currencies = DataObjectGenerator.createMap();

        for (int index = 0; index < CURRENCY.values().length; index++) {
            CURRENCY type = CURRENCY.values()[index];

            currencies.put(type, svc.getCurrencyType(type.name()));
        }

    }

    private static void setupAccountRepo(TypeService svc) throws ServiceError {
        accountTypes = DataObjectGenerator.createMap();

        for (int index = 0; index < ACCOUNT_TYPE.values().length; index++) {
            ACCOUNT_TYPE type = ACCOUNT_TYPE.values()[index];

            accountTypes.put(type, svc.getAccountType(type.name()));
        }

    }

    public static TypeDTO getTypeDTO(EMPLOYEE_TYPE type) {

        Integer key = getKey(type);

        return new TypeDTO(type.name(), key);

    }
    
        public static TypeDTO getTypeDTO(TRANSACTION_TYPE type) {

        Integer key = getKey(type);

        return new TypeDTO(type.name(), key);

    }
    
    
    
        public static TypeDTO getTypeDTO(INVOICE_TYPE type) {

        Integer key = getKey(type);

        return new TypeDTO(type.name(), key);

    }
    
    
    
    
     public static TypeDTO getTypeDTO(NOTE_TYPE type) {

        Integer key = getKey(type);

        return new TypeDTO(type.name(), key);

    }
    
    

    public static TypeDTO getTypeDTO(EMPLOYEE_ROLE type) {

        Integer key = getKey(type);

        return new TypeDTO(type.name(), key);

    }

    public static TypeDTO getTypeDTO(CURRENCY type) {

        Integer key = getKey(type);

        return new TypeDTO(type.name(), key);

    }

    public static TypeDTO getTypeDTO(ACCOUNT_TYPE type) {

        Integer key = getKey(type);

        return new TypeDTO(type.name(), key);

    }

    public static Integer getKey(ITEM_TYPE type) {
        return itemTypes.get(type);
    }

    public static Integer getKey(NOTE_TYPE type) {
        return noteTypes.get(type);
    }

    public static Integer getKey(EMPLOYEE_TYPE type) {
        return employeeTypes.get(type);
    }

    public static Integer getKey(EMPLOYEE_ROLE type) {
        return employeeRoles.get(type);
    }

    public static Integer getKey(TRANSACTION_TYPE type) {
        return transactionTypes.get(type);
    }

    public static Integer getKey(CURRENCY type) {
        return currencies.get(type);
    }

    public static Integer getKey(ACCOUNT_TYPE type) {
        return accountTypes.get(type);
    }

       public static Integer getKey(INVOICE_TYPE type) {
        return invoiceTypes.get(type);
    }

    
    
    
    public enum NOTE_TYPE {
        PERSON, EMPLOYMENT, EDUCATION, SERVICE, MAINTENANCE, SALES, INVENTORY;
    }

    public enum EMPLOYEE_TYPE {
        EMPLOYEE, CONSULTANT, CONTRACTOR;
    }

    public enum EMPLOYEE_ROLE {
        MANAGER, INSTRUCTOR, CASHIER, TECHNICIAN
    }

    public enum ITEM_TYPE {
        REGULATOR, BCD;
    }

    public enum TRANSACTION_TYPE {

        SALE, SHIPMENT, RECIEVE, RETURN_TO_MFG, RETURN, SERVICE, RELEASE, RETURN_REMOVED,NON_INVENTORY_RECIEVE;

    }

    public enum CURRENCY {
        USD, CAD, POUND, AUD, EURO
    }

    public enum INVOICE_TYPE {
        SALE, SHIPMENT, AUDIT, CORRECTION
    }

    public enum ACCOUNT_TYPE {
        CONSUMER, ORGANIZATION, AUDIT, CORRECTION
    }

}
