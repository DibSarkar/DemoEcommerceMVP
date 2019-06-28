package com.app.demoopencartapp.ui.reviews;

import com.app.demoopencartapp.data.network.models.ReviewsResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface ReviewsListMvpView extends MvpView {

    void getReviews(List<ReviewsResponse.ReviewListBean> reviewList);
}
