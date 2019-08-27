package com.app.demoopencartapp.ui.checkout;

import com.app.demoopencartapp.data.network.models.PaymentMethodsResponse;
import com.app.demoopencartapp.data.network.models.ShippingMethodsResponse;
import com.app.demoopencartapp.shared.base.MvpView;

public interface CheckoutMvpView extends MvpView {

    void getPaymentMethods(PaymentMethodsResponse.PaymentBean shipping);
    void getShippingMethods(ShippingMethodsResponse.ShippingBean.WeightBean weight);
    void openAddress(int flag);
    void getAddress(String country_id, String zone_id, String address_1, String address_2, String zone, String city, String country, String postcode);
}
