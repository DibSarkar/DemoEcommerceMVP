package com.app.demoopencartapp.ui.home;

import com.app.demoopencartapp.shared.base.MvpPresenter;

public interface MainMvpPresenter  <V extends MainMvpView> extends MvpPresenter<V> {

    void onGetAllCategories();
    void onLogout();
    void onGetHomeProducts();
    void onOpenProductDetails(String product_id);

}
