/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.ui;

import CASS.data.item.ItemDTO;
import CASS.data.item.SerializedItemDTO;
import CASS.data.person.CompanyDTO;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ctydi
 */
public class BaseTableModel  extends AbstractTableModel {

        private String[] columnNames = {"Type", "Mnfr", "Item", "SN"};
        private Object[][] data;

        
        
        
        public BaseTableModel(String[] cols,Object[][] data) {
            this.data = data;
            this.columnNames = cols;
        }
        public BaseTableModel(Object[][] data) {
            this.data = data;
            
        }
        
        
        protected void setCols(String [] cols){
            this.columnNames = cols;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getRawValue(int row, int col) {
            return data[row][col];
        }

        public Object getValueAt(int row, int col) {

           
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
     * Don't need to implement this method unless your table's
     * editable.
         */
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        /*
     * Don't need to implement this method unless your table's
     * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

    
        
        public Object [] [] getData(){
            
            return this.data;
        }

}
