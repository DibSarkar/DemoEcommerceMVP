package com.app.demoopencartapp.ui.cart;

import com.app.demoopencartapp.shared.base.DialogMvpView;

public interface Shipping_Fragment_MvpView extends DialogMvpView {

    void getSendShippingData(String title, double cost, double tax, int radio_selected);
    void closeDialog();



}

