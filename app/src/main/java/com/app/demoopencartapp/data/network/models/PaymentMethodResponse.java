package com.app.demoopencartapp.data.network.models;

public class PaymentMethodResponse {


    /**
     * responseCode : 1
     * responseText : Data Found
     * shipping : {"cod":{"code":"cod","title":"Cash On Delivery","terms":"","sort_order":"1"}}
     */

    private int responseCode;
    private String responseText;
    private ShippingBean shipping;

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

    public ShippingBean getShipping() {
        return shipping;
    }

    public void setShipping(ShippingBean shipping) {
        this.shipping = shipping;
    }

    public static class ShippingBean {
        /**
         * cod : {"code":"cod","title":"Cash On Delivery","terms":"","sort_order":"1"}
         */

        private CodBean cod;

        public CodBean getCod() {
            return cod;
        }

        public void setCod(CodBean cod) {
            this.cod = cod;
        }

        public static class CodBean {
            /**
             * code : cod
             * title : Cash On Delivery
             * terms :
             * sort_order : 1
             */

            private String code;
            private String title;
            private String terms;
            private String sort_order;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTerms() {
                return terms;
            }

            public void setTerms(String terms) {
                this.terms = terms;
            }

            public String getSort_order() {
                return sort_order;
            }

            public void setSort_order(String sort_order) {
                this.sort_order = sort_order;
            }
        }
    }
}
