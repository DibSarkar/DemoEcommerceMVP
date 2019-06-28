package com.app.demoopencartapp.data.network.models;

import java.util.List;

public class AddressListResponse {


    /**
     * responseCode : 1
     * responseText : Successfully Found.
     * responseData : [{"address_id":"24","firstname":"hfdfhf","lastname":"dfhhh","company":"hghgh","gstin":"","address_1":"hfghfgh","address_2":"kolkata","postcode":"700091","city":"gfhfgh","zone_id":"1506","zone":"West Bengal","country_id":"99","country":"India","default_address":0}]
     */

    private int responseCode;
    private String responseText;
    private List<ResponseDataBean> responseData;

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

    public List<ResponseDataBean> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<ResponseDataBean> responseData) {
        this.responseData = responseData;
    }

    public static class ResponseDataBean {
        /**
         * address_id : 24
         * firstname : hfdfhf
         * lastname : dfhhh
         * company : hghgh
         * gstin :
         * address_1 : hfghfgh
         * address_2 : kolkata
         * postcode : 700091
         * city : gfhfgh
         * zone_id : 1506
         * zone : West Bengal
         * country_id : 99
         * country : India
         * default_address : 0
         */

        private String address_id;
        private String firstname;
        private String lastname;
        private String company;
        private String gstin;
        private String address_1;
        private String address_2;
        private String postcode;
        private String city;
        private String zone_id;
        private String zone;
        private String country_id;
        private String country;
        private int default_address;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getGstin() {
            return gstin;
        }

        public void setGstin(String gstin) {
            this.gstin = gstin;
        }

        public String getAddress_1() {
            return address_1;
        }

        public void setAddress_1(String address_1) {
            this.address_1 = address_1;
        }

        public String getAddress_2() {
            return address_2;
        }

        public void setAddress_2(String address_2) {
            this.address_2 = address_2;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZone_id() {
            return zone_id;
        }

        public void setZone_id(String zone_id) {
            this.zone_id = zone_id;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getDefault_address() {
            return default_address;
        }

        public void setDefault_address(int default_address) {
            this.default_address = default_address;
        }
    }
}
