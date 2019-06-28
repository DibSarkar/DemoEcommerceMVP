package com.app.demoopencartapp.ui.reviews;

import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpView;

public interface ReviewsListMvpPresenter <V extends ReviewsListMvpView> extends MvpPresenter<V> {

    void onGetReviews(String product_id);
}
