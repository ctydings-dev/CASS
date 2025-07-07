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
import CASS.data.person.PersonDTO;

/**
 *
 * @author ctydi
 */
public class PersonTableModel extends ItemTableModel {

    private Object[] data;

    private String[] columnNames;

    private boolean isSimple;

    public PersonTableModel(Object[] data, boolean isSimple) {
        super(data);
        this.data = data;
        this.isSimple = isSimple;

        if (isSimple == true) {
            this.columnNames = new String[2];
            this.columnNames[0] = "First Name";
            this.columnNames[1] = "Last Name";
            return;
        }

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
        PersonDTO person = (PersonDTO) this.getRawValue(row);

        if (col == 0) {
            return person.getFirstName();
        }

        if (col == 1) {

            if (isSimple) {
                return person.getLastName();
            }

        }

        return person.getKey();
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
