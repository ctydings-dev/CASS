/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

/**
 *
 * @author ctydi
 */
public class TABLE_COLUMNS {

    public static class TYPE {

        public static class EMPLOYEE_ROLE {

            public static final String TABLE_NAME = "employee_role_types";
            public static final String ID = "employee_role_type_id";
            public static final String NAME = TABLE_COLUMNS.GENERAL.TYPE;

        }

        public static class EMPLOYEE {

            public static final String TABLE_NAME = "employee_types";
            public static final String ID = "employee_type_id";
            public static final String NAME = TABLE_COLUMNS.GENERAL.TYPE;

        }

        public static class CERTIFICATION {

            public static final String TABLE_NAME = "certification_types";
            public static final String NAME = TABLE_COLUMNS.GENERAL.TYPE;

        }

        public static class NOTE {

            public static final String TABLE_NAME = "note_types";
            public static final String ID = "note_type_id";
            public static final String NAME = TABLE_COLUMNS.GENERAL.TYPE;
        }

        public static class ITEM {

            public static final String TABLE_NAME = "item_types";
            public static final String ID = "item_type_id";
            public static final String NAME = TABLE_COLUMNS.GENERAL.TYPE;
        }

        public static class TRANSACTION {

            public static final String TABLE_NAME = "inventory_transaction_types";
            public static final String ID = "inventory_transaction_type_id";
            public static final String MULTIPLYER = "inventory_multiplyer";
            public static final String NAME = TABLE_COLUMNS.GENERAL.TYPE;
        }

        public static class CURRENCY {

            public static final String TABLE_NAME = "currencies";
            public static final String ID = "currency_id";
            public static final String NAME = "currency_name";
        }

        public static class INVOICE {

            public static final String TABLE_NAME = "invoice_types";
            public static final String ID = "invoice_type_id";
            public static final String NAME = "type_name";
        }

        public static class ACCOUNT {

            public static final String TABLE_NAME = "account_types";
            public static final String ID = "account_type_id";
            public static final String NAME = "type_name";
        }

    }

    private static class GENERAL {

        public static final String IS_ACTIVE = "is_active";
        public static final String IS_CURRENT = "is_current";

        public static final String CREATED_DATE = "created_date";

        public static final String UPDATED_DATE = "updated_date";

        public static final String TYPE = "type_name";

    }

    public static class INVOICE {

        public static class INVOICE_TABLE {

            public static final String TABLE_NAME = "invoices";
            public static final String ID = "invoice_id";
            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;
            public static final String TYPE = TABLE_COLUMNS.TYPE.INVOICE.ID;
            public static final String NAME = "invoice_name";
            public static final String ACCOUNT = TABLE_COLUMNS.PEOPLE.ACCOUNT.ID;
            public static final String EMPLOYEE = TABLE_COLUMNS.PEOPLE.EMPLOYEE.ID;
            public static final String FINISIHED = "finished_date";

        }

        public static class INVOICE_ITEM {

            public static final String TABLE_NAME = "invoice_items";
            public static final String ID = "invoice_item_id";
            public static final String INVOICE = "invoice_id";
            
            public static final String PRICE = "price_id";
            public static final String TRANSACTION = TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ID;
            public static final String TAX = "tax";
            public static final String ADJUSTMENT = "adjustment";
        }

        public static class INVOICE_NOTE {

            public static final String TABLE_NAME = "invoice_notes";
            public static final String ID = "invoice_note_id";
            public static final String INVOICE = TABLE_COLUMNS.INVOICE.INVOICE_TABLE.ID;
            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;
            public static final String NOTE = "note";
            public static final String TYPE = "note_type_id";
            public static final String EMPLOYEE = TABLE_COLUMNS.PEOPLE.EMPLOYEE.ID;

        }
        
        
        public static class INVOICE_ITEM_SERIAL_NUMBER{
            
            public static final String TABLE_NAME = "invoice_item_serial_numbers";
            public static final String ID = "invoice_item_serial_number_id";
            public static final String INVOICE_ITEM = TABLE_COLUMNS.INVOICE.INVOICE_ITEM.ID;
            public static final String ITEM  = TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ID;
            
        }

        public static class INVOICE_ITEM_NOTE {

            public static final String TABLE_NAME = "invoice_item_notes";
            public static final String ID = "invoice_note_id";
            public static final String ITEM = TABLE_COLUMNS.INVOICE.INVOICE_ITEM.ID;
            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;
            public static final String NOTE = "note";
            public static final String TYPE = "note_type_id";
            public static final String EMPLOYEE = TABLE_COLUMNS.PEOPLE.EMPLOYEE.ID;

        }

    }

    public static class PEOPLE {

        public static class PERSON {

            public static final String TABLE_NAME = "persons";
            public static final String ID = "person_id";
            public static final String FIRST_NAME = "first_name";
            public static final String MIDDLE_NAME = "middle_name";
            public static final String LAST_NAME = "last_name";
            public static final String NICKNAME = "nickname";
            public static final String ALIAS = "alias";
            public static final String GENDER = "sex";
            public static final String BIRTHDAY = "birthday";
            public static final String ADDRESS = TABLE_COLUMNS.LOCATION.ADDRESS.ID;
            public static final String IS_ACTIVE = TABLE_COLUMNS.GENERAL.IS_ACTIVE;
            public static final String IS_CURRENT = TABLE_COLUMNS.GENERAL.IS_CURRENT;

            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;

            public static final String UPDATED_DATE = TABLE_COLUMNS.GENERAL.UPDATED_DATE;
        }

        public static class EMPLOYEE {

            public static final String TABLE_NAME = "employees";
            public static final String ID = "employee_id";
            public static final String PERSON = TABLE_COLUMNS.PEOPLE.PERSON.ID;
            public static final String HIRE_DATE = "hire_date";
            public static final String EMPLOYEE_TYPE = "employee_type_id";
            public static final String EMPLOYEE_CODE = "employee_code";
            public static final String IS_ACTIVE = TABLE_COLUMNS.GENERAL.IS_ACTIVE;
            public static final String IS_CURRENT = TABLE_COLUMNS.GENERAL.IS_CURRENT;

            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;

        }

        public static class COMPANY {

            public static final String TABLE_NAME = "companies";
            public static final String ID = "company_id";
            public static final String NAME = "company_name";
            public static final String CODE = "company_code";
            public static final String NOTES = "notes";
            public static final String ADDRESS = TABLE_COLUMNS.LOCATION.ADDRESS.ID;

            public static final String IS_ACTIVE = TABLE_COLUMNS.GENERAL.IS_ACTIVE;
            public static final String IS_CURRENT = TABLE_COLUMNS.GENERAL.IS_CURRENT;

            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;

            public static final String UPDATED_DATE = TABLE_COLUMNS.GENERAL.UPDATED_DATE;

        }

        public static class COMPANY_PERSON {

            public static final String TABLE_NAME = "company_persons";
            public static final String ID = "company_person_id";
            public static final String COMPANY = "company_id";
            public static final String PERSON = "person_id";

        }

        public static class EMPLOYEE_ROLES {

            public static final String TABLE_NAME = "employee_roles";

            public static final String ID = "employee_role_id";
            public static final String EMPLOYEE = "employee_id";
            public static final String ROLE = "role_id";
            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;

        }

        public static class ACCOUNT {

            public static final String TABLE_NAME = "accounts";
            public static final String ID = "account_id";
            public static final String TYPE = "account_type_id";
            public static final String NAME = "account_name";
            public static final String NUMBER = "account_number";
            public static final String PERSON = TABLE_COLUMNS.PEOPLE.PERSON.ID;

            public static final String CLOSED = "closed_date";

            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;
        }

    }

    public static class INVENTORY {

        public static class ITEM {

            public static final String TABLE_NAME = "items";
            public static final String ID = "item_id";
            public static final String NAME = "item_name";
            public static final String ALIAS = "item_alias";
            public static final String COMPANY = "company_id";
            public static final String TYPE = "item_type_id";
            public static final String IS_FOR_SALE = "is_for_sale";
            public static final String IS_SERIALIZED = "is_serialized";
              public static final String VALID = "valid";
            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;
        }
        
       
        public static class SERIALIZED_ITEM{
            public static final String TABLE_NAME = "serialized_items";
            public static final String ID  = "serialized_item_id";
            public static final String ITEM = TABLE_COLUMNS.INVENTORY.ITEM.ID;
            public static final String SERIAL_NUMBER = "serial_number";
              public static final String IS_FOR_SALE ="is_for_sale";
              public static final String PRESENT = "is_present";
        }
        
         public static class SERIALIZED_ITEM_OWNERSHIP{
            public static final String TABLE_NAME = "serialized_item_ownerships";
            public static final String ID  = "serialized_item_ownership_id";
            public static final String ITEM = TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ID;
           public static final String OWNER = TABLE_COLUMNS.PEOPLE.ACCOUNT.ID;
           public static final String ACTIVE = "is_active";
            }
        
        
        
        
        
           public static class SERIALIZED_ITEM_NOTE{
            public static final String TABLE_NAME = "serialized_item_notes";
            public static final String ID  = "serialized_item_note_id";
            public static final String ITEM = TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ID;
            public static final String NOTE = "note";
             public static final String TYPE = TABLE_COLUMNS.TYPE.NOTE.ID;
             public static final String EMPLOYEE = TABLE_COLUMNS.PEOPLE.EMPLOYEE.ID;
            
        }

        
        

        public static class SERIALIZED_INVENTORY{
            public static final String TABLE_NAME = "serialized_inventory";
            public static final String ITEM = TABLE_COLUMNS.INVENTORY.SERIALIZED_ITEM.ID;
            public static final String TRANSACTION = TABLE_COLUMNS.INVENTORY.INVENTORY_TRANSACTION.ID;
                              }
        
        
        
        
        public static class INVENTORY_TABLE {

            public static final String TABLE_NAME = "inventory";
            public static final String ID = "inventory_id";
            public static final String ITEM = "item_id";
            public static final String STOCK = "quantity";
            public static final String FACILITY = "facility_id";
        }

        public static class INVENTORY_TRANSACTION {

            public static final String TABLE_NAME = "inventory_transactions";
            public static final String ID = "inventory_transaction_id";
            public static final String ITEM = "item_id";
            public static final String AMMOUNT = "quantity";
            public static final String EMPLOYEE = "employee_id";
            public static final String TYPE = "inventory_transaction_type_id";
            public static final String DATE = "transaction_date";
            public static final String IS_VALID = "valid";
            public static final String FACILITY = "facility_id";

        }

        public static class INVENTORY_TRANSACTION_NOTE {

            public static final String TABLE_NAME = "inventory_transaction_notes";
            public static final String ID = "inventory_transaction_note_id";
            public static final String TRANSACTION = "inventory_transaction_id";
            public static final String NOTE = "note";
            public static final String TYPE = "note_type_id";
        }

        public static class PRICE {

            public static final String TABLE_NAME = "prices";
            public static final String ID = "price_id";

            public static final String NAME = "price_name";

            public static final String ITEM = "item_id";

            public static final String BUY = "purchase_price";

            public static final String SELL = "sell_price";

            public static final String IS_SPECIAL = "is_special";

            public static final String IS_SALE = "is_sale";

            public static final String CURRENCY = "currency_id";
            public static final String STARTED = "started_date";

            public static final String ENDED = "ended_date";

            public static final String CODE = "price_code";

            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;
            public static final String EMPLOYEE = TABLE_COLUMNS.PEOPLE.EMPLOYEE.ID;

        }

    }

    public static class LOCATION {

        public static class COUNTRY {

            public static final String TABLE_NAME = "countries";
            public static final String ID = "country_id";
            public static final String NAME = "country_name";
            public static final String ABBV = "country_abbv";

        }

        public static class STATE {

            public static final String TABLE_NAME = "states";
            public static final String ID = "state_id";
            public static final String NAME = "state_name";
            public static final String ABBV = "state_abbv";
            public static final String COUNTRY = TABLE_COLUMNS.LOCATION.COUNTRY.ID;

        }

        public static class CITY {

            public static final String TABLE_NAME = "cities";
            public static final String ID = "city_id";
            public static final String NAME = "city_name";
            public static final String STATE = TABLE_COLUMNS.LOCATION.STATE.ID;

        }

        public static class ADDRESS {

            public static final String TABLE_NAME = "addresses";
            public static final String ID = "address_id";
            public static final String STREET = "street";
            public static final String STREET_2 = "street_2";
            public static final String POST_CODE = "post_code";
            public static final String CITY = TABLE_COLUMNS.LOCATION.CITY.ID;

            public static final String IS_ACTIVE = TABLE_COLUMNS.GENERAL.IS_ACTIVE;
            public static final String IS_CURRENT = TABLE_COLUMNS.GENERAL.IS_CURRENT;

            public static final String CREATED_DATE = TABLE_COLUMNS.GENERAL.CREATED_DATE;

            public static final String UPDATED_DATE = TABLE_COLUMNS.GENERAL.UPDATED_DATE;

        }

    }

}
