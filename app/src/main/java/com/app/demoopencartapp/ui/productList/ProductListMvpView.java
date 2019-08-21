package com.app.demoopencartapp.ui.productList;

import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface ProductListMvpView extends MvpView {

    void getProducts(List<CategoriesProductsResponse.ProductBean> product, int total_qty);
    void openSortUI();
    void openProductDetails(String pro_id);
    void checkWish(CategoriesProductsResponse.ProductBean productBean,int pos);
    void openLoginActivity();
    void addToCart(String product_id, String quantity);
    void addToCartDone(int cart_count);
    void openCartActivity();
    void addWish(String product_id,String product_option_value_id,String product_option_id);
    void addWishDone(String product_id,String product_option_value_id,String wishlist_id);
    void removeWishDone(String product_id,String product_option_value_id);
}
