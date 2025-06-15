/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package CASS.ui;

import CASS.data.TypeDTO;
import CASS.data.item.InventoryItemDTO;
import CASS.data.item.ItemDTO;
import CASS.data.item.SerializedItemDTO;
import CASS.data.person.CompanyDTO;
import CASS.manager.InventoryManager;
import CASS.services.ServiceError;
import CASS.util.DataObjectGenerator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ctydi
 */
public class SerializedItemView extends javax.swing.JPanel {

    /**
     * Creates new form InventoryView
     */
    private Map<SerializedItemDTO, ItemDTO> inventory;

    private Map<CompanyDTO, Integer> companies;

    private Map<TypeDTO, Integer> types;

    private Map<Integer, ItemDTO> items;

    private GUIRunner caller;

    private static final String EMPTY = "-";

    public SerializedItemView(GUIRunner caller) throws ServiceError {
        initComponents();
        this.caller = caller;
        this.loadInventory();
        this.loadAllItemTypes();
        this.loadAllMfgrs();
        this.setInventoryTable();
        this.clearItemDisplay();

        this.inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private GUIRunner getCaller() {
        return this.caller;
    }

    public InventoryManager getInventoryMngr() {
        return this.getCaller().getInventoryManager();
    }

    private void loadInventory() throws ServiceError {

        this.inventory = this.getInventoryMngr().getAllSerialziedItems();
        this.items = DataObjectGenerator.createMap();

        for (SerializedItemDTO item : this.inventory.keySet()) {
            this.items.put(item.getItem(), this.inventory.get(item));
        }

        this.types = this.getInventoryMngr().getTypesInInventory();
        this.companies = this.getInventoryMngr().getCompaniesInInventory();

    }

    private void loadAllItemTypes() throws ServiceError {

        String[] types = new String[this.types.keySet().size() + 1];
        types[0] = EMPTY;
        AtomicInteger counter = new AtomicInteger(1);

        this.types.keySet().stream().forEach(entry -> {
            types[counter.get()] = entry.getTypeName();
            counter.addAndGet(1);
        });

        ComboBoxModel model = new DefaultComboBoxModel(types);
        this.itemTypes.setModel(model);

    }

    private int getCompanyID(String companyCode) {

        for (CompanyDTO comp : this.companies.keySet()) {
            if (companyCode.trim().equalsIgnoreCase(comp.getCompanyCode().trim())) {
                return comp.getKey();
            }
        }

        return -1;
    }

    public void setCompany(Integer companyId) {

    }

    private Object[] createItemRow(SerializedItemDTO item) {
        Object[] ret = new Object[4];
        ItemDTO spec = this.inventory.get(item);
       ret[0] = this.getType(spec.getItemType());

     ret[1] = this.getCompany(spec.getCompany());
        ret[2] = spec;
        ret[3] = item;

        return ret;
    }

    
    
    private ItemDTO getItem(SerializedItemDTO base){
        return this.inventory.get(base);
    }
    
    private boolean includeItem(SerializedItemDTO toCheck) {
        ItemDTO spec = this.getItem(toCheck);
        String typeName = this.itemTypes.getSelectedItem().toString().trim();

        if (typeName.equalsIgnoreCase(EMPTY) == false) {

            if (typeName.equalsIgnoreCase(this.getType(spec.getItemType()).trim()) == false) {
                return false;
            }

        }

        String companyName = this.manufacturers.getSelectedItem().toString().trim();

        if (companyName.equalsIgnoreCase(EMPTY) == false) {
            int companyId = this.getCompanyID(companyName);

            if (companyId != spec.getCompany()) {
                return false;
            }

        }

        String name = this.itemNameFilter.getText().trim().toUpperCase();

        if (name.length() > 0) {

            if (spec.getItemName().toUpperCase().contains(name) == false) {
                return false;
            }

        }

        return true;
    }

    private void setInventoryTable() {

        List<SerializedItemDTO> sub= this.inventory.keySet().stream().filter(toCheck -> {
            return this.includeItem(toCheck);
        }).collect(Collectors.toList());


        
        
        Object[][] data = new Object[sub.size()][];

        for (int index = 0; index < sub.size(); index++) {
            data[index] = this.createItemRow(sub.get(index));

        }
        ItemTableModel model = new ItemTableModel(data);
        this.inventoryTable.setModel(model);

    }

    private CompanyDTO getCompany(Integer id) {

        for (CompanyDTO comp : this.companies.keySet()) {
            if (id == comp.getKey()) {
                return comp;
            }
        }

        return null;

    }

    private String getType(Integer id) {

        for (TypeDTO comp : this.types.keySet()) {
            if (id == comp.getKey()) {
                return comp.getTypeName();
            }
        }

        return "ERROR";

    }

    private void loadAllMfgrs() throws ServiceError {

        String[] types = new String[this.companies.keySet().size() + 1];
        types[0] = EMPTY;
        AtomicInteger counter = new AtomicInteger(1);

        this.companies.keySet().stream().forEach(entry -> {
            types[counter.get()] = entry.getCompanyCode();
            counter.addAndGet(1);
        });

        ComboBoxModel model = new DefaultComboBoxModel(types);

        this.manufacturers.setModel(model);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        companyCode1 = new javax.swing.JTextField();
        Company2 = new javax.swing.JLabel();
        itemTypes = new javax.swing.JComboBox<>();
        manufacturers = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        itemNameFilter = new javax.swing.JTextField();
        reset = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        inventoryTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        itemType = new javax.swing.JTextField();
        itemName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        itemAlias = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        getSerializedItemButton = new javax.swing.JButton();
        companyName = new javax.swing.JTextField();
        Company = new javax.swing.JLabel();
        companyCode = new javax.swing.JTextField();
        Company1 = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        Company3 = new javax.swing.JLabel();

        companyCode1.setEditable(false);
        companyCode1.setText("jTextField1");

        Company2.setText("Company Code");

        itemTypes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        itemTypes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                itemTypesItemStateChanged(evt);
            }
        });

        manufacturers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        manufacturers.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                manufacturersItemStateChanged(evt);
            }
        });

        jLabel1.setText("Item Type");

        jLabel2.setText("Manufacturer");

        jLabel3.setText("Item Name");

        itemNameFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                itemNameFilterKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                itemNameFilterKeyTyped(evt);
            }
        });

        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        inventoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Type", "Mnfgr", "Item", "Qty"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        inventoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                inventoryTableMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(inventoryTable);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setAutoscrolls(false);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel4.setText("Item Notes");

        jLabel5.setText("Item");

        jLabel6.setText("Item Type:");

        itemType.setEditable(false);
        itemType.setText("jTextField1");

        itemName.setEditable(false);
        itemName.setText("jTextField1");

        jLabel7.setText("Item Name:");

        itemAlias.setEditable(false);
        itemAlias.setText("jTextField1");

        jLabel8.setText("Item Alias:");

        getSerializedItemButton.setText("jButton1");

        companyName.setEditable(false);
        companyName.setText("jTextField1");

        Company.setText("Company Name");

        companyCode.setEditable(false);
        companyCode.setText("jTextField1");

        Company1.setText("Company Code");

        qty.setEditable(false);
        qty.setText("jTextField1");

        Company3.setText("Quantity in Stock:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(itemTypes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(manufacturers, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(itemNameFilter)
                    .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(itemName, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(itemType, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Company)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(itemAlias, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                    .addComponent(companyName)))
                            .addComponent(Company1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(getSerializedItemButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(Company3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(companyCode, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                        .addComponent(qty)))))
                        .addContainerGap(83, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(170, 170, 170))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(itemTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(manufacturers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(itemNameFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reset))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(itemType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(itemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(itemAlias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Company)
                            .addComponent(companyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Company1)
                            .addComponent(companyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Company3)
                            .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(getSerializedItemButton)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void itemTypesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_itemTypesItemStateChanged
        this.setInventoryTable();
    }//GEN-LAST:event_itemTypesItemStateChanged

    private void manufacturersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_manufacturersItemStateChanged
        this.setInventoryTable();
    }//GEN-LAST:event_manufacturersItemStateChanged

    private void itemNameFilterKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemNameFilterKeyTyped
        // this.setInventoryTable();
    }//GEN-LAST:event_itemNameFilterKeyTyped

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed

        this.itemTypes.setSelectedIndex(0);
        this.manufacturers.setSelectedIndex(0);
        this.itemNameFilter.setText("");
        this.clearItemDisplay();


    }//GEN-LAST:event_resetActionPerformed

    public void setItemToDisplay(ItemDTO item, int qty) {
        CompanyDTO comp = this.getCompany(item.getCompany());

        this.itemName.setText(item.getItemName());
        this.itemType.setText("");
        this.itemAlias.setText(item.getItemAlias());
        this.companyName.setText(comp.getCompanyName());
        this.companyCode.setText(comp.getCompanyCode());
        this.qty.setText("" + qty);

        if (item.isSerialized() == true) {
            this.getSerializedItemButton.setText("View Serialized Items");
            this.getSerializedItemButton.setEnabled(false);

        } else {
            this.getSerializedItemButton.setText("Item is not Serialized");
            this.getSerializedItemButton.setEnabled(false);

        }

    }

    public void clearItemDisplay() {

        this.itemName.setText("");
        this.itemType.setText("");
        this.itemAlias.setText("");
        this.companyName.setText("");
        this.companyCode.setText("");
        this.qty.setText("");
        this.getSerializedItemButton.setText("No Item Selected");
        this.getSerializedItemButton.setEnabled(false);

    }


    private void itemNameFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemNameFilterKeyReleased
        this.setInventoryTable();
    }//GEN-LAST:event_itemNameFilterKeyReleased

    private void inventoryTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryTableMouseReleased
        int index = this.inventoryTable.getSelectedRow();

        ItemTableModel model = (ItemTableModel) this.inventoryTable.getModel();

        ItemDTO item = (ItemDTO) model.getRawValue(index, 2);

        InventoryItemDTO inven = (InventoryItemDTO) model.getRawValue(index, 3);
        this.setItemToDisplay(item, inven.getQuantity());

        //this.inven
    }//GEN-LAST:event_inventoryTableMouseReleased

    //https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
    private class ItemTableModel extends AbstractTableModel {

        private String[] columnNames = {"Type", "Mnfr", "Item", "SN"};
        private Object[][] data;

        public ItemTableModel(Object[][] data) {
            this.data = data;
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

            if (col == 1) {
               return ((CompanyDTO) data[row][col]).getCompanyCode();
            }

            if (col == 2) {
                return ((ItemDTO) data[row][col]).getItemName();
            }

            if (col == 3) {
                return ((SerializedItemDTO) data[row][col]).getSerialNumber();
            }

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

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Company;
    private javax.swing.JLabel Company1;
    private javax.swing.JLabel Company2;
    private javax.swing.JLabel Company3;
    private javax.swing.JTextField companyCode;
    private javax.swing.JTextField companyCode1;
    private javax.swing.JTextField companyName;
    private javax.swing.JButton getSerializedItemButton;
    private javax.swing.JTable inventoryTable;
    private javax.swing.JTextField itemAlias;
    private javax.swing.JTextField itemName;
    private javax.swing.JTextField itemNameFilter;
    private javax.swing.JTextField itemType;
    private javax.swing.JComboBox<String> itemTypes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox<String> manufacturers;
    private javax.swing.JTextField qty;
    private javax.swing.JButton reset;
    // End of variables declaration//GEN-END:variables
}
