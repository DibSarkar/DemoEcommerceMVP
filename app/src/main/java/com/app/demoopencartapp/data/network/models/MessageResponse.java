package com.app.demoopencartapp.data.network.models;

public class MessageResponse {


    /**
     * responseCode : 1
     * responseText : Successfully Updated.
     */

    private int responseCode;
    private String responseText;

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
}
