/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package CASS.ui;

import CASS.PersonDataSeeder;
import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.item.InventoryItemDTO;
import CASS.data.item.ItemDTO;
import CASS.data.item.TransactionDTO;
import CASS.data.person.CompanyDTO;
import CASS.manager.InventoryManager;
import CASS.services.ServiceError;
import CASS.util.DataObjectGenerator;
import CASS.util.Utils;
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

/**
 *
 * @author ctydi
 */
public class InventoryView extends javax.swing.JPanel {

    /**
     * Creates new form InventoryView
     */
    
    
    
    
    private InventoryManager invenManager;
    

    
    private GUIRunner caller;
    
    
    private boolean isAddMode;
    
    
    private static final String EMPTY = "-";
    
    public InventoryView(InventoryManager invenManager, GUIRunner caller) throws ServiceError {
        initComponents();
        
        this.invenManager = invenManager;
               this.caller = caller;
               this.load();
        this.inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      
    }

    
    public InventoryManager getInventoryMngr(){
        return this.invenManager;
        
    }
    
    private void load() throws ServiceError{
        this.isAddMode = false;
       this.getCaller().loadInventory();
        this.loadAllItemTypes();
        this.loadAllMfgrs();
        this.setInventoryTable();
        this.clearItemDisplay();
          setAddButtonText();
    }
    
    
    public GUIRunner getCaller(){
        return this.caller;
    }
    
    
    public Map<CompanyDTO, Integer> getCompanies()
    {
        return this.getCaller().getCompanies();
    }
    
    public Map<Integer, ItemDTO> getItems(){
        return this.getCaller().getItems();
    }
 
    public Map<TypeDTO, Integer> getTypes(){
        return this.getCaller().getTypes();
    }
    
    
    public List<InventoryItemDTO> getInventory(){
        return this.getCaller().getInventory();
    }
    
    
    private void loadAllItemTypes() throws ServiceError{
        
       
        String[] types = new String[this.getCaller().getTypes().keySet().size() + 1];
        types[0] = EMPTY;
        AtomicInteger counter = new AtomicInteger(1);
        
        this.getCaller().getTypes().keySet().stream().forEach(entry ->{
            types[counter.get()] = entry.getTypeName();
            counter.addAndGet(1);
            });
        
        
       ComboBoxModel model = new DefaultComboBoxModel(types);
        this.itemTypes.setModel(model);
        model = new DefaultComboBoxModel(types);
        this.itemType.setModel(model);
       
    }
    
    
    private int getCompanyID(String companyCode){
        
       for(CompanyDTO comp :  this.getCompanies().keySet())
       {
           if(companyCode.trim().equalsIgnoreCase(comp.getCompanyCode().trim())){
               return comp.getKey();
           }
       }
        
        return -1;
    }
    
    
    public void setCompany(Integer companyId){
        
        
        
    }
    
    private Object [] createItemRow(InventoryItemDTO item){
        Object [] ret = new Object[4];
         ItemDTO spec = this.getItems().get(item.getKey());
        ret[0] = this.getType(spec.getItemType());
       
       
        
        ret[1] =this.getCompany(spec.getCompany());
        ret [2] = spec;
        ret[3] = item;
      
        return ret;
    }
    
    private boolean includeItem(InventoryItemDTO toCheck){
        ItemDTO spec = this.getItems().get(toCheck.getKey());
        String typeName = this.itemTypes.getSelectedItem().toString().trim();
        
        if(typeName.equalsIgnoreCase(EMPTY) == false){
            
            if(typeName.equalsIgnoreCase(this.getType(spec.getItemType()).trim()) == false){
                return false;
            }
            
        }
  
        String companyName = this.manufacturers.getSelectedItem().toString().trim();
        
        
        if(companyName.equalsIgnoreCase(EMPTY) == false){
           int companyId = this.getCompanyID(companyName);
           
           if(companyId != spec.getCompany()){
               return false;
           }
            
        }
        
        
        String name = this.itemNameFilter.getText().trim().toUpperCase();
        
        
        if(name.length() > 0){
            
            
            if(spec.getItemName().toUpperCase().contains(name) == false){
                return false;
            }
            
            
        }
        
        
        
         String alias = this.itemAliasFilter.getText().trim().toUpperCase();
        
        
        if(alias.length() > 0){
            
            
            if(spec.getItemAlias().toUpperCase().contains(alias) == false){
                return false;
            }
            
        }
        
        
        
        
        
        
        
        if(this.showInstockOnly.isSelected() == true){
            
            
            if(toCheck.getQuantity() < 1){
                return false;
            }
            
        }
        
        
        
        
        
        
        
        
        
        
        return true;
    }
    
    
    private void setInventoryTable(){
        
           List<InventoryItemDTO> sub = this.getInventory().stream().filter(toCheck ->{
           return this.includeItem(toCheck);    
           }).collect(Collectors.toList());
        
       Object [] [] data = new Object[sub.size()][];
    
       
       
       
       for(int index = 0; index < sub.size(); index++){
          data[index ] = this.createItemRow(sub.get(index));
          
       }
        ItemTableModel model = new ItemTableModel(data);
        this.inventoryTable.setModel(model);
   
        
    }
    
    private CompanyDTO getCompany(Integer id){
       return this.getCaller().getCompany(id);
        
    }
    
       private String getType(Integer id){
      
        
    return this.getCaller().getType(id);
        
    }
    
  
       
        
    
    
  
       
       
    
    
      
    private void loadAllMfgrs() throws ServiceError{
        
       
        String[] types = new String[this.getCompanies().keySet().size() + 1];
        types[0] = EMPTY;
        AtomicInteger counter = new AtomicInteger(1);
        
        this.getCompanies().keySet().stream().forEach(entry ->{
            types[counter.get()] = entry.getCompanyCode();
            counter.addAndGet(1);
            });
        
        
       ComboBoxModel model = new DefaultComboBoxModel(types);
       
       this.manufacturers.setModel(model);
        model = new DefaultComboBoxModel(types);
       this.companyCode.setModel(model);
    }
    
    
    
    
    private void handleAddAction() throws ServiceError{
        
        
        if(this.isAddMode == false){
            this.isAddMode = true;
            this.setAddButtonText();
            return;
        }
        
        this.isAddMode = false;
        String name = this.itemName.getText();
        String alias = this.itemAlias.getText();
        int company = this.getCompanyID(this.companyCode.getSelectedItem()+"");
        
        int type = this.getTypeID(this.itemType.getSelectedItem() + "");
        
        if(company < 1){
            return;
        }
        
        
        boolean serialized = this.getSerializedItemButton.getText().toUpperCase().contains("NON") == false;
        
        
      ItemDTO item = new ItemDTO(0,name,alias,type,company,true,serialized);
        
        Integer emp = this.getCaller().getEmployeeID();
        
        this.getInventoryMngr().addItem(item, emp);
        Integer qty = Utils.parseInt(this.qty.getText(), 0);
      
        
        if(qty > 0){
             int transType  = TypeRepository.getKey(TypeRepository.TRANSACTION_TYPE.RECIEVE);
          TransactionDTO trans = new TransactionDTO(item.getKey(),caller.getEmployeeID(),qty,this.getInventoryMngr().getFacility(),transType,true);
               
          
          this.getInventoryMngr().addInventoryTransaction(trans);
        int price = 0;  
        
         this.getInventoryMngr().addPrice(item.getKey(), price, caller.getEmployeeID());
            
            
            
        }
        
        
        
        this.getCaller().displayMessage("Item Added!");
        
        
        this.load();
        
        this.setAddButtonText();
        
        
        
    }
    
    
    private void setSerialiedItemButton(){
                 this.getSerializedItemButton.setEnabled(this.isAddMode);
        if(this.isAddMode){
   
            if(this.getSerializedItemButton.getText().toUpperCase().contains("NON") || this.getSerializedItemButton.getText().toUpperCase().contains("NOT"))
            {
                this.getSerializedItemButton.setText("Serialized Item");
                
                return;
            }
            
               this.getSerializedItemButton.setText("Non-Serialized Item");
            
        return;    
        }
        
        
        this.getCaller().viewSerializedItems();
        
        
    }
    
    
    
    
    private Integer getTypeID(String name){
        
        for(TypeDTO toCheck : this.getTypes().keySet()){
            
            if(toCheck.getTypeName().equalsIgnoreCase(name)){
                
                return toCheck.getTypeID();
                
            }
            
        }
        return -1;
        
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
        itemAliasFilter = new javax.swing.JTextField();
        reset = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        inventoryTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        itemName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        itemAlias = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        getSerializedItemButton = new javax.swing.JButton();
        companyName = new javax.swing.JTextField();
        Company = new javax.swing.JLabel();
        Company1 = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        Company3 = new javax.swing.JLabel();
        showInstockOnly = new javax.swing.JCheckBox();
        itemNameFilter = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        itemType = new javax.swing.JComboBox<>();
        companyCode = new javax.swing.JComboBox<>();

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

        itemAliasFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                itemAliasFilterKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                itemAliasFilterKeyTyped(evt);
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

        jLabel5.setText("Item");

        jLabel6.setText("Item Type:");

        itemName.setEditable(false);
        itemName.setText("jTextField1");

        jLabel7.setText("Item Name:");

        itemAlias.setEditable(false);
        itemAlias.setText("jTextField1");

        jLabel8.setText("Item Alias:");

        getSerializedItemButton.setText("jButton1");
        getSerializedItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getSerializedItemButtonActionPerformed(evt);
            }
        });

        companyName.setEditable(false);
        companyName.setText("jTextField1");

        Company.setText("Company Name");

        Company1.setText("Company Code");

        qty.setEditable(false);
        qty.setText("jTextField1");

        Company3.setText("Quantity in Stock:");

        showInstockOnly.setText("Instock Only");
        showInstockOnly.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                showInstockOnlyStateChanged(evt);
            }
        });

        itemNameFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                itemNameFilterKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                itemNameFilterKeyTyped(evt);
            }
        });

        jLabel9.setText("Item Alias");

        addButton.setText("Click for Add Mode");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        itemType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        itemType.setFocusable(false);

        companyCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        companyCode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                companyCodeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(itemTypes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(manufacturers, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(itemNameFilter, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(itemAliasFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(170, 170, 170))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showInstockOnly)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Company)
                                            .addComponent(Company3))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(getSerializedItemButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(0, 98, Short.MAX_VALUE)))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(itemAlias, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(companyName, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(itemName, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(Company1)
                                                .addGap(29, 29, 29)
                                                .addComponent(companyCode, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(57, 57, 57)
                                                .addComponent(itemType, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(83, 83, 83))))
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
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(itemAliasFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
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
                        .addGap(9, 9, 9)
                        .addComponent(getSerializedItemButton)
                        .addGap(10, 10, 10)
                        .addComponent(addButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showInstockOnly)
                .addContainerGap(106, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void itemTypesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_itemTypesItemStateChanged
        this.setInventoryTable();
    }//GEN-LAST:event_itemTypesItemStateChanged

    private void manufacturersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_manufacturersItemStateChanged
  this.setInventoryTable();
    }//GEN-LAST:event_manufacturersItemStateChanged

    private void itemAliasFilterKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemAliasFilterKeyTyped
     // this.setInventoryTable();
    }//GEN-LAST:event_itemAliasFilterKeyTyped

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        
        this.itemTypes.setSelectedIndex(0);
        this.manufacturers.setSelectedIndex(0);
        this.itemAliasFilter.setText("");
        this.itemNameFilter.setText("");
        this.clearItemDisplay();
        this.setInventoryTable();
         this.isAddMode = false;
           this.setAddButtonText();
        
        
        
    }//GEN-LAST:event_resetActionPerformed

    
    private void setItemTypeAndCompany(int companyId, int type){
        
        
         CompanyDTO comp =     this.getCompany(companyId);
           
        
        
         
            String code = comp.getCompanyCode().trim();
            
       
            for(int index = 0; index< this.companyCode.getModel().getSize(); index++){
                
                
                if(this.companyCode.getModel().getElementAt(index).trim().equalsIgnoreCase(code)){
                 this.companyCode.setSelectedIndex(index);
                }
           
            }
            
         
            String typeName = this.getType(type);
        this.itemType.setSelectedIndex(0);
                for(int index = 0; index< this.itemType.getModel().getSize(); index++){
                
                
                if(this.itemType.getModel().getElementAt(index).trim().equalsIgnoreCase(typeName)){
                 this.itemType.setSelectedIndex(index);
                }
           
            }
            
            
            
            
            
            
            
            
            
            
    }
    
    
    
       public void setItemToDisplay( ItemDTO item, int qty){
           this.setItemTypeAndCompany(item.getCompany(),item.getItemType());
       this.itemName.setText(item.getItemName());
           //this.itemType.setText("");
         
           
          // this.itemType.getModel().s
           
           
           this.itemAlias.setText(item.getItemAlias());
       
           this.qty.setText("" + qty);
           
           if(item.isSerialized() == true){
                this.getSerializedItemButton.setText("View Serialized Items");
           this.getSerializedItemButton.setEnabled(true);
           
           }else
           {
               this.getSerializedItemButton.setText("Item is not Serialized");
           this.getSerializedItemButton.setEnabled(false);
            
           }
           
            this.setAddButtonText();
           
           
    }
    
     public void clearItemDisplay(){
        
           this.itemName.setText("");
           this.itemType.setSelectedIndex(0);
           this.itemAlias.setText("");
           this.companyName.setText("");
          this.companyCode.setSelectedIndex(0);
           this.qty.setText("");
           this.getSerializedItemButton.setText("No Item Selected");
           this.getSerializedItemButton.setEnabled(false);
           this.isAddMode = false;
           this.setAddButtonText();
           
           
           
           
    }      
       
       
       
    
    private void itemAliasFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemAliasFilterKeyReleased
       this.setInventoryTable();
    }//GEN-LAST:event_itemAliasFilterKeyReleased

    private void inventoryTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventoryTableMouseReleased

       this.isAddMode = false;
       this.setAddButtonText();
        int index = this.inventoryTable.getSelectedRow();
  
  ItemTableModel model = (ItemTableModel) this.inventoryTable.getModel();
  
  
  
  ItemDTO item = (ItemDTO) model.getRawValue(index, 2);
  
  
  if(item.isSerialized()){
      this.getCaller().setSerializedBaseItem(item);
  }
  
  InventoryItemDTO inven = (InventoryItemDTO)model.getRawValue(index, 3);
  this.setItemToDisplay(item,inven.getQuantity());
  
  //this.inven
    }//GEN-LAST:event_inventoryTableMouseReleased

    private void showInstockOnlyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_showInstockOnlyStateChanged
      this.setInventoryTable();
    }//GEN-LAST:event_showInstockOnlyStateChanged

    private void itemNameFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemNameFilterKeyReleased
      this.setInventoryTable();
    }//GEN-LAST:event_itemNameFilterKeyReleased

    private void itemNameFilterKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_itemNameFilterKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_itemNameFilterKeyTyped

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        try {
            handleAddAction();
        } catch (ServiceError ex) {
            this.clearItemDisplay();
           this.getCaller().displayError(ex);  }
    }//GEN-LAST:event_addButtonActionPerformed

    private void getSerializedItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getSerializedItemButtonActionPerformed
      this.setSerialiedItemButton();
    }//GEN-LAST:event_getSerializedItemButtonActionPerformed

    private void companyCodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_companyCodeItemStateChanged
      
        String name = this.getCompanyName(this.companyCode.getSelectedItem().toString());
        
        this.companyName.setText(name);
        
        
        
        
        
    }//GEN-LAST:event_companyCodeItemStateChanged

    
    private String getCompanyName(String code){
return this.getCaller().getCompanyName(code);
    }
    
    
    
    private void setAddButtonText(){
        
          this.companyCode.setFocusable(this.isAddMode);
        
        this.itemType.setFocusable(this.isAddMode);
        this.itemAlias.setEditable(this.isAddMode);
        this.itemName.setEditable(this.isAddMode);
        this.qty.setEditable(this.isAddMode);
        
        
      
        
        if(this.isAddMode == true){
            this.addButton.setText("Add Item");
          this.qty.setText("0");
            setSerialiedItemButton();
            
            
       return;
        }
       
        this.addButton.setText("Click for Add Mode");
        
    }
    
    
    //https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
private     class ItemTableModel extends AbstractTableModel {
    private String[] columnNames = {"Type","Mnfr","Item","Qty"} ;
    private Object[][] data ;
    
    
    public ItemTableModel(Object [] [] data){
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
    
    public Object getRawValue(int row, int col){
         return data[row][col];
    }
    
    

    public Object getValueAt(int row, int col) {
        
      
          if(col == 1){
            return ((CompanyDTO) data[row][col]).getCompanyCode();
        }
        
        
        
        if(col == 2){
            return ((ItemDTO) data[row][col]).getItemName();
        }
        
        
        
        
        if(col == 3){
     return    ((InventoryItemDTO) data[row][col]).getQuantity();
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
    private javax.swing.JButton addButton;
    private javax.swing.JComboBox<String> companyCode;
    private javax.swing.JTextField companyCode1;
    private javax.swing.JTextField companyName;
    private javax.swing.JButton getSerializedItemButton;
    private javax.swing.JTable inventoryTable;
    private javax.swing.JTextField itemAlias;
    private javax.swing.JTextField itemAliasFilter;
    private javax.swing.JTextField itemName;
    private javax.swing.JTextField itemNameFilter;
    private javax.swing.JComboBox<String> itemType;
    private javax.swing.JComboBox<String> itemTypes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> manufacturers;
    private javax.swing.JTextField qty;
    private javax.swing.JButton reset;
    private javax.swing.JCheckBox showInstockOnly;
    // End of variables declaration//GEN-END:variables
}
