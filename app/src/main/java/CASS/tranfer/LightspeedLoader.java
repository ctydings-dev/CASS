/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.tranfer;

import static curltest.LightspeedConnector.getTagAPI;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ctydi
 */
public class LightspeedLoader extends LightspeedConnector {

    private Connection sql;

    private static final String USER_URL = "user";

    private static final String CUSTOMER_URL = "customers";

    private static final String REGISTER_URL = "registers";

    private static final String INVENTORY_URL = "inventory";
    private Map<String, Integer> inventory;

    private Map<String, String> knownKeys;

    public LightspeedLoader(Outputter out, String url, String token, String register, String user, String outlet, Connection conn) throws IOException, SQLException {
        super(out, url, token, register, user, outlet);
        this.sql = conn;

        this.knownKeys = new HashMap();
        this.loadInventory();

        this.clearDB();
    }

    public Connection getSql() {
        return this.sql;
    }

    public String getUserURL() {
        return BASE + USER_URL;
    }

    public String getRegisterURL() {
        return BASE + REGISTER_URL;
    }

    public String getCustomerURL() {
        return BASE + CUSTOMER_URL;
    }

    public String getInventoryURL() {
        return BASE + INVENTORY_URL;
    }

    public String getUsersURL() {
        return this.getUserURL() + "s";
    }

    public void loadUsers() throws SQLException, IOException {

        String url = getTagAPI();
        List<String> input = sendGetRequest(this.getUsersURL());
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);

        JSONArray data = obj.getJSONArray("data");

        for (int x = 0; x < data.length(); x++) {

            String id = data.getJSONObject(x).getString("id");

            String user = data.getJSONObject(x).getString("username");

            String name = data.getJSONObject(x).getString("display_name");
            String email = "NA";
            if (!data.getJSONObject(x).isNull("email")) {
                email = data.getJSONObject(x).getString("email");
            }
            addEmployeeToDB(id, user, name, email);
        }

    }

    public void loadRegister() throws SQLException, IOException {

        String url = getTagAPI();
        List<String> input = sendGetRequest(this.getRegisterURL());
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);

        JSONArray data = obj.getJSONArray("data");

        for (int x = 0; x < data.length(); x++) {

            String id = data.getJSONObject(x).getString("id");

            String outlet = data.getJSONObject(x).getString("outlet_id");

            String name = data.getJSONObject(x).getString("name");

            addRegisterToDB(name, outlet, id);
        }

    }

    public void clearDB() throws SQLException {

        String[] tables = {"customers", "products", "product_tags", "registers", "employees"};

        for (int x = 0; x < tables.length; x++) {
            String stmt = "DELETE FROM " + tables[x] + ";";

            this.getSql().createStatement().execute(stmt);
        }
    }

    public void loadCustomers() throws SQLException, IOException {

        String url = getTagAPI();
        List<String> input = sendGetRequest(this.getCustomerURL() + "?page_size=55555");
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);

        JSONArray data = obj.getJSONArray("data");

        for (int x = 0; x < data.length(); x++) {
            if (x % PRINT_INC == 0) {
                System.out.println("ON " + x + " OF " + data.length());
            }
            String id = data.getJSONObject(x).getString("id");

            String fname = getStringValue(data.getJSONObject(x), "first_name");
            String lname = getStringValue(data.getJSONObject(x), "last_name");

            String code = getStringValue(data.getJSONObject(x), "customer_code");
            String email = getStringValue(data.getJSONObject(x), "email");
            String phone = getStringValue(data.getJSONObject(x), "phone");
            String eve = getStringValue(data.getJSONObject(x), "custom_field_1");
            String street = getStringValue(data.getJSONObject(x), "postal_address_1");
            String city = getStringValue(data.getJSONObject(x), "postal_city");
            String bDay = getStringValue(data.getJSONObject(x), "date_of_birth");
            String state = "";
            String country = "";
            String zip = "";
            this.addCustomerToDB(id, code, fname, lname, street, city, state, country, zip, email, phone, bDay, eve);

        }

    }

    public void load() throws SQLException, IOException {
        this.clearDB();
        this.loadUsers();
        this.loadRegister();
        this.loadCustomers();
        this.loadProducts();

    }

    public void loadProducts() throws SQLException, IOException {

        String url = getTagAPI();
        List<String> input = sendGetRequest(this.getProductAPI() + "?page_size=55555");
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);

        JSONArray data = obj.getJSONArray("data");

        for (int x = 0; x < data.length(); x++) {
            if (x % PRINT_INC == 0) {
                System.out.println("ON " + x + " OF " + data.length());
            }
            String id = getStringValue(data.getJSONObject(x), "id");
            String name = getStringValue(data.getJSONObject(x), "name");

            String category = "NA";
            String categoryName = "NA";
            if (!data.getJSONObject(x).isNull("product_category")) {
                category = getStringValue(data.getJSONObject(x).getJSONObject("product_category"), "id");
                categoryName = getStringValue(data.getJSONObject(x).getJSONObject("product_category"), "name");

            }

            category = this.getCategoryId(category, categoryName);
            String sku = getStringValue(data.getJSONObject(x), "sku");

            String cost = getStringValue(data.getJSONObject(x), "supply_price");
            String price = getStringValue(data.getJSONObject(x), "price_excluding_tax");

            String supplier = "NA";
            String supplierName = "NA";
            if (!data.getJSONObject(x).isNull("supplier")) {
                supplier = getStringValue(data.getJSONObject(x).getJSONObject("supplier"), "id");
                supplierName = getStringValue(data.getJSONObject(x).getJSONObject("supplier"), "name");

            }
            supplier = this.getCompanyId(supplier, supplierName);
            String supplierCode = getStringValue(data.getJSONObject(x), "supplier_code");

            String brand = "NA";
            String brandName = "NA";
            if (!data.getJSONObject(x).isNull("brand")) {
                brand = getStringValue(data.getJSONObject(x).getJSONObject("brand"), "id");
                brandName = getStringValue(data.getJSONObject(x).getJSONObject("brand"), "name");
            }
            brand = this.getCompanyId(brand, brandName);
            String barcode = "NA";
            int stock = 0;
            if (this.inventory.containsKey(id)) {
                stock = this.inventory.get(id);
            }
            this.addProductToDB(name, id, sku, category, cost, price, brand, supplier, supplierCode, "" + stock, barcode);

            JSONArray cats = data.getJSONObject(x).getJSONArray("categories");

            for (int catIndex = 0; catIndex < cats.length(); catIndex++) {

                String tagId = cats.getJSONObject(catIndex).getString("id");
                String tagName = cats.getJSONObject(catIndex).getString("name");

                tagId = this.getTagId(tagId, tagName);

                String stmt = "INSERT INTO product_tags (product_id, tag_id) SELECT id, " + tagId + " FROM products WHERE product_id = '" + id + "';";

                this.getSql().createStatement().execute(stmt);
            }

        }

    }

    private void addRegisterToDB(String name, String outlet, String id) throws SQLException {
        String stmt = "INSERT INTO registers(register_name, outlet, register_id) VALUES ('";
        stmt = stmt + name + "','" + outlet + "','" + id + "');";
        this.getSql().createStatement().execute(stmt);
    }

    private void addEmployeeToDB(String id, String user, String name, String email) throws SQLException {
        String stmt = "INSERT INTO employees(employee_id, employee_name, username, email) VALUES ('";
        stmt = stmt + id + "','" + name + "','" + user + "','" + email + "');";
        this.getSql().createStatement().execute(stmt);

    }

    private void addCustomerToDB(String customerId, String code, String fName, String lName, String street, String city, String state, String country, String post, String email, String phone, String bDay, String eve) throws SQLException {
        fName = sqlCleanString(fName);
        lName = sqlCleanString(lName);
        street = sqlCleanString(street);
        email = sqlCleanString(email);
        phone = sqlCleanString(phone);
        String stmt = "INSERT INTO customers(customer_id, customer_code, first_name, last_name, street, city, state,country,post_code,email,phone,eve_id) VALUES ('";
        stmt = stmt + customerId + "','" + code + "','" + fName + "','" + lName + "','" + street + "','" + city + "','" + state + "','" + country + "','" + post + "','" + email + "','" + phone + "','" + eve + "');";
        if (bDay != null && !bDay.equalsIgnoreCase("NULL")) {
            stmt = "INSERT INTO customers(date_of_birth, customer_id, customer_code, first_name, last_name, street, city, state,country,post_code,email,phone,eve_id) VALUES ('";
            stmt = stmt + bDay + "','" + customerId + "','" + code + "','" + fName + "','" + lName + "','" + street + "','" + city + "','" + state + "','" + country + "','" + post + "','" + email + "','" + phone + "','" + eve + "');";
        }

        this.getSql().createStatement().execute(stmt);
    }

    private void addProductToDB(String name, String id, String sku, String category, String cost, String price, String brand, String supplier, String code, String stock, String barcode) throws SQLException {

        String stmt = "INSERT INTO products(product_name,product_id, sku, category, cost,price,brand,supplier, supplier_code, stock,barcode, is_active) VALUES ('";

        stmt = stmt + name + "','" + id + "','" + sku + "'," + category + "," + cost + "," + price + "," + brand + "," + supplier + ",'" + code + "','" + stock + "','" + barcode + "',TRUE);";

        this.getSql().createStatement().execute(stmt);
    }

    private void loadInventory() throws IOException {
        this.inventory = new HashMap();

        String url = getTagAPI();
        List<String> input = sendGetRequest(this.getInventoryURL() + "?page_size=55555");
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);

        JSONArray data = obj.getJSONArray("data");

        for (int x = 0; x < data.length(); x++) {

            String id = data.getJSONObject(x).getString("product_id");
            int amount = data.getJSONObject(x).getInt("current_amount");
            this.inventory.put(id, amount);

        }

    }

    private static String sqlCleanString(String in) {
        in = in.replace("'", "\\'");

        return in;
    }

    public String getCompanyId(String id, String name) throws SQLException {

        if (this.knownKeys.containsKey(id)) {
            return this.knownKeys.get(id);
        }

        String stmt = "SELECT id FROM companies WHERE company_id ='" + id + "' LIMIT 1;";

        ResultSet rs = this.getSql().createStatement().executeQuery(stmt);

        if (!rs.next()) {
            stmt = "INSERT INTO companies(company_id,company_name) VALUES ('";
            stmt = stmt + id + "','" + name + "');";

            this.getSql().createStatement().execute(stmt);
            return getCompanyId(id, name);
        }

        this.knownKeys.put(id, rs.getString("id"));
        return getCompanyId(id, name);

    }

    public String getCategoryId(String id, String name) throws SQLException {
        name = sqlCleanString(name);
        if (this.knownKeys.containsKey(id)) {
            return this.knownKeys.get(id);
        }

        String stmt = "SELECT id FROM categories WHERE category_id ='" + id + "' LIMIT 1;";

        ResultSet rs = this.getSql().createStatement().executeQuery(stmt);

        if (!rs.next()) {
            stmt = "INSERT INTO categories(category_id,category_name) VALUES ('";
            stmt = stmt + id + "','" + name + "');";
            this.getSql().createStatement().execute(stmt);
            return getCompanyId(id, name);
        }

        this.knownKeys.put(id, rs.getString("id"));
        return getCompanyId(id, name);

    }

    public String getTagId(String id, String name) throws SQLException {
        name = sqlCleanString(name);
        if (this.knownKeys.containsKey(id)) {
            return this.knownKeys.get(id);
        }

        String stmt = "SELECT id FROM tags WHERE tag_id ='" + id + "' LIMIT 1;";

        ResultSet rs = this.getSql().createStatement().executeQuery(stmt);

        if (!rs.next()) {
            stmt = "INSERT INTO tags(tag_id,tag_name) VALUES ('";
            stmt = stmt + id + "','" + name + "');";
            this.getSql().createStatement().execute(stmt);
            return getCompanyId(id, name);
        }

        this.knownKeys.put(id, rs.getString("id"));
        return getCompanyId(id, name);

    }

    private Statement createStatement() throws SQLException {
        return this.getSql().createStatement();
    }

    private List<String> getSalesIds() throws SQLException {
        List<String> ret = new ArrayList<String>();
        String stmt = "SELECT id FROM sales WHERE is_service IS NULL;";
        ResultSet rs = this.createStatement().executeQuery(stmt);
        while (rs.next()) {
            ret.add(rs.getString("id"));
        }
        return ret;
    }

    private void addSale(String id) throws SQLException {

        String stmt = "SELECT * FROM sale_items AS si INNER JOIN products AS pro ON si.product_id = pro.id WHERE si.sale_id = " + id + ";";
        ResultSet rs = this.createStatement().executeQuery(stmt);

        while (rs.next()) {
            String product = rs.getString("product_id");
            int stock = rs.getInt("amount");
            // items.add(new SaleItem(product, stock));
        }
        stmt = "SELECT cust.customer_id AS cust_key, reg.register_id AS reg_key, emp.employee_id AS emp_key FROM sales AS si INNER JOIN customers AS cust ON si.customer_id = cust.id INNER JOIN registers AS reg ON si.register_id = reg.id INNER JOIN employees AS emp ON si.user_id = emp.id;";
        rs.next();
        Sale sale = new Sale();
        sale.setTax(this.tax);
        sale.setRegister(rs.getString("reg_key"));
        sale.setUser(rs.getString("emp_key"));
        sale.setPaymentType(this.payment);
        sale.setCustomer(rs.getString("cust_key"));

    }

}
