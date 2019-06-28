package com.app.demoopencartapp.data.network.models;

public class AddRatingResponse {


    /**
     * responseCode : 1
     * responseText : Successfully Review Added.
     * rating : 4
     * reviews : 13
     */

    private int responseCode;
    private String responseText;
    private int rating;
    private String reviews;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }
}