package com.app.demoopencartapp.ui.productList;

import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface ProductListMvpView extends MvpView {

    void getProducts(List<CategoriesProductsResponse.ProductBean> product);
    void openSortUI();
    void openProductDetails(String pro_id);
}
