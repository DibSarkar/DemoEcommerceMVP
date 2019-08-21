package com.app.demoopencartapp.ui.wishlist;

import com.app.demoopencartapp.data.network.models.WishlistResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface WishlistMvpView extends MvpView {

    void loadProducts();
    void getWislist(List<WishlistResponse.ProductListBean> product, int total_qty);
    void openProductDetails(String pro_id);
    void addToCartDone(int cart_count);
    void removeWishDone(int pos, WishlistResponse.ProductListBean item);
}
