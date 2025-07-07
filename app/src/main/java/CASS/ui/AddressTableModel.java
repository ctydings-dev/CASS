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
import CASS.data.address.CompositeAddress;

/**
 *
 * @author ctydi
 */
public class AddressTableModel extends ItemTableModel {

    private Object[] data;

    private String[] columnNames;

    public AddressTableModel(Object[] data) {
        super(data);
        this.data = data;
        this.columnNames = new String[4];
        this.columnNames[0] = "Street";
        this.columnNames[1] = "Town";
        this.columnNames[2] = "Zip";
        this.columnNames[3] = "State/Country";
    }

    protected void setCols(String[] cols) {
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

    public Object getValueAt(int row, int col) {
        CompositeAddress addr = (CompositeAddress) this.getRawValue(row);

        if (col == 0) {
            return addr.getAddress().getStreet() + " " + addr.getAddress().getStreet2();
        }

        if (col == 1) {
            return addr.getCity().getCityName();
        }

        if (col == 2) {
            return addr.getAddress().getPostCode();
        }

        if (col == 3) {
            return addr.getState().getAbbreviation() + "-" + addr.getCountry().getAbbreviation();
        }
        return addr.getAddress().getKey();
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
    @Override
    public void setValueAt(Object value, int row, int col) {
        this.setValueAt(value, row, 0);
    }

    public void setValueAt(Object value, int row) {
        this.getData()[row] = value;
    }

    public Object[] getData() {

        return this.data;
    }

}
