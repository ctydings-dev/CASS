/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.search;

import CASS.services.mysql.TABLE_COLUMNS;

/**
 *
 * @author ctydi
 */
public class SearchTable {

    private String tableName;

    private String tableAlias;

    private String idName;

    public SearchTable(String name, String alias, String idName) {
        this.tableName = name;
        this.tableAlias = alias;
        this.idName = idName;
    }

    public SearchTable copy() {
        return new SearchTable(this.getTableName(), this.getTableAlias(), this.getIdName());
    }

    public String getIdName() {
        return idName;
    }

    public String getSelectValue(boolean idOnly) {

        if (idOnly) {
            return this.getTableAlias() + "." + this.getTableAliasId();
        }

        return this.getTableAlias() + ".*";
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public String getTableAliasId() {
        return this.getColumnName(this.getIdName());
    }

    public String getColumnName(String name) {
        return this.getTableAlias() + "." + name;
    }

    public static SearchTable getCountryTable() {
        return new SearchTable(TABLE_COLUMNS.LOCATION.COUNTRY.TABLE_NAME, "con", TABLE_COLUMNS.LOCATION.COUNTRY.ID);
    }

    public static SearchTable getStateTable() {
        return new SearchTable(TABLE_COLUMNS.LOCATION.STATE.TABLE_NAME, "st", TABLE_COLUMNS.LOCATION.STATE.ID);
    }

    public static SearchTable getCityTable() {
        return new SearchTable(TABLE_COLUMNS.LOCATION.CITY.TABLE_NAME, "cty", TABLE_COLUMNS.LOCATION.CITY.ID);
    }

    public static SearchTable getAddressTable() {
        return new SearchTable(TABLE_COLUMNS.LOCATION.ADDRESS.TABLE_NAME, "addr", TABLE_COLUMNS.LOCATION.ADDRESS.ID);
    }

    public static SearchTable getPersonTable() {
        return new SearchTable(TABLE_COLUMNS.PEOPLE.PERSON.TABLE_NAME, "prsn", TABLE_COLUMNS.PEOPLE.PERSON.ID);
    }

    public static SearchTable getEmployeeTable() {
        return new SearchTable(TABLE_COLUMNS.PEOPLE.EMPLOYEE.TABLE_NAME, "empl", TABLE_COLUMNS.PEOPLE.EMPLOYEE.ID);
    }


    public static SearchTable getCompanyTable(){
        return new SearchTable(TABLE_COLUMNS.PEOPLE.COMPANY.TABLE_NAME,"comp", TABLE_COLUMNS.PEOPLE.COMPANY.ID);
    }
    
    
    public static SearchTable getItemTable(){
        return new SearchTable(TABLE_COLUMNS.INVENTORY.ITEM.TABLE_NAME, "itm", TABLE_COLUMNS.INVENTORY.ITEM.ID);
    }

    
    
    
   

}


