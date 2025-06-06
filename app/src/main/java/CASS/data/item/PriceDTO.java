/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CASS.data.item;

import CASS.data.CreatedDTO;

/**
 *
 * @author ctydi
 */
public class PriceDTO extends CreatedDTO{

    private Integer item;
    
    private Double purchasePrice;
    
    private Double salePrice;
    
    private String startDate;
    
    private String endDate;
    private boolean isSale;
    
    private boolean isSpecial;
    
    private Integer currency;
    
    private Integer employee;
    
    private String code;
    
    
    public PriceDTO(int key){
        super(key);
            }

    public PriceDTO(int key,Integer item, Double purchasePrice, Double salePrice, String startDate, String endDate, boolean isSale, boolean isSpecial, Integer currency, Integer employee, String code, String createdDate) {
        super(key, createdDate);
        this.item = item;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isSale = isSale;
        this.isSpecial = isSpecial;
        this.currency = currency;
        this.employee = employee;
        this.code = code;
    }

    public PriceDTO(Integer item,Double purchasePrice, Double salePrice, String startDate, String endDate, boolean isSale, boolean isSpecial, Integer currency, Integer employee, String code, String createdDate) {
        super(createdDate);
        this.item = item;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isSale = isSale;
        this.isSpecial = isSpecial;
        this.currency = currency;
        this.employee = employee;
    this.code = code;
}

    public PriceDTO(int key,Integer item,Double purchasePrice, Double salePrice, String startDate, String endDate, boolean isSale, boolean isSpecial, Integer currency, Integer employee, String code) {
        super(key);
        this.item = item;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isSale = isSale;
        this.isSpecial = isSpecial;
        this.currency = currency;
        this.employee = employee;
        this.code = code;
    }
    
    
     public PriceDTO(Integer item,Double purchasePrice, Double salePrice, String startDate, String endDate, boolean isSale, boolean isSpecial, Integer currency, Integer employee, String code) {
        super();
        this.item = item;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isSale = isSale;
        this.isSpecial = isSpecial;
        this.currency = currency;
        this.employee = employee;
        this.code = code;
    }
    
         public PriceDTO(Integer item,Double purchasePrice, Double salePrice, String startDate,  boolean isSale, boolean isSpecial, Integer currency, Integer employee, String code) {
        super();
        this.item = item;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.startDate = startDate;
        this.endDate = null;
        this.isSale = isSale;
        this.isSpecial = isSpecial;
        this.currency = currency;
        this.employee = employee;
        this.code = code;
    }
    
      
   
    
    public String getEndDate(){
        return this.endDate;
    }
    
    
    public String getStartDate(){
        return this.startDate;
    }
    
    
    public boolean isSpecial(){
        return this.isSpecial;
    }
    
    public boolean isSale(){
        return this.isSale;
    }
    
    
    public Double getSalePrice(){
        return this.salePrice;
    }
    
    public Double getPurchasePrice(){
        return this.purchasePrice;
            }
    
    public Integer getEmployee(){
        return this.employee;
    }
    
    
    public Integer getItem(){
        return this.item;
    }
    
    public String getCode(){
        return this.code;
    }
    
}
