/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.ui;

/**
 *
 * @author ctydi
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import CASS.data.item.ItemDTO;
import CASS.data.item.SerializedItemDTO;
import CASS.data.person.CompanyDTO;
import javax.swing.table.AbstractTableModel;

import javax.swing.table.AbstractTableModel;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ctydi
 */
public abstract class ItemTableModel  extends AbstractTableModel {

   private Object [] data;
        
        private String [] columnNames;
        
        public ItemTableModel(String[] cols,Object[] data) {
            this.data = data;
            this.columnNames = cols;
        }
        public ItemTableModel(Object[] data) {
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

        public Object getRawValue(int row) {
            return data[row];
        }

        public abstract Object getValueAt(int row, int col);
        
      
        
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
       @Override
        public void setValueAt(Object value, int row, int col) {
         this.setValueAt(value,row,0);
        }

    public void setValueAt(Object value, int row){
        this.getData()[row] = value;
    }
        
        
        
        
        public Object [] getData(){
            
            return this.data;
        }

}
