package com.app.demoopencartapp.data.network.models;

import java.util.List;

public class WishlistResponse {


    /**
     * responseCode : 1
     * responseText : Wishlist get Successfully!
     * total_qty : 3
     * productList : [{"wishlist_id":"12","product_id":"90","name":"Domestic Monoblock Pump Kirloskar Brothers KDS 0510","minimum":"1","image":"http://192.168.5.51/rajudhara_multivender/image/cache/catalog/Kirloskar_Horizontal_Monoblock_Pump_KDS_0510_-500x554-200x200.jpg","manufacturer":"Kirloskar Brothers Ltd","price":"6950.00","special":"5351.50","option":["247"]},{"wishlist_id":"13","product_id":"208","name":"Safari Pro Safety Shoes Safex Molded Steel Toe 200","minimum":"2","image":"http://192.168.5.51/rajudhara_multivender/image/cache/catalog/Safari%20Pro%20Safety%20Shoes%20Safex%20Molded%20Steel%20Toe%20200-200x200.jpg","manufacturer":"Safari Pro","price":"231.00","special":"185.00","option":["247"]}]
     */

    private int responseCode;
    private String responseText;
    private int total_qty;
    private List<ProductListBean> productList;

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

    public int getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(int total_qty) {
        this.total_qty = total_qty;
    }

    public List<ProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductListBean> productList) {
        this.productList = productList;
    }

    public static class ProductListBean {
        /**
         * wishlist_id : 12
         * product_id : 90
         * name : Domestic Monoblock Pump Kirloskar Brothers KDS 0510
         * minimum : 1
         * image : http://192.168.5.51/rajudhara_multivender/image/cache/catalog/Kirloskar_Horizontal_Monoblock_Pump_KDS_0510_-500x554-200x200.jpg
         * manufacturer : Kirloskar Brothers Ltd
         * price : 6950.00
         * special : 5351.50
         * option : ["247"]
         */

        private String wishlist_id;
        private String product_id;
        private String name;
        private String minimum;
        private String image;
        private String manufacturer;
        private String price;
        private String special;
        private List<String> option;

        public String getWishlist_id() {
            return wishlist_id;
        }

        public void setWishlist_id(String wishlist_id) {
            this.wishlist_id = wishlist_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMinimum() {
            return minimum;
        }

        public void setMinimum(String minimum) {
            this.minimum = minimum;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
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

        public List<String> getOption() {
            return option;
        }

        public void setOption(List<String> option) {
            this.option = option;
        }
    }
}
