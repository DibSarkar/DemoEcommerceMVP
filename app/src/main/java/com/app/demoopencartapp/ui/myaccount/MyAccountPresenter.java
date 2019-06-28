package com.app.demoopencartapp.ui.myaccount;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.ui.home.MainMvpPresenter;
import com.app.demoopencartapp.ui.home.MainMvpView;
import com.app.demoopencartapp.ui.home.MainPresenter;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MyAccountPresenter <V extends MyAccountMvpView> extends BasePresenter<V>
        implements MyAccountMvpPresenter<V> {

    public static final String TAG = MyAccountPresenter.class.getSimpleName();

    @Inject
    public MyAccountPresenter(DataManager dataManager,
                              SchedulerProvider schedulerProvider,
                              CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onOpenEditAccount() {

        getMvpView().openEditAccount();

    }

    @Override
    public void onOpenAddressBook() {
        getMvpView().openAddressBook();

    }

    @Override
    public void onOpenWishlist() {
        getMvpView().openWishlist();
    }
}
