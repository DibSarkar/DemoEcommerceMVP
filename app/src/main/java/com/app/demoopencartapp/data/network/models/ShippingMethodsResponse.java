package com.app.demoopencartapp.data.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ShippingMethodsResponse {


    /**
     * responseCode : 1
     * responseText : Data Found
     * shipping : {"weight":{"title":"Weight Based Shipping","quote":{"weight_6":{"code":"weight.weight_6","title":"COD Available Zone","cost":"60.00","tax_class_id":"9","text":"\u20b9 60"},"weight_5":{"code":"weight.weight_5","title":"India Shipping","cost":"60.00","tax_class_id":"9","text":"\u20b9 60"}},"sort_order":"","error":false}}
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
         * weight : {"title":"Weight Based Shipping","quote":{"weight_6":{"code":"weight.weight_6","title":"COD Available Zone","cost":"60.00","tax_class_id":"9","text":"\u20b9 60"},"weight_5":{"code":"weight.weight_5","title":"India Shipping","cost":"60.00","tax_class_id":"9","text":"\u20b9 60"}},"sort_order":"","error":false}
         */

        private WeightBean weight;

        public WeightBean getWeight() {
            return weight;
        }

        public void setWeight(WeightBean weight) {
            this.weight = weight;
        }

            public static class WeightBean implements Serializable {
            /**
             * title : Weight Based Shipping
             * quote : {"weight_6":{"code":"weight.weight_6","title":"COD Available Zone","cost":"60.00","tax_class_id":"9","text":"\u20b9 60"},"weight_5":{"code":"weight.weight_5","title":"India Shipping","cost":"60.00","tax_class_id":"9","text":"\u20b9 60"}}
             * sort_order :
             * error : false
             */

            private String title;
            private QuoteBean quote;
            private String sort_order;
            private boolean error;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public QuoteBean getQuote() {
                return quote;
            }

            public void setQuote(QuoteBean quote) {
                this.quote = quote;
            }

            public String getSort_order() {
                return sort_order;
            }

            public void setSort_order(String sort_order) {
                this.sort_order = sort_order;
            }

            public boolean isError() {
                return error;
            }

            public void setError(boolean error) {
                this.error = error;
            }



            public static class QuoteBean {
                /**
                 * weight_6 : {"code":"weight.weight_6","title":"COD Available Zone","cost":"60.00","tax_class_id":"9","text":"\u20b9 60"}
                 * weight_5 : {"code":"weight.weight_5","title":"India Shipping","cost":"60.00","tax_class_id":"9","text":"\u20b9 60"}
                 */

                private Weight6Bean weight_6;
                private Weight5Bean weight_5;

                public Weight6Bean getWeight_6() {
                    return weight_6;
                }

                public void setWeight_6(Weight6Bean weight_6) {
                    this.weight_6 = weight_6;
                }

                public Weight5Bean getWeight_5() {
                    return weight_5;
                }

                public void setWeight_5(Weight5Bean weight_5) {
                    this.weight_5 = weight_5;
                }

                public static class Weight6Bean {
                    /**
                     * code : weight.weight_6
                     * title : COD Available Zone
                     * cost : 60.00
                     * tax_class_id : 9
                     * text : ₹ 60
                     */

                    private String code;
                    private String title;
                    private String cost;
                    private String tax_class_id;
                    private String text;
                    private String tax;

                    public void setTax(String tax) {
                        this.tax = tax;
                    }

                    public String getTax() {
                        return tax;
                    }

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

                    public String getCost() {
                        return cost;
                    }

                    public void setCost(String cost) {
                        this.cost = cost;
                    }

                    public String getTax_class_id() {
                        return tax_class_id;
                    }

                    public void setTax_class_id(String tax_class_id) {
                        this.tax_class_id = tax_class_id;
                    }

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }
                }

                public static class Weight5Bean {
                    /**
                     * code : weight.weight_5
                     * title : India Shipping
                     * cost : 60.00
                     * tax_class_id : 9
                     * text : ₹ 60
                     */

                    private String code;
                    private String title;
                    private String cost;
                    private String tax_class_id;
                    private String text;
                    private String tax;

                    public void setTax(String tax) {
                        this.tax = tax;
                    }

                    public String getTax() {
                        return tax;
                    }

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

                    public String getCost() {
                        return cost;
                    }

                    public void setCost(String cost) {
                        this.cost = cost;
                    }

                    public String getTax_class_id() {
                        return tax_class_id;
                    }

                    public void setTax_class_id(String tax_class_id) {
                        this.tax_class_id = tax_class_id;
                    }

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }
                }
            }
        }
    }
}
