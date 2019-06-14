package com.app.demoopencartapp.data.local_models;

public class ProductListBean {

    String product_name;
    String product_img;
    String seller_name;
    String selling_price;
    String mrp_price;
    String pro_id;

    public ProductListBean(String pro_id, String product_name, String product_img, String seller_name, String selling_price, String mrp_price) {
        this.product_name = product_name;
        this.product_img = product_img;
        this.seller_name = seller_name;
        this.selling_price = selling_price;
        this.mrp_price = mrp_price;
        this.pro_id = pro_id;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getMrp_price() {
        return mrp_price;
    }

    public void setMrp_price(String mrp_price) {
        this.mrp_price = mrp_price;
    }
}
