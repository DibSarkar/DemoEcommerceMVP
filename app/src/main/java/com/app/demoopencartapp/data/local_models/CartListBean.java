package com.app.demoopencartapp.data.local_models;

public class CartListBean {

    private String cartId;
    private String cart_img;
    private String pro_name;
    private String pro_model;
    private String pro_price;
    private String quantity;

    public CartListBean(String cartId, String cart_img, String pro_name, String pro_model, String pro_price, String quantity) {
        this.cartId = cartId;
        this.cart_img = cart_img;
        this.pro_name = pro_name;
        this.pro_model = pro_model;
        this.pro_price = pro_price;
        this.quantity = quantity;
    }


    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCart_img() {
        return cart_img;
    }

    public void setCart_img(String cart_img) {
        this.cart_img = cart_img;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_model() {
        return pro_model;
    }

    public void setPro_model(String pro_model) {
        this.pro_model = pro_model;
    }

    public String getPro_price() {
        return pro_price;
    }

    public void setPro_price(String pro_price) {
        this.pro_price = pro_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
