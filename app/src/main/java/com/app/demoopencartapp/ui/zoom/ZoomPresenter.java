package com.app.demoopencartapp.ui.zoom;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpPresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpView;
import com.app.demoopencartapp.ui.productList.ProductListPresenter;

import javax.inject.Inject;

public class ZoomPresenter <V extends ZoomMvpView> extends BasePresenter<V> implements ZoomMvpPresenter<V> {

    public static final String TAG = ZoomPresenter.class.getSimpleName();

    @Inject
    public ZoomPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onShowImage(String product_img) {

        getMvpView().showImage(product_img);

    }
}
