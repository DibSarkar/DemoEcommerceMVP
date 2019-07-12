package com.app.demoopencartapp.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReviewsResponse extends BaseResponse{


    /**
     * responseCode : 1
     * responseText : Data Found.
     * reviewList : [{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"xbxbnddnddndbdbdbdbd","review":"sgsgsgd<br />\r\ngnfjfjjfjfjfjfjfjfjfjfjfjfjfjfj","rating":4,"date_added":"14/06/2019"},{"name":"hgfgh","review":"fghfghhfghffffffffffffffffffffffffffffffffg","rating":3,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"payal","review":"lorem ipsum","rating":3,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"payal","review":"lorem ipsum","rating":3,"date_added":"14/06/2019"},{"name":"payal","review":"lorem ipsum","rating":3,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"sym","review":"lorem ipsum","rating":5,"date_added":"14/06/2019"},{"name":"som","review":"lorem ipsum","rating":4,"date_added":"14/06/2019"},{"name":"payal","review":"lorem ipsum","rating":3,"date_added":"14/06/2019"},{"name":"payal","review":"lorem ipsum","rating":3,"date_added":"14/06/2019"}]
     */



    @SerializedName("reviewList")
    @Expose
    private List<ReviewListBean> reviewList = null;
    public List<ReviewListBean> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewListBean> reviewList) {
        this.reviewList = reviewList;
    }

    public static class ReviewListBean  implements Serializable {
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
