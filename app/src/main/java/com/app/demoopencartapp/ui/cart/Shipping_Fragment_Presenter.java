package com.app.demoopencartapp.ui.cart;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class Shipping_Fragment_Presenter <V extends Shipping_Fragment_MvpView> extends BasePresenter<V>
        implements Shipping_Fragment_MvpPresenter<V> {

    public static final String TAG = Shipping_Fragment_Presenter.class.getSimpleName();

    @Inject
    public Shipping_Fragment_Presenter(DataManager dataManager,
                              SchedulerProvider schedulerProvider,
                              CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSendShippingData(String title, double cost, double tax, int radio_selected, String weight_code) {

        getMvpView().getSendShippingData(title,cost,tax,radio_selected,weight_code);

    }

    @Override
    public void onCloseDialog() {

    }
}
