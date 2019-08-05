package com.app.demoopencartapp.data.network.models;

public class AddWishlistResponse {


    /**
     * responseCode : 1
     * responseText : Wishlist Added!
     * wishlist_id : 1
     */

    private int responseCode;
    private String responseText;
    private int wishlist_id;

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

    public int getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(int wishlist_id) {
        this.wishlist_id = wishlist_id;
    }
}
