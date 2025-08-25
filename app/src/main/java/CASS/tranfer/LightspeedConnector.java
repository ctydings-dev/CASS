/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.tranfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.JSONArray;
//import org.json.JSONObject;

/**
 *
 * @author rfancher
 */
public class LightspeedConnector {

    private final String URL;

    private final String TOKEN;

    private final Map<String, String> suppliers;

    private final Map<String, String> codes;

    private final Map<String, String> categories;

    private final Map<String, String> tags;

    private final Map<String, String> brands;

    protected static final String BASE = "api/2.0/";

    private static final String PRODUCT = "products";

    private static final String SUPPLIER = "suppliers";

    private static final String BRAND = "brands";

    private static final String CATEGORY = "product_categories";

    private static final String TAG = "tags";

    private static final String REGISTER = "registers";

    private static final String USER = "users";

    private static final String OUTLET = "outlets";

    private static final String TAX = "taxes";

    private static final String PAYMENT_TYPE = "payment_types";

    protected final String register;

    protected final String outlet;

    protected final String user;

    protected final String payment;

    protected final String tax;

    private List<String> addedNames;

    protected Outputter output;

    private int skip = 182;

    protected static final int PRINT_INC = 500;

    int counter = 0;

    public LightspeedConnector(Outputter out, String url, String token, String registerName, String userName, String outletName) throws IOException {
        skip = 0;
        this.output = out;
        this.URL = url;
        this.TOKEN = token;
        this.suppliers = new HashMap();
        this.codes = new HashMap();
        this.categories = new HashMap();
        this.tags = new HashMap();
        this.brands = new HashMap();
        this.getSuppliers();
        this.getBrands();
        this.getCategories();
        this.getTags();
        String taxName = "No Tax";
        String paymentTypeName = "Cash";
        this.addedNames = new ArrayList<String>();
        this.register = this.getRegister(registerName);
        this.tax = this.getTax(taxName);
        this.payment = this.getPayment(paymentTypeName);
        this.user = this.getUser(userName);
        this.outlet = this.getOutlet(outletName);

        this.getOutput().println("PARAMETERS:");
        this.getOutput().println("REGISTER(" + registerName + ") - " + this.register);
        this.getOutput().println("OUTLET(" + outletName + ") - " + this.outlet);
        this.getOutput().println("USER(" + userName + ") - " + this.user);
        this.getOutput().println("TAX(" + taxName + ") - " + this.tax);
        this.getOutput().println("PAYMENT_TYPE(" + paymentTypeName + ") - " + this.payment);

    }

    public Outputter getOutput() {
        return output;
    }

    public String getURL() {
        return this.URL;
    }

    public static String getProductAPI() {

        return BASE + PRODUCT;

    }

    public static String getSupplierAPI() {
        return BASE + SUPPLIER;
    }

    public static String getBrandAPI() {
        return BASE + BRAND;

    }

    public static String getCategoryAPI() {
        return BASE + CATEGORY;

    }

    public static String getTagAPI() {
        return BASE + TAG;

    }

    public static String getRegisterAPI() {
        return BASE + REGISTER;
    }

    public static String getUserAPI() {
        return BASE + USER;
    }

    public static String getOutletAPI() {
        return BASE + OUTLET;
    }

    public static String getPaymentAPI() {
        return BASE + PAYMENT_TYPE;
    }

    public static String getTaxAPI() {
        return BASE + TAX;
    }

    public void clearItems() throws IOException {
        List<String> ids = getItems();
        for (int x = 0; x < ids.size(); x++) {
            if (x % PRINT_INC == 0) {
                System.out.println("DELETEING " + x + " OF " + ids.size());
            }
            this.deleteItem(ids.get(x));

        }
    }

    public void clearSuppliers() throws IOException {

        for (String name : this.suppliers.keySet()) {

            String id = this.suppliers.get(name);

            System.out.println("DELETEING " + name);
            this.deleteSupplier(id);

        }

    }

    public List<String> getItems() throws IOException {

        List<String> input = sendGetRequest("api/2.0/products");
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);

        JSONArray data = obj.getJSONArray("data");

        for (int x = 0; x < data.length(); x++) {

            String id = data.getJSONObject(x).getString("id");
            ret.add(id);

        }
        return ret;

    }

    public String getToken() {
        return this.TOKEN;
    }

    private String createJSONLine(String field, String value) {

        return "\"" + field + "\": \"" + value + "\", ";
    }

    private String createJSONBooleanLine(String field, boolean value) {
        if (value == true) {
            return "\"" + field + "\": true, ";
        }
        return "\"" + field + "\": false, ";

    }

    private String createJSONNumberLine(String field, String value) {

        return "\"" + field + "\": " + value + ", ";

    }

    private String createTagJSON(List<String> tags) throws IOException {
        String ret = "\"tag_ids\":[";

        if (tags.size() < 1) {
            return ret + "],";
        }
        List<String> ids = new ArrayList<String>();

        for (int x = 0; x < tags.size(); x++) {

            String id = this.getTag(tags.get(x));

            if (ids.contains(id) == false) {
                ret = ret + "\"" + id + "\", ";
                ids.add(id);
            }

        }

        ret = ret.trim();
        ret = ret.substring(0, ret.length() - 1);
        ret = ret + "],";
        return ret;

    }

    public void addItem(String sku, String name, String category, String cost, String price, String brand, String supplier, String stock, String barcode, String size, String color) throws IOException {
        skip--;
        counter++;
        if (skip > 0) {
            return;
        }

        name = name.trim().toUpperCase();
        if (this.addedNames.contains(name)) {

            name = name + "-" + sku;
        }

        this.addedNames.add(name);
        if (counter % PRINT_INC == 0) {
            System.out.println("ADDING " + name + " : " + sku + " OF " + counter);

        }
        category = category.trim().toUpperCase();

        List<String> tagList = new ArrayList<String>();
        tagList.add(category);

        addItem(sku, name, category, cost, price, brand, supplier, stock, barcode, size, color, tagList);
    }

    public static String cleanupSKU(String in) {

        in = in.trim().toUpperCase();
        in = in.replace("!", "");
        in = in.replace(",", "");
        in = in.replace("'", "");
        in = in.replace("\"", "");
        try {
            in = "SKU-" + Integer.parseInt(in);

        } catch (Throwable e) {

        }

        return in;
    }

    public void addItem(String sku, String name, String category, String cost, String price, String brand, String supplier, String stock, String barcode, String size, String color, List<String> tagList) throws IOException {
        skip--;
        counter++;
        if (skip > 0) {
            return;
        }

        sku = cleanupSKU(sku);
        barcode = cleanupSKU(barcode);

        String testName = name.trim().toUpperCase();
        if (this.addedNames.contains(testName)) {

            name = testName + "-" + sku;
            testName = name;
        }

        this.addedNames.add(testName);

        System.out.println("ADDING " + name + " OF " + counter);
        String json = "{";

        json = json + createJSONLine("name", name);
        json = json + createJSONLine("sku", sku);

        String catId = this.getCategory(category);

        json = json + createJSONLine("product_category_id", catId);
        String suppId = this.getSupplier(supplier);

        String code = "TO_ADD";// ItemReader.getCode(suppId);

        String brandId = this.getBrand(brand);

        if (!barcode.equalsIgnoreCase("NA")) {
            String barcodeJSON = "\"product_codes\" : [ { \"type\":\"UPC\",\"code\":\"";
            barcodeJSON = barcodeJSON + barcode + "\"}], ";

            json = json + barcodeJSON;
        }
        json = json + createJSONLine("supplier_id", suppId);
        json = json + createJSONLine("supplier_code", code);
        json = json + createJSONLine("brand_id", brandId);

        String invenJSON = "\"inventory\": [ { \"current_amount\":";
        invenJSON = invenJSON + stock + ", \"outlet_id\" : \"" + outlet + "\"}],";
        json = json + invenJSON;

        if (color != null) {
            tagList.add(color);
        }
        if (size != null) {
            tagList.add(size);
        }

        String jsonTag = createTagJSON(tagList);
        json = json + jsonTag;

        json = json + createJSONBooleanLine("is_active", true);
        json = json + createJSONNumberLine("price_excluding_tax", price);

        json = json + createJSONNumberLine("supply_price", cost);

        json = json.trim();
        json = json.substring(0, json.length() - 1);
        json = json + "}";
        try {
            sendPostRequest("api/2.0/products", json);
        } catch (Throwable e) {

            e.printStackTrace();
        }

    }

    public void getSuppliers() throws IOException {

        String url = "api/2.0/suppliers";

        List<String> input = sendGetRequest(url);
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);

        JSONArray data = obj.getJSONArray("data");

        for (int x = 0; x < data.length(); x++) {

            String id = data.getJSONObject(x).getString("id");

            String name = data.getJSONObject(x).getString("name");
            name = name.trim().toUpperCase();
            this.suppliers.put(name, id);
        }
    }

    public void addSupplier(String supplier) throws IOException {
        String url = getSupplierAPI();
        String json = "{\"name\":\"" + supplier + "\"}";
        sendPostRequest(url, json);
        this.getSuppliers();
    }

    public String getSupplier(String supplier) throws IOException {

        supplier = supplier.trim().toUpperCase();

        if (this.suppliers.containsKey(supplier)) {

            return this.suppliers.get(supplier);
        }
        this.addSupplier(supplier);

        return getSupplier(supplier);

    }

    public void getBrands() throws IOException {

        String url = getBrandAPI();
        List<String> input = sendGetRequest(url);
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);

        JSONArray data = obj.getJSONArray("data");

        for (int x = 0; x < data.length(); x++) {

            String id = data.getJSONObject(x).getString("id");

            String name = data.getJSONObject(x).getString("name");
            name = name.trim().toUpperCase();
            this.brands.put(name, id);
        }
    }

    public void addBrand(String supplier) throws IOException {
        String url = getBrandAPI();
        supplier = supplier.trim().toUpperCase();
        String json = "{\"name\":\"" + supplier + "\"}";
        sendPostRequest(url, json);
        this.getBrands();
    }

    public String getBrand(String supplier) throws IOException {
        supplier = supplier.trim().toUpperCase();
        if (this.brands.containsKey(supplier)) {

            return this.brands.get(supplier);
        }
        this.addBrand(supplier);
        return getBrand(supplier);

    }

    public void getCategories() throws IOException {

        String url = getCategoryAPI();

        List<String> input = sendGetRequest(url);
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);
        obj = obj.getJSONObject("data").getJSONObject("data");
        JSONArray data = obj.getJSONArray("categories");

        for (int x = 0; x < data.length(); x++) {

            String id = data.getJSONObject(x).getString("id");

            String name = data.getJSONObject(x).getString("name");
            this.categories.put(name, id);
        }
    }

    public String getCategory(String supplier) throws IOException {
        supplier = supplier.trim().toUpperCase();

        return this.categories.get(supplier);

    }

    public void getTags() throws IOException {

        String url = getTagAPI();
        List<String> input = sendGetRequest(url);
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);

        JSONArray data = obj.getJSONArray("data");

        for (int x = 0; x < data.length(); x++) {

            String id = data.getJSONObject(x).getString("id");

            String name = data.getJSONObject(x).getString("name");
            this.tags.put(name, id);
        }
    }

    public void addTag(String supplier) throws IOException {
        String url = getTagAPI();
        String json = "{\"name\":\"" + supplier + "\"}";
        sendPostRequest(url, json);
        this.getTags();

    }

    public String getTag(String supplier) throws IOException {
        supplier = supplier.trim().toUpperCase();

        if (this.tags.containsKey(supplier)) {

            return this.tags.get(supplier);
        }
        this.addTag(supplier);
        return getTag(supplier);

    }

    public void deleteItem(String item) throws MalformedURLException, IOException {
        final String charset = "UTF-8";
        String urlToRead;
        String path = this.getProductAPI();

        deleteCall(path, item);

    }

    public void deleteSupplier(String item) throws MalformedURLException, IOException {
        final String charset = "UTF-8";
        String urlToRead;
        String path = "api/2.0/suppliers";

        deleteCall(path, item);

    }

    public String getRegister(String registerName) throws IOException {
        registerName = registerName.trim();

        List<String> input = sendGetRequest(getRegisterAPI());
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);
        JSONArray registers = obj.getJSONArray("data");

        for (int x = 0; x < registers.length(); x++) {
            if (registerName.equalsIgnoreCase(registers.getJSONObject(x).getString("name").trim())) {
                return registers.getJSONObject(x).getString("id");
            }
        }

        return null;
    }

    public String getOutlet(String registerName) throws IOException {
        registerName = registerName.trim();

        List<String> input = sendGetRequest(getOutletAPI());
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);
        JSONArray registers = obj.getJSONArray("data");

        for (int x = 0; x < registers.length(); x++) {
            if (registerName.equalsIgnoreCase(registers.getJSONObject(x).getString("name").trim())) {
                return registers.getJSONObject(x).getString("id");
            }
        }

        return null;
    }

    public String getUser(String registerName) throws IOException {
        registerName = registerName.trim();

        List<String> input = sendGetRequest(getUserAPI());
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);
        JSONArray registers = obj.getJSONArray("data");

        for (int x = 0; x < registers.length(); x++) {
            if (registerName.equalsIgnoreCase(registers.getJSONObject(x).getString("username").trim())) {
                return registers.getJSONObject(x).getString("id");
            }
        }

        return null;
    }

    public String getTax(String registerName) throws IOException {
        registerName = registerName.trim();

        List<String> input = sendGetRequest(getTaxAPI());
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);
        JSONArray registers = obj.getJSONArray("data");

        for (int x = 0; x < registers.length(); x++) {
            if (registerName.equalsIgnoreCase(registers.getJSONObject(x).getString("name").trim())) {
                return registers.getJSONObject(x).getString("id");
            }
        }

        return null;
    }

    public String getPayment(String registerName) throws IOException {
        registerName = registerName.trim();

        List<String> input = sendGetRequest(getPaymentAPI());
        String line = input.get(0);
        List<String> ret = new ArrayList<String>();

        JSONObject obj = new JSONObject(line);
        JSONArray registers = obj.getJSONArray("data");

        for (int x = 0; x < registers.length(); x++) {
            if (registerName.equalsIgnoreCase(registers.getJSONObject(x).getString("name").trim())) {
                return registers.getJSONObject(x).getString("id");
            }
        }

        return null;
    }

    public void deleteCall(String api, String item) throws MalformedURLException, IOException {
        final String charset = "UTF-8";
        String urlToRead;
        String path = api + "/" + item;

        // Create the connection
        HttpURLConnection connection = (HttpURLConnection) new URL(this.getURL() + "/" + path).openConnection();
        // setDoOutput(true) implicitly set's the request type to POST

        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-type", "application/json");
        connection.setRequestProperty("authorization", "Bearer " + this.getToken());
        connection.setRequestMethod("DELETE");

        connection.connect();
        int code = connection.getResponseCode();
        String msg = connection.getResponseMessage();
        System.out.println(code + "  " + item);

    }

    public void sendPostRequest(String path, String JSON) throws MalformedURLException, IOException {
        final String charset = "UTF-8";
        String urlToRead;
        // Create the connection
        HttpURLConnection connection = (HttpURLConnection) new URL(this.getURL() + "/" + path).openConnection();
        // setDoOutput(true) implicitly set's the request type to POST
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + this.getToken());

        try ( OutputStream os = connection.getOutputStream()) {
            byte[] input = JSON.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Check the error stream first, if this is null then there have been no issues with the request
        InputStream inputStream = connection.getErrorStream();
        if (inputStream == null) {
            inputStream = connection.getInputStream();
        }

        // Read everything from our stream
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream, charset));

        String inputLine;
        StringBuffer response = new StringBuffer();
        List<String> ret = new ArrayList<String>();
        while ((inputLine = responseReader.readLine()) != null) {

            response.append(inputLine);
            ;
        }
        responseReader.close();

    }

    public List<String> sendGetRequest(String path) throws MalformedURLException, IOException {
        final String charset = "UTF-8";
        String urlToRead;
        // Create the connection
        HttpURLConnection connection = (HttpURLConnection) new URL(this.getURL() + "/" + path).openConnection();
        // setDoOutput(true) implicitly set's the request type to POST
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + this.getToken());
        connection.setRequestMethod("GET");

        // Check the error stream first, if this is null then there have been no issues with the request
        InputStream inputStream = connection.getErrorStream();
        if (inputStream == null) {
            inputStream = connection.getInputStream();
        }

        // Read everything from our stream
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream, charset));

        String inputLine;
        StringBuffer response = new StringBuffer();
        List<String> ret = new ArrayList<String>();
        while ((inputLine = responseReader.readLine()) != null) {

            response.append(inputLine);
            ret.add(inputLine);
        }
        responseReader.close();
        return ret;

    }

    public String getStringValue(JSONObject obj, String field) {
        if (obj.has(field)) {
            Object test = obj.get(field);
            if (test == null) {
                return null;
            }
            return test.toString();
        }
        return null;
    }

    public void createSale(Sale sale) throws IOException {
        String json = sale.createJSON();
        sendPostRequest("api/register_sales", json);
    }

}
