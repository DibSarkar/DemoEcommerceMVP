package com.app.demoopencartapp.ui.zoom;

import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.shared.base.MvpView;
import com.app.demoopencartapp.ui.productList.ProductListMvpView;

public interface ZoomMvpPresenter <V extends ZoomMvpView> extends MvpPresenter<V> {

    void onShowImage(String product_img);

}
