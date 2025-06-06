/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data;

import CASS.search.SearchTable;
import CASS.services.mysql.SearchParameter;
import CASS.search.SearchValue;
import CASS.util.DataObjectGenerator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ctydi
 */
public abstract class BaseSearchParameter {

    private Integer key;

    private final SearchTable table;

    private boolean idOverride;

    private Map<String, SearchValue> searchValues;

    public BaseSearchParameter(Integer key, SearchTable table) {
        this.key = key;
        this.setKey(key);
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

        public void addSearchParameter(String name, Boolean value) {
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

    public Integer getKey() {
        return this.key;
    }

    public void setKey(Integer toSet){
        
        this.key = toSet;
        
        if(this.getKey() != null){
           this.idOverride = true;
        }
        
        
    }
    
    
    
    protected boolean useIdOverride() {
        return this.idOverride == true;
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

    protected String getKeyFields(){
        
        return "";
        
    }
    
      protected String getKeyValues(){
        
        return "";
        
    }
    
    public String getInsertStatement(){
        
        String ret = "INSERT INTO " + this.getTable().getTableName();
        
        String fields = "(";
        
        String values = "(";
        
        
    for(String entry : this.getSearchValues().keySet()){
        fields = fields + entry + ", ";
        
        values = values + this.getSearchValues().get(entry).getSqlValue(false) + ", ";
        
    }
    
    if(this.useIdOverride() == true){
        fields = fields  + this.getTable().getIdName()+ ", ";
        values = values + this.getKey()+ ", " ;
    }
    
        
    fields = fields + this.getKeyFields();
    values = values + this.getKeyValues();
    
    fields = fields.trim();
    values = values.trim();
fields = fields.substring(0,fields.length() -1);

values = values.substring(0,values.length() -1);
        
ret = ret + fields + ") VALUES " + values + ");";

        return ret;
        
    }
    
    
    
    public abstract String getSearchQuery();
    
    
    
}
