package com.app.demoopencartapp.data.network.models;

import java.util.List;

public class CartListResponse {


    /**
     * responseCode : 1
     * responseText : Cart List Found
     * cartlist : {"products":[{"cart_id":"31","product_id":"292","name":"Bosch Impact Drill &lt;500W  GSB 10RE (kit) Professional ","image":"http://192.168.5.51/rajudhara_multivender/image/cache/catalog/impact-drill-gsb-10-re-50505-50505-1200x1024.png","model":"Bosch GSB 10RE (kit) Professional ","seller_id":42,"seller":"Adinath Equipments Pvt Ltd","option":[],"cart_item":"1","tax":5,"minimum":"2","quantity":"100","price":"3510.92"},{"cart_id":"32","product_id":"231","name":"Bosch Mini Angel Grinder 4'' GWS 600 Professional","image":"http://192.168.5.51/rajudhara_multivender/image/cache/catalog/o299497v54_lv-103345-12-gws600_dyn-1200x1024.png","model":"Bosch GWS 600 Professional","seller_id":42,"seller":"Adinath Equipments Pvt Ltd","option":[],"cart_item":"1","tax":5,"minimum":"2","quantity":"1","price":"1851"},{"cart_id":"34","product_id":"208","name":"Safari Pro Safety Shoes Safex Molded Steel Toe 200","image":"http://192.168.5.51/rajudhara_multivender/image/cache/catalog/Safari%20Pro%20Safety%20Shoes%20Safex%20Molded%20Steel%20Toe%20200-1200x1024.jpg","model":"Safex Molded Steel Toe 200","seller_id":28,"seller":"Unique Sales","option":[{"product_option_id":"251","product_option_value_id":"247","name":"Chain Size In Mtr.","value":"11 Mtr.","type":"radio"}],"cart_item":"2","tax":5,"minimum":"1","quantity":"4","price":"370"}]}
     * subtotal : 5732
     * taxdata : [983,333,19]
     * total : 7067
     */

    private int responseCode;
    private String responseText;
    private CartlistBean cartlist;
    private String subtotal;
    private String total;
    private List<Integer> taxdata;

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

    public CartlistBean getCartlist() {
        return cartlist;
    }

    public void setCartlist(CartlistBean cartlist) {
        this.cartlist = cartlist;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Integer> getTaxdata() {
        return taxdata;
    }

    public void setTaxdata(List<Integer> taxdata) {
        this.taxdata = taxdata;
    }

    public static class CartlistBean {
        private List<ProductsBean> products;

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * cart_id : 31
             * product_id : 292
             * name : Bosch Impact Drill &lt;500W  GSB 10RE (kit) Professional
             * image : http://192.168.5.51/rajudhara_multivender/image/cache/catalog/impact-drill-gsb-10-re-50505-50505-1200x1024.png
             * model : Bosch GSB 10RE (kit) Professional
             * seller_id : 42
             * seller : Adinath Equipments Pvt Ltd
             * option : []
             * cart_item : 1
             * tax : 5
             * minimum : 2
             * quantity : 100
             * price : 3510.92
             */

            private String cart_id;
            private String product_id;
            private String name;
            private String image;
            private String model;
            private int seller_id;
            private String seller;
            private String cart_item;
            private int tax;
            private String minimum;
            private String quantity;
            private String price;
            private List<?> option;

            public String getCart_id() {
                return cart_id;
            }

            public void setCart_id(String cart_id) {
                this.cart_id = cart_id;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public int getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(int seller_id) {
                this.seller_id = seller_id;
            }

            public String getSeller() {
                return seller;
            }

            public void setSeller(String seller) {
                this.seller = seller;
            }

            public String getCart_item() {
                return cart_item;
            }

            public void setCart_item(String cart_item) {
                this.cart_item = cart_item;
            }

            public int getTax() {
                return tax;
            }

            public void setTax(int tax) {
                this.tax = tax;
            }

            public String getMinimum() {
                return minimum;
            }

            public void setMinimum(String minimum) {
                this.minimum = minimum;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public List<?> getOption() {
                return option;
            }

            public void setOption(List<?> option) {
                this.option = option;
            }
        }
    }
}
