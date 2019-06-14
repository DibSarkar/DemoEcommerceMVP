package com.app.demoopencartapp.data.local_models;

public class HomeOffersListBean {

    private String offers_img;
    private String offers_id;

    public HomeOffersListBean(String offers_img, String offers_id) {
        this.offers_img = offers_img;
        this.offers_id = offers_id;
    }

    public String getOffers_img() {
        return offers_img;
    }

    public void setOffers_img(String offers_img) {
        this.offers_img = offers_img;
    }

    public String getOffers_id() {
        return offers_id;
    }

    public void setOffers_id(String offers_id) {
        this.offers_id = offers_id;
    }
}
