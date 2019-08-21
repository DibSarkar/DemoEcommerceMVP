package com.app.demoopencartapp.ui.productList;

import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.shared.base.MvpPresenter;

public interface ProductListMvpPresenter <V extends ProductListMvpView> extends MvpPresenter<V> {

    void onGetProductList(String priority,String order,String sub_category_id);
    void onOpenSort();
    void onOpenProductDetails(String pro_id);
    void onCheckWish(CategoriesProductsResponse.ProductBean productBean,int pos);
    void onOpenLoginActivity();
    void onAddtoCart(String product_id, String quantity);
    void onConfirmAddCart(String product_id, String quantity, String stock);
    void onOpenCartActivity();
    void onAddWish(String product_id,String product_option_value_id,String product_option_id);
    void onDeleteWish(String product_id,String product_option_value_id,String wishlist_id);


}
