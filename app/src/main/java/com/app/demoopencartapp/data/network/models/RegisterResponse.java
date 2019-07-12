package com.app.demoopencartapp.data.network.models;

public class RegisterResponse extends BaseResponse{


    /**
     * responseCode : 1
     * responseText : Successfully Register.
     * responseData : {"id":32,"name":"payal prachanda","email":"payal2@gmail.com","telephone":"9090909090"}
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
         * id : 32
         * name : payal prachanda
         * email : payal2@gmail.com
         * telephone : 9090909090
         */

        private int id;
        private String name;
        private String email;
        private String telephone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}
