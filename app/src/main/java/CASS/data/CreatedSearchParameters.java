/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data;

import CASS.services.mysql.SearchTable;
import CASS.util.DataObjectGenerator;

/**
 *
 * @author ctydi
 */
public abstract class CreatedSearchParameters extends BaseSearchParameter {

    public CreatedSearchParameters(int key, SearchTable table) {
        super(key, table);
    }

    public CreatedSearchParameters(SearchTable table) {
        super(table);
    }

}
