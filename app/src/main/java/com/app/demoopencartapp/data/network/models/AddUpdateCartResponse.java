package com.app.demoopencartapp.data.network.models;

public class AddUpdateCartResponse extends BaseResponse{


    /**
     * responseCode : 1
     * responseText : Cart Item Update Successfully
     * session_id : bf64684c86dd4c7889827d06dc
     * cartData : 11
     */


    private String session_id;
    private int cartData;


    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public int getCartData() {
        return cartData;
    }

    public void setCartData(int cartData) {
        this.cartData = cartData;
    }
}
