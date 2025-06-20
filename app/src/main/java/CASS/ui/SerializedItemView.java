/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package CASS.ui;

import CASS.data.TypeDTO;
import CASS.data.invoice.InvoiceDTO;
import CASS.data.invoice.InvoiceItemDTO;
import CASS.data.item.InventoryItemDTO;
import CASS.data.item.ItemDTO;
import CASS.data.item.SerializedItemDTO;
import CASS.data.person.AccountDTO;
import CASS.data.person.CompanyDTO;
import CASS.manager.InventoryManager;
import CASS.services.ServiceError;
import CASS.util.DataObjectGenerator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

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
    
    
    private static final String viewModeText = "Click to Enter Add Mode";
    
    private static final String addModeText = "Click to Add Item";
    
    
   private static final String updateModeText = "Click to Update Item";
    
    private boolean isAddMode;
    
    

    public SerializedItemView(GUIRunner caller) throws ServiceError {
        initComponents();
        this.caller = caller;
        this.loadInventory();
        this.loadAllItemTypes();
        this.loadAllMfgrs();
        this.setInventoryTable();
        this.clearItemDisplay();
        this.isAddMode = false;

        this.turnAddModeOff();
        this.inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private GUIRunner getCaller() {
        return this.caller;
    }

    public InventoryManager getInventoryMngr() {
        return this.getCaller().getInventoryManager();
    }

    
    private void setAddButtonText(){
        if(this.isAddMode != true){
            this.addButton.setText(viewModeText);
            
        }
    
        if(this.getSerializedItem() == null)
        {       
    this.addButton.setText(addModeText);
    return;
        }
        
        this.addButton.setText(updateModeText);
    
    
    
    }
    
    
    private SerializedItemDTO getSerializedItem(Integer item, String serialNumber ){
   return     this.getCaller().getInventoryManager().getSerializedItem(item, serialNumber);
    }
    
    
    
    private SerializedItemDTO getSerializedItem(){
        
        String alias = this.itemAlias.getText().trim().toUpperCase();
        
        ItemDTO item  = this.getCaller().getItemByCode(alias);
        if(item == null){
            return null;
        }
        
        String sn = this.serialNumber.getText();
        
        return this.getSerializedItem(item.getKey(),sn);
        
        
        
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

    
    public void seedItem(ItemDTO toSeed){
        
        this.clearItemDisplay();
        
      this.setItem(toSeed);
       
        
    }
    
    
    
    
    public void setItem(ItemDTO toSet){
         String typeName = this.getCaller().getType(toSet.getKey());
           
        
         CompanyDTO comp = this.getCaller().getCompany(toSet.getCompany());
        
       this.companyCode.setText(comp.getCompanyCode());
       this.itemType.setText(typeName);
       this.itemAlias.setText(toSet.getItemAlias());
       
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

            if (spec.getItemAlias().toUpperCase().contains(name) == false) {
                return false;
            }

        }
        
        
        String sn = this.serialNumberFilter.getText().trim().toUpperCase();
        
        
        if(sn.length() > 0){
            
            
             if (toCheck.getSerialNumber().toUpperCase().contains(sn) == false) {
                return false;
            }
            
        }
        String owner = this.ownerFilter.getText().trim().toUpperCase();
        
        if(owner.length() > 0){
            try {
                AccountDTO acc = this.getCaller().getInventoryManager().getOwner(toCheck.getKey());
                if(acc.getAccountName().toUpperCase().contains(owner) == false){
                    
                    if(acc.getAccountNumber().toUpperCase().contains(owner) == false){
                    
                    return false;
                    }
                    
                }
                
                
                
                
                
            } catch (ServiceError ex) {
             return false;}
            
            
            
            
            
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
        SNItemTableModel model = new SNItemTableModel(data);
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
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        itemType = new javax.swing.JTextField();
        itemAlias = new javax.swing.JTextField();
        itemAliasLabel = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        companyCode = new javax.swing.JTextField();
        Company1 = new javax.swing.JLabel();
        serialNumber = new javax.swing.JTextField();
        Company3 = new javax.swing.JLabel();
        serialNumberFilter = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        invoiceTable = new javax.swing.JTable();
        account = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        itemHistory = new javax.swing.JTable();
        ownerFilter = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        sellable = new javax.swing.JCheckBox();

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

        jLabel4.setText("Serial Number");

        jLabel6.setText("Item Type:");

        itemType.setEditable(false);
        itemType.setText("jTextField1");

        itemAlias.setEditable(false);
        itemAlias.setText("jTextField1");
        itemAlias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                itemAliasKeyReleased(evt);
            }
        });

        itemAliasLabel.setText("Item Alias:");

        addButton.setText("Click to add Serialized Item");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        companyCode.setEditable(false);
        companyCode.setText("jTextField1");

        Company1.setText("Company Code");

        serialNumber.setEditable(false);
        serialNumber.setText("jTextField1");
        serialNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                serialNumberKeyReleased(evt);
            }
        });

        Company3.setText("Serial Number");

        serialNumberFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                serialNumberFilterKeyReleased(evt);
            }
        });

        jLabel7.setText("Current Owner");

        invoiceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Invoice", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        invoiceTable.setEnabled(false);
        invoiceTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        invoiceTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(invoiceTable);

        account.setText("jTextField1");

        itemHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Owner", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(itemHistory);

        ownerFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ownerFilterKeyReleased(evt);
            }
        });

        jLabel5.setText("Serial Number");

        sellable.setText("Sellable Item");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(itemTypes, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(manufacturers, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reset, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(ownerFilter)
                    .addComponent(serialNumberFilter)
                    .addComponent(itemNameFilter)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sellable))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(Company1))
                                    .addGap(21, 21, 21)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(itemType, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(companyCode, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(itemAlias, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Company3)
                                        .addComponent(jLabel7))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(serialNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                        .addComponent(account))))
                            .addComponent(itemAliasLabel))
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(itemType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(companyCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Company1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(itemAliasLabel)
                                    .addComponent(itemAlias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Company3)
                                    .addComponent(serialNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(account, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(serialNumberFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ownerFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(reset))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(sellable))
                .addContainerGap(94, Short.MAX_VALUE))
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
        this.serialNumberFilter.setText("");
        this.ownerFilter.setText("");
        this.clearItemDisplay();


    }//GEN-LAST:event_resetActionPerformed

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void setItemToDisplay(ItemDTO item, SerializedItemDTO sn) throws ServiceError {
       //this.setItemTypeAndCompany(item.getCompany(),item.getItemType());
    this.serialNumber.setText(sn.getSerialNumber());
           //this.itemType.setText("");
         
           
          // this.itemType.getModel().s
           
   this.setItem(item);
       AccountDTO acc =     this.getCaller().getInventoryManager().getOwner(sn.getKey());
Map<Date,AccountDTO> history = this.getCaller().getInventoryManager().getItemHistory(sn.getKey());


List<AccountDTO> sorted = DataObjectGenerator.createList();


  this.account.setText(acc.getAccountName());
          history.keySet().stream().forEach(entry ->{
              AccountDTO toAdd = history.get(entry);
              toAdd.setCreatedDate(entry.toString());
              sorted.add(toAdd);
                       });
        
          Object [] tableData = new Object[sorted.size()];
          for(int index = 0; index < tableData.length; index++){
              tableData[index] = sorted.get(index);
              
          }
          TableModel model = new ItemHistoryTableModel(tableData);
          itemHistory.setModel(model);
          
          this.loadInvoices(sn.getKey());
          this.addButton.setText(viewModeText);
    }

    private void loadInvoices(Integer key) throws ServiceError{
        
        List<InvoiceDTO> invoices = this.getCaller().getInvoiceManager().getInvoicesWithSerialNumber(key);
     
       InvoiceDTO [] data = new InvoiceDTO[invoices.size()];
       
       for(int index = 0; index < data.length; index++){
           
           data[index] = invoices.get(index);
           
           
           
       }
       
        TableModel model =new InvoiceTableModel(data, 2);
        this.invoiceTable.setModel(model);
               
    }
    
    
    
    
    
    public void clearItemDisplay() {

        this.itemType.setText("");
        this.itemAlias.setText("");
  
        this.companyCode.setText("");
        this.serialNumber.setText("");
        this.account.setText("");
        
        
    this.turnAddModeOff();
    }


    private void itemNameFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemNameFilterKeyReleased
        this.setInventoryTable();
    }//GEN-LAST:event_itemNameFilterKeyReleased

    private void inventoryTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryTableMouseReleased
        int index = this.inventoryTable.getSelectedRow();

        SNItemTableModel model = (SNItemTableModel) this.inventoryTable.getModel();

        ItemDTO item = (ItemDTO) model.getRawValue(index, 2);

        SerializedItemDTO sn = (SerializedItemDTO) model.getRawValue(index, 3);
   
        try {
            this.setItemToDisplay(item, sn);
            
            //this.inven
        } catch (ServiceError ex) {
        this.getCaller().displayError(ex);  }
    }//GEN-LAST:event_inventoryTableMouseReleased

    private void serialNumberFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_serialNumberFilterKeyReleased
     this.setInventoryTable();
    }//GEN-LAST:event_serialNumberFilterKeyReleased

    
    
    private void turnAddModeOff(){
        this.isAddMode = true;
        this.toggleAddButton();
        
    }
    
    
    private void toggleAddButton(){
         this.isAddMode = !this.isAddMode; 
      this.setAddButtonText();
      
    
     this.itemAliasLabel.setText("Item Alias:");
          this.itemAlias.setEditable(this.isAddMode);
          this.serialNumber.setEditable(this.isAddMode);
          this.account.setEditable(this.isAddMode);
          this.companyCode.setEnabled(!this.isAddMode);
          this.itemType.setEnabled(!this.isAddMode);
      
    }
    
    
    
    
    private boolean addItem() throws ServiceError{
    
        String alias = this.itemAlias.getText().trim().toUpperCase();
        ItemDTO item = this.getCaller().getItemByCode(alias);
        if(item == null){
            this.getCaller().displayError(alias + " is not a valid item alias.");
            return false;
        }
        String owner = this.account.getText();
        AccountDTO acc = this.getCaller().getAccountByName(owner);
        
      if(acc == null){
          
          this.getCaller().displayError(owner + " is not a valid account name.");
          return false;
      } 
  
      
      
      
      
      
      boolean isForSale = this.sellable.isSelected();
      SerializedItemDTO sn = this.getSerializedItem();
      boolean update = true;
    
      if(sn == null){
 update = false;         
          sn = new SerializedItemDTO(this.serialNumber.getText().trim(), item.getKey(), isForSale);
                }
      else
      {
      AccountDTO check = this.getCaller().getInventoryManager().getOwner(sn.getKey());
      
      if(check.getKey() == acc.getKey()){
          this.getCaller().displayError("This item is already owned by " + acc.getAccountName() + ".");
          
          return false; 
      }
         
          
      }
      
      
      
      
      
      Integer emp = this.getCaller().getEmployeeID();
      
      
      if(update == true){
          
          boolean addToInven = false;
          
          
          
          
          this.getCaller().getInventoryManager().setSerializedItemOwnership(sn.getKey(), acc.getKey(), emp);
          
          if(acc.getKey() == this.getCaller().getStoreAccountId()){
    
              this.getCaller().getInventoryManager().addSerializedItemToInventory(sn, emp);
              

              
          }
         
          
       
            return true;
       
      }
      
      
      
      
      
      
      
           return true;
        
        
    }
    
    
    
    
    
    
    
    
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
   
        if(this.isAddMode == true){
            try {
                this.addItem();
                this.clearItemDisplay();
                this.loadInventory();
            } catch (ServiceError ex) {
             this.getCaller().displayError(ex);  }
        }
        
        
        this.toggleAddButton();
    }//GEN-LAST:event_addButtonActionPerformed

    private void ownerFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ownerFilterKeyReleased
   this.setInventoryTable();
    }//GEN-LAST:event_ownerFilterKeyReleased

    
    private void serialNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_serialNumberKeyReleased
      this.setAddButtonText();
    }//GEN-LAST:event_serialNumberKeyReleased

    private void itemAliasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemAliasKeyReleased
     this.setAddButtonText();
     
     ItemDTO item = this.getCaller().getItemByCode(this.itemAlias.getText());
     
     this.itemAliasLabel.setText("Item Alias:");
     if(item == null){
         this.itemAliasLabel.setText("Item Alias:DNE");
     }
     
     
     
    }//GEN-LAST:event_itemAliasKeyReleased

    //https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
    private class SNItemTableModel extends BaseTableModel {

     
        public SNItemTableModel(Object[][] data) {
            super(data);
            String[] columnNames = {"Type", "Mnfr", "Item", "SN"};
 this.setCols(columnNames);
        }

       


        public Object getValueAt(int row, int col) {

            if (col == 1) {
          
               return ((CompanyDTO) this.getData()[row][col]).getCompanyCode();
            }

            if (col == 2) {
                return ((ItemDTO) this.getData()[row][col]).getItemAlias();
            }

            if (col == 3) {
                return ((SerializedItemDTO) this.getData()[row][col]).getSerialNumber();
            }

            return this.getData()[row][col];
        }
    }

    
    
    
    
    //https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
    private class ItemHistoryTableModel extends ItemTableModel {

        public ItemHistoryTableModel(Object[] data) {
            super(data);
            String[] columnNames = {"Acc Name", "Acc #", "Date"};
 this.setCols(columnNames);
        }

       


        public Object getValueAt(int row, int col) {

            if (col == 0) {
               return ((AccountDTO) this.getData()[row]).getAccountName();
            }
  if (col == 1) {
               return ((AccountDTO) this.getData()[row]).getAccountNumber();
            }
          
  return ((AccountDTO) this.getData()[row]).getCreatedDate();
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Company1;
    private javax.swing.JLabel Company2;
    private javax.swing.JLabel Company3;
    private javax.swing.JTextField account;
    private javax.swing.JButton addButton;
    private javax.swing.JTextField companyCode;
    private javax.swing.JTextField companyCode1;
    private javax.swing.JTable inventoryTable;
    private javax.swing.JTable invoiceTable;
    private javax.swing.JTextField itemAlias;
    private javax.swing.JLabel itemAliasLabel;
    private javax.swing.JTable itemHistory;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> manufacturers;
    private javax.swing.JTextField ownerFilter;
    private javax.swing.JButton reset;
    private javax.swing.JCheckBox sellable;
    private javax.swing.JTextField serialNumber;
    private javax.swing.JTextField serialNumberFilter;
    // End of variables declaration//GEN-END:variables
}
