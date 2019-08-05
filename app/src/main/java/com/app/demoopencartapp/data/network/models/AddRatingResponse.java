package com.app.demoopencartapp.data.network.models;

public class AddRatingResponse extends BaseResponse {


    /**
     * responseCode : 1
     * responseText : Successfully Review Added.
     * rating : 4
     * reviews : 13
     */


    private int rating;
    private int reviews;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
}