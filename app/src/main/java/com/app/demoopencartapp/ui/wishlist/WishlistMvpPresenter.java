package com.app.demoopencartapp.ui.wishlist;

import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpView;

public interface WishlistMvpPresenter <V extends WishlistMvpView> extends MvpPresenter<V> {

    void onGetWishlist();
    void onLoadProducts();
    void onOpenProductDetails(String pro_id);
}
