/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

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
        return new SearchTable("countries", "con", "country_id");
    }

    public static SearchTable getStateTable() {
        return new SearchTable("states", "st", "state_id");
    }

    public static SearchTable getCityTable() {
        return new SearchTable("cities", "cty", "city_id");
    }

    public static SearchTable getAddressTable() {
        return new SearchTable("addresses", "addr", "address_id");
    }
}
