/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.tranfer;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ctydi
 */
public class Sale {

    private String register;
    private String user;

    private String customer;

    private String tax;

    private String paymentType;

    private String status = "CLOSED";

    private double runningCost = 0;

    private String invoice;

    public String createJSON() {
        JSONObject json = new JSONObject();
        json.put("register_id", register);
        json.put("user_id", user);
        json.put("customer_id", customer);
        json.put("status", status);

        JSONObject payment = new JSONObject();
        payment.put("retailer_payment_type_id", paymentType);
        payment.put("amount", runningCost);

        json.put("register_sale_products", new JSONArray());
        this.getItems().forEach(entry -> {
            addSaleItem(json, entry);
        });

        JSONObject[] toSetPayments = {payment};

        json.put("register_sale_payments", toSetPayments);
        return json.toString();

    }

    private void addSaleItem(JSONObject json, SaleItem item) {
        JSONObject toAdd = new JSONObject();
        toAdd.put("product_id", item.getProductId());
        toAdd.put("register_id", this.getRegister());
        toAdd.put("quantity", item.getInventory());
        toAdd.put("price", item.getPrice());
        runningCost += item.getPrice();
        toAdd.put("tax", 0);
        toAdd.put("tax_id", this.getTax());
        JSONArray array = new JSONArray(json.getJSONArray("register_sale_products").length() + 1);
        array.putAll(json.getJSONArray("register_sale_products"));
        array.put(array.length() - 1, toAdd);
        json.put("register_sale_products", array);
    }

    public String getRegister() {
        return register;
    }

    public String getUser() {
        return user;
    }

    public String getCustomer() {
        return customer;
    }

    public String getTax() {
        return tax;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getStatus() {
        return status;
    }

    public String getInvoice() {
        return invoice;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    private List<SaleItem> items;

    private class SaleItem {

        private String productId;

        private int inventory;

        private double price;

        public SaleItem(String product, int inven, double price) {
            this.productId = product;
            this.inventory = inven;
            this.price = price;
        }

        public String getProductId() {
            return productId;
        }

        public int getInventory() {
            return inventory;
        }

        public double getPrice() {
            return price;
        }

    }

}
