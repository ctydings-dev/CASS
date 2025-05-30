/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.services.mysql;

/**
 *
 * @author ctydi
 */
public class SearchBuilderValue {

    private String selectClause;

    private String fromClause;

    private String whereClause;

    public SearchBuilderValue(String selectClause, String fromClause, String whereClause) {
        this.selectClause = selectClause;
        this.fromClause = fromClause;
        this.whereClause = whereClause;
    }

    public String getSelectClause() {
        return selectClause;
    }

    public String getFromClause() {
        return fromClause;
    }

    public String getWhereClause() {
        return whereClause;
    }

}
