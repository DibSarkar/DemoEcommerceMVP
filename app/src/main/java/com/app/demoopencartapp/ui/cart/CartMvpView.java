package com.app.demoopencartapp.ui.cart;

import com.app.demoopencartapp.data.network.models.CartListResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface CartMvpView extends MvpView {

    void getCartList(List<CartListResponse.CartlistBean.ProductsBean> products, String total, String subtotal, List<Integer> taxdata);
    void updateDone();
    void openProductDetails(String pro_id);
    void openCheckoutAsUI(String user_id);
    void openLogin();


}
