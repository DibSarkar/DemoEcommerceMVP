package com.app.demoopencartapp.ui.myaccount;

import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpView;

public interface MyAccountMvpPresenter <V extends MyAccountMvpView> extends MvpPresenter<V> {

    void onOpenEditAccount();
    void onOpenAddressBook();
    void onOpenWishlist();
}
