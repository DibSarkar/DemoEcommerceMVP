package com.app.demoopencartapp.data.network.models;

import java.util.List;

public class CategoriesProductsResponse {


    /**
     * responseCode : 1
     * responseText : Successfully Data Found.
     * product : [{"product_id":"89","thumb":"http://192.168.5.51/rajudhara_multivender/image/cache/catalog/3-228x228.png","name":"Husqvarna Blower 350BT ALL EX US50/CA","description":"The Husqvarna 350BT is a commercial styled blower in the popular 50 cc mid-size range featuring a ne..","price":"\u20b9 53,500","special":"\u20b9 37,420","tax":false,"minimum":"1","rating":0,"href":"http://192.168.5.51/rajudhara_multivender/index.php?route=product/product&amp;product_id=89"},{"product_id":"91","thumb":"http://192.168.5.51/rajudhara_multivender/image/cache/catalog/H210-0494-228x228.png","name":"Husqvarna Blower 525BX EMEA  APAC","description":"Air flow in housing\r\n  \r\n  \r\n  14\r\n  m³/min\r\n  \r\n \r\n \r\n  \r\n  Air flow in pipe\r\n  \r\n  \r\n  13\r\n  m³/mi..","price":"\u20b9 24,800","special":"\u20b9 19,244","tax":false,"minimum":"1","rating":0,"href":"http://192.168.5.51/rajudhara_multivender/index.php?route=product/product&amp;product_id=91"},{"product_id":"93","thumb":"http://192.168.5.51/rajudhara_multivender/image/cache/catalog/H210-0314%20570-228x228.png","name":"Husqvarna Blower 570BTS ALL EX US50 / CA","description":"Air flow in housing\r\n  \r\n  \r\n  28 m³/min\r\n  \r\n \r\n \r\n  \r\n  Air flow in pipe\r\n  \r\n  \r\n  22 m³/min\r\n  \r..","price":"\u20b9 59,000","special":"\u20b9 45,366","tax":false,"minimum":"1","rating":0,"href":"http://192.168.5.51/rajudhara_multivender/index.php?route=product/product&amp;product_id=93"}]
     */

    private int responseCode;
    private String responseText;
    private List<ProductBean> product;

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

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class ProductBean {
        /**
         * product_id : 89
         * thumb : http://192.168.5.51/rajudhara_multivender/image/cache/catalog/3-228x228.png
         * name : Husqvarna Blower 350BT ALL EX US50/CA
         * description : The Husqvarna 350BT is a commercial styled blower in the popular 50 cc mid-size range featuring a ne..
         * price : ₹ 53,500
         * special : ₹ 37,420
         * tax : false
         * minimum : 1
         * rating : 0
         * href : http://192.168.5.51/rajudhara_multivender/index.php?route=product/product&amp;product_id=89
         */

        private String product_id;
        private String thumb;
        private String name;
        private String description;
        private String price;
        private String special;
        private boolean tax;
        private String minimum;
        private int rating;
        private String href;
        private String manufacturer;

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSpecial() {
            return special;
        }

        public void setSpecial(String special) {
            this.special = special;
        }

        public boolean isTax() {
            return tax;
        }

        public void setTax(boolean tax) {
            this.tax = tax;
        }

        public String getMinimum() {
            return minimum;
        }

        public void setMinimum(String minimum) {
            this.minimum = minimum;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}
