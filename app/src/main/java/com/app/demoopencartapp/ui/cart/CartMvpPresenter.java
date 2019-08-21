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
    void onGetCountries();
    void onGetStateByCountry(String country_id);
    void onGetShippingMethods(String country_id,String zone_id);
    void onOpenShippingMethods();
    void onFinish();
    void onOpenCheckout();

}
