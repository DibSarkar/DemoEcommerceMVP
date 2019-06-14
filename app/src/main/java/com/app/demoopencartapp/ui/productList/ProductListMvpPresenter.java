package com.app.demoopencartapp.ui.productList;

import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.ui.login.LoginMvpView;

public interface ProductListMvpPresenter <V extends ProductListMvpView> extends MvpPresenter<V> {

    void onGetProductList(String priority,String order,String sub_category_id);
    void onOpenSort();
    void onOpenProductDetails(String pro_id);

}
