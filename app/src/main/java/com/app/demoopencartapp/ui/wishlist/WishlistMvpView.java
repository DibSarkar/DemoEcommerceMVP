package com.app.demoopencartapp.ui.wishlist;

import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface WishlistMvpView extends MvpView {

    void loadProducts();
    void getWislist(List<CategoriesProductsResponse.ProductBean> product, int total_qty);
    void openProductDetails(String pro_id);
}
