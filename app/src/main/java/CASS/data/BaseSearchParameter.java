/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data;

import CASS.services.mysql.SearchTable;
import CASS.services.mysql.SearchParameter;
import CASS.services.mysql.SearchValue;
import CASS.util.DataObjectGenerator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ctydi
 */
public class BaseSearchParameter {

    private Integer key;

    private final SearchTable table;

    private boolean idOverride;

    private Map<String, SearchValue> searchValues;

    public BaseSearchParameter(int key, SearchTable table) {
        this.key = key;
        this.idOverride = true;
        this.searchValues = DataObjectGenerator.createMap();
        this.table = table;

    }

    public BaseSearchParameter(SearchTable table) {
        this.key = null;
        this.idOverride = false;
        this.table = table;
        this.searchValues = DataObjectGenerator.createMap();
    }

    public SearchTable getTable() {
        return this.table;
    }

    public void addSearchParameter(String name, String value) {
        addSearchParameter(name, new SearchValue(value));
    }

    public void addSearchParameter(String name, SearchValue value) {

        if (value.getValue() == null) {
            this.getSearchValues().put(name, null);
        }

        this.getSearchValues().put(name, value);
    }

    public Map<String, SearchValue> getSearchValues() {
        return searchValues;
    }

    public int getKey() {
        return this.key;
    }

    protected boolean useIdOverride() {
        return this.idOverride = true;
    }

    protected boolean hasParameter(Object value) {

        if (this.useIdOverride() == true) {
            if (this.searchById() == true) {
                return false;
            }
        }

        return value != null;
    }

    public boolean searchById() {
        return this.key != null;
    }

    public void setParameterIsExact(String paramName, boolean toSet) {

        this.getParameters().stream().forEach(value -> {
            if (value.getColumn().equalsIgnoreCase(paramName)) {
                value.getTarget().setIsExact(toSet);
            }
        });

    }

    public List<SearchParameter> getParameters() {

        List<SearchParameter> ret = DataObjectGenerator.createList();

        if (this.searchById() == true) {
            SearchValue value = new SearchValue("" + this.getKey());
            value.setIsString(false);

            SearchParameter toAdd = new SearchParameter(this.getTable(),
                    this.getTable().getIdName(), value);
            ret.add(toAdd);
        }

        for (String param : this.getSearchValues().keySet()) {

            if (this.hasParameter(this.getSearchValues().get(param))) {
                SearchParameter toAdd = new SearchParameter(this.getTable(),
                        param,
                        this.getSearchValues().get(param));
                ret.add(toAdd);

            }

        }

        return ret;
    }

}
