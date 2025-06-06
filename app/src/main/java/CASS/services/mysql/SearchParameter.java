/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

import CASS.search.SearchTable;
import CASS.search.SearchValue;

/**
 *
 * @author ctydi
 */
public class SearchParameter {

    private SearchTable table;

    private String column;

    private SearchValue target;

    public SearchParameter(SearchTable table, String column, SearchValue target) {
        this.table = table;
        this.column = column;
        this.target = target;

    }

    public SearchParameter(SearchTable table, SearchValue target) {
        this.table = table;
        this.column = table.getIdName();
        this.target = target;

    }

    public SearchTable getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public SearchValue getTarget() {
        return target;
    }

    public String getWhereClause() {

        String ret = this.getTable().getColumnName(this.getColumn()) + this.getTarget().getComparator() + this.getTarget().getSqlValue();

        return ret;
    }

}
