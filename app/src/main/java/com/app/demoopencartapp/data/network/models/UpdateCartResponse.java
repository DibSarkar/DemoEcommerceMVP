package com.app.demoopencartapp.data.network.models;

public class UpdateCartResponse {


    /**
     * responseCode : 1
     * responseText : Cart Deleted!
     * cartData : 0
     */

    private int responseCode;
    private String responseText;
    private int cartData;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public int getCartData() {
        return cartData;
    }

    public void setCartData(int cartData) {
        this.cartData = cartData;
    }
}
