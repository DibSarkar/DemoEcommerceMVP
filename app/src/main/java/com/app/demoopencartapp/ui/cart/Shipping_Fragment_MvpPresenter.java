package com.app.demoopencartapp.ui.cart;

import com.app.demoopencartapp.shared.base.MvpPresenter;

public interface Shipping_Fragment_MvpPresenter  <V extends Shipping_Fragment_MvpView> extends MvpPresenter<V> {

    void onSendShippingData(String title, double cost, double tax, int radio_selected, String weight_code);
    void onCloseDialog();
}
