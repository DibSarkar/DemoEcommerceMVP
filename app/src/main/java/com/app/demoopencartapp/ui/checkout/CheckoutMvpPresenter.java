package com.app.demoopencartapp.ui.checkout;

import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.ui.cart.CartMvpView;

public interface CheckoutMvpPresenter <V extends CheckoutMvpView> extends MvpPresenter<V> {

    void onGetPaymentMethods(String country_id,String zone_id);
    void onGetShippingMethods(String country_id,String zone_id);
    void onOpenAddress(int flag);
    void onGetAddress();

}
