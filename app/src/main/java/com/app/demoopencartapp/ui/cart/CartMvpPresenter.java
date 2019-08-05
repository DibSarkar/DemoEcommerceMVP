package com.app.demoopencartapp.ui.cart;

import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.ui.home.MainMvpView;

public interface CartMvpPresenter <V extends CartMvpView> extends MvpPresenter<V> {

    void onGetCartList();
    void onUpdateCart(String cart_id, int quantity);
    void onCartDelete(String cart_id);
    void onOpenProductDetails(String product_id);
    void onOpenCheckoutAsUI();
    void onOpenLogin();
    void onClearSession();

}
