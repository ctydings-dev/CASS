/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cass.services.mysql;

import cass.data.BaseSearchParameter;
import cass.util.DataObjectGenerator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ctydi
 */
public class SearchBuilder {

    private SearchTable base;

    private List<SearchParameter> params;

    private Map<SearchTable, SearchTable> joins;

    private List<SearchTable> joinOrder;

    public SearchBuilder(SearchTable base) {
        this.base = base;
        this.params = DataObjectGenerator.createList();
        this.joins = DataObjectGenerator.createMap();
        this.joinOrder = DataObjectGenerator.createList();
    }

    public SearchTable getBase() {
        return base;
    }

    public List<SearchTable> getJoinOrder() {
        return joinOrder;
    }

    public Map<SearchTable, SearchTable> getJoins() {
        return joins;
    }

    public List<SearchParameter> getParams() {
        return this.params;
    }

    public void addJoin(SearchTable a, SearchTable b) {
        a = a.copy();
        this.getJoins().put(a, b);
        this.getJoinOrder().add(a);
    }

    public void addParam(SearchParameter toAdd) {
        this.getParams().add(toAdd);

    }

    private String createFromClause() {
        String ret = "FROM " + this.getBase().getTableName() + " AS " + this.getBase().getTableAlias();

        for (SearchTable tab : this.getJoinOrder()) {

            SearchTable target = this.getJoins().get(tab);

            ret = ret + " INNER JOIN " + target.getTableName() + " AS " + target.getTableAlias();

            ret = ret + " ON " + target.getTableAliasId() + "=" + tab.getTableAlias() + "." + target.getIdName();

        }
        return ret;
    }

    private String createSelectClause(boolean idOnly) {
        String ret = "SELECT " + this.getBase().getSelectValue(idOnly);

        ret = ret + ", ";
        for (SearchTable tab : this.getJoinOrder()) {
            SearchTable target = this.getJoins().get(tab);
            String sub = target.getSelectValue(idOnly);

            ret = ret + sub + ", ";

        }
        return ret.substring(0, ret.length() - 2);
    }

    private String createWhereClause() {

        if (this.getParams().size() < 1) {
            return "";
        }

        String ret = "WHERE ";

        for (SearchParameter val : this.getParams()) {
            String sub = val.getWhereClause();
            sub = sub + " AND ";
            ret = ret + sub;

        }

        ret = ret.trim();
        ret = ret.substring(0, ret.length() - 3);

        return ret;
    }

    public String createQuery(boolean idOnly) {

        return (this.createSelectClause(idOnly) + " " + this.createFromClause() + " " + this.createWhereClause()).trim() + ";";
    }

    public String createQuery() {
        return this.createQuery(false);
    }

    public void addJoinParameter(SearchTable parent, BaseSearchParameter toAdd) {
        this.addJoin(parent, toAdd.getTable());

        this.addSearhParameter(toAdd);

    }

    public void addSearhParameter(BaseSearchParameter toAdd) {
        List<SearchParameter> params = toAdd.getParameters();

        for (SearchParameter param : params) {
            this.addParam(param);
        }

    }

}
