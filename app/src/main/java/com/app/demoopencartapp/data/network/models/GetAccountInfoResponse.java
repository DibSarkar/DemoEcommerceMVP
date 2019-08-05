package com.app.demoopencartapp.data.network.models;

public class GetAccountInfoResponse extends BaseResponse {


    /**
     * responseCode : 1
     * responseText : Data Found.
     * responseData : {"firstname":"Dib","lastname":"Sarkar","email":"sarkardibivd@gmail.com","telephone":"8240379920","gstin":"1235","newsletter":"1"}
     */


    private ResponseDataBean responseData;

    public ResponseDataBean getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseDataBean responseData) {
        this.responseData = responseData;
    }

    public static class ResponseDataBean {
        /**
         * firstname : Dib
         * lastname : Sarkar
         * email : sarkardibivd@gmail.com
         * telephone : 8240379920
         * gstin : 1235
         * newsletter : 1
         */

        private String firstname;
        private String lastname;
        private String email;
        private String telephone;
        private String gstin;
        private int newsletter;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getGstin() {
            return gstin;
        }

        public void setGstin(String gstin) {
            this.gstin = gstin;
        }

        public int getNewsletter() {
            return newsletter;
        }

        public void setNewsletter(int newsletter) {
            this.newsletter = newsletter;
        }
    }
}
