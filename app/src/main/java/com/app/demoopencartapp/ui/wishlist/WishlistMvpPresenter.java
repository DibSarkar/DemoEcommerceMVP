package com.app.demoopencartapp.ui.wishlist;

import com.app.demoopencartapp.data.network.models.WishlistResponse;
import com.app.demoopencartapp.shared.base.MvpPresenter;

import java.util.ArrayList;

public interface WishlistMvpPresenter <V extends WishlistMvpView> extends MvpPresenter<V> {

    void onGetWishlist();
    void onLoadProducts();
    void onOpenProductDetails(String pro_id);
    void onAddtoCart(String product_id, String quantity, ArrayList<Integer> product_option_id);
    void onDeleteWish(String wishlist_id, int pos, WishlistResponse.ProductListBean item);

}
