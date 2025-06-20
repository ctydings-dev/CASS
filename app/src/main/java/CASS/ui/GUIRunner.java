/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package CASS.ui;

import CASS.AddressDataSeeder;
import CASS.PersonDataSeeder;
import CASS.data.TypeDTO;
import CASS.data.TypeRepository;
import CASS.data.address.AddressDTO;
import CASS.data.item.InventoryItemDTO;
import CASS.data.item.ItemDTO;
import CASS.data.person.AccountDTO;
import CASS.data.person.CompanyDTO;
import CASS.manager.InventoryManager;
import CASS.manager.InvoiceManager;
import CASS.manager.PersonManager;
import CASS.services.AddressService;
import CASS.services.ExtendedItemService;
import CASS.services.ItemService;
import CASS.services.PersonService;
import CASS.services.ServiceError;
import CASS.services.ServiceProvider;
import CASS.services.TypeService;
import CASS.util.DataObjectGenerator;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ctydi
 */
public class GUIRunner extends javax.swing.JFrame {

    private PersonManager prsnMngr;
    private InventoryManager invnMngr;
    private InvoiceManager ivoMngr;
  
    private InventoryView invenView ;
    private SerializedItemView itemView;
    
        private List<InventoryItemDTO> inventory;
    
   private Map<CompanyDTO, Integer> companies;
    
   private  Map<TypeDTO, Integer> types; 
   
    private Map<Integer, ItemDTO> items;
    
    
    
    

    /**
     * Creates new form GUIRunner
     */
    public GUIRunner() throws ServiceError, SQLException {
        initComponents();
        
        this.setupBackend();
        this.loadInventory();
        
       invenView = new InventoryView(this.invnMngr, this);
            itemView = new SerializedItemView(this);
          
    
        this.main.add("Inventory", invenView);
         this.main.add("Items", itemView);

        
        
   }
    
    
    public List<InventoryItemDTO> getInventory(){
        return this.inventory;
        
    }
    
    public InvoiceManager getInvoiceManager(){
        return this.ivoMngr;
    }
    
    
    public PersonManager getPersonManager(){
        return this.prsnMngr;
    }
    
    public  Map<CompanyDTO, Integer> getCompanies(){
        return this.companies;
    }
    
    public Map<TypeDTO, Integer> getTypes(){
        return this.types;
    }
    
    public Map<Integer, ItemDTO> getItems(){
        return this.items;
    }
    
    
    public ItemDTO getItemByCode(String code){
        
        return this.getInventoryManager().getItemByAlias(code);
    }
    
    
    public AccountDTO getAccountByName(String name){
        
      return  this.getPersonManager().getAccountByName(name);
    }
    
    
    
    
    
       public void loadInventory() throws ServiceError{
       this.inventory=  this.getInventoryManager().getInventory();
       this.items = DataObjectGenerator.createMap();
       for(InventoryItemDTO item : this.inventory){
           this.getItems().put( item.getKey(), this.getInventoryManager().getItem(item.getKey()));
       }

       this.types =  this.getInventoryManager().getTypesInInventory();
       this.companies = this.getInventoryManager().getCompaniesInInventory();
    }
    
    
    
    
    

       public String getType(Integer id){
        
        for(TypeDTO comp :  this.getTypes().keySet())
       {
           if(id == comp.getKey()){
               return comp.getTypeName();
           }  
       }
        
        return "ERROR";
        
    }
    
       public SerializedItemView getSerializedItemView(){
           return this.itemView;
       }
       public void setSerializedBaseItem(ItemDTO toSet){
           
           this.getSerializedItemView().seedItem(toSet);
       }
       
       

       
       
       public Integer getStoreAccountId(){
         
       return this.getInventoryManager().getShopAccount();
       }
       
       
       
       
        public CompanyDTO getCompany(Integer id){
        
        for(CompanyDTO comp :  this.getCompanies().keySet())
       {
           if(id == comp.getKey()){
               return comp;
           }  
       }
        
        return null;
        
    }
       
    
    
    
    
    public Integer getEmployeeID() throws ServiceError{
        
    return    this.prsnMngr.getEmployees().get(0).getKey();
        
        
        
    }
    
    

    
    
    public void setSerializedItemsToView(ItemDTO toView){
        
    }
    
    
    public void viewSerializedItems(){
     this.main.setSelectedComponent(this.itemView);

    }
    
    
    public void displayError(Throwable e){
        e.printStackTrace();
        this.displayError(e.getLocalizedMessage());
    }
    public void displayError(String msg){
        this.displayMessage("ERROR: " + msg);
    }
    
    
    public void displayMessage(String msg){
        JOptionPane.showMessageDialog(null,msg);
    }
    
    public InventoryManager getInventoryManager(){
        return this.invnMngr;
    }
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, 1306, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIRunner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIRunner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIRunner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIRunner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                try {
                    new GUIRunner().setVisible(true);
                } catch (ServiceError ex) {
                    Logger.getLogger(GUIRunner.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(GUIRunner.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                
            }
        });
    }

    
    
    
    
    
    
    
    private void setupBackend() throws SQLException, ServiceError {
        AddressService addrSvc = ServiceProvider.getAddressService();
        PersonService prsnSvc = ServiceProvider.getPersonService();
        ItemService itmSvc = ServiceProvider.getItemService();
        TypeService typeSvc = ServiceProvider.getTypeService();

List<AddressDTO> addrs    =     addrSvc.getAddressses();
        
int shop = 0;

 int addr =0;

for(AddressDTO adr : addrs){
    if(adr.getStreet().toUpperCase().contains("SEWARD")){
        addr = adr.getKey();
    }
    }



TypeRepository.setupRepo(typeSvc);
Integer accType = TypeRepository.getKey(TypeRepository.ACCOUNT_TYPE.SHOP);
        AccountDTO[] accs = prsnSvc.getAccountsByType(new TypeDTO(accType));



        shop = accs[0].getKey();
        

        this.prsnMngr = new PersonManager(prsnSvc);
        
       
        this.invnMngr = new InventoryManager((ExtendedItemService) itmSvc, this.prsnMngr, shop, addr);
        this.ivoMngr = new InvoiceManager(ServiceProvider.getInvoiceService(), this.invnMngr, this.prsnMngr);

    }

    
     public String getCompanyName(String code){
        code = code.trim();
        for(CompanyDTO comp : this.getCompanies().keySet()){
            
            
            if(comp.getCompanyCode().trim().equalsIgnoreCase(code) == true){
                return comp.getCompanyName();
            }
        }
        return "ERROR";
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane main;
    // End of variables declaration//GEN-END:variables
}
