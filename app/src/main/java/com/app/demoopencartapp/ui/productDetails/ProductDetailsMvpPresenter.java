package com.app.demoopencartapp.ui.productDetails;

import com.app.demoopencartapp.shared.base.MvpPresenter;

public interface ProductDetailsMvpPresenter  <V extends ProductDetailsMvpView> extends MvpPresenter<V> {

    void onGetProductDetails(String product_id);
    void onChangeImage(String url);
    void onHideDesc(boolean showDesc);
    void onHideSpec(boolean showSpec);
    void onLoadQuantities(String min,String max);
    void onOpenProductDetails(String pro_id);
    void onOpenZoom(String product_img);
}
