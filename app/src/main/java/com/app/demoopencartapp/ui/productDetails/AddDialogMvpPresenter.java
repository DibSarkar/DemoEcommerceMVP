package com.app.demoopencartapp.ui.productDetails;

import com.app.demoopencartapp.shared.base.MvpPresenter;

public interface AddDialogMvpPresenter <V extends AddRateDialogMvpView> extends MvpPresenter<V> {

    void onSendRating(String name, String review, float rating, String product_id);
    void onConfirmSendRating(String name, String review, float rating, String product_id);
    void onCloseDialog();

}
