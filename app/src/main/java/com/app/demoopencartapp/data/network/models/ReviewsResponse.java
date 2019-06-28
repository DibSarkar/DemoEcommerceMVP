package com.app.demoopencartapp.data.network.models;

import java.util.List;

public class ReviewsResponse {


    /**
     * responseCode : 1
     * responseText : Data Found.
     * reviewList : [{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"xbxbnddnddndbdbdbdbd","review":"sgsgsgd<br />\r\ngnfjfjjfjfjfjfjfjfjfjfjfjfjfjfj","rating":4,"date_added":"14/06/2019"},{"name":"hgfgh","review":"fghfghhfghffffffffffffffffffffffffffffffffg","rating":3,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"payal","review":"lorem ipsum","rating":3,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"payal","review":"lorem ipsum","rating":3,"date_added":"14/06/2019"},{"name":"payal","review":"lorem ipsum","rating":3,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"som","review":"lorem ipsum","rating":4,"date_added":"14/06/2019"},{"name":"payal","review":"lorem ipsum","rating":3,"date_added":"14/06/2019"},{"name":"payal","review":"lorem ipsum","rating":3,"date_added":"14/06/2019"}]
     */

    private int responseCode;
    private String responseText;
    private List<ReviewListBean> reviewList;

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

    public List<ReviewListBean> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewListBean> reviewList) {
        this.reviewList = reviewList;
    }

    public static class ReviewListBean {
        /**
         * name : sym
         * review : lorem ipsum
         * rating : 5
         * date_added : 14/06/2019
         */

        private String name;
        private String review;
        private int rating;
        private String date_added;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getDate_added() {
            return date_added;
        }

        public void setDate_added(String date_added) {
            this.date_added = date_added;
        }
    }
}
