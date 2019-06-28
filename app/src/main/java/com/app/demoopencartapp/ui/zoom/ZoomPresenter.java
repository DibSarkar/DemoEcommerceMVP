package com.app.demoopencartapp.ui.zoom;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpPresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpView;
import com.app.demoopencartapp.ui.productList.ProductListPresenter;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ZoomPresenter <V extends ZoomMvpView> extends BasePresenter<V> implements ZoomMvpPresenter<V> {

    public static final String TAG = ZoomPresenter.class.getSimpleName();

    @Inject
    public ZoomPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onShowImage(String product_img) {

        getMvpView().showImage(product_img);

    }
}
