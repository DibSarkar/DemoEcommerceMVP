package com.app.demoopencartapp.ui.productDetails;

import com.app.demoopencartapp.shared.base.DialogMvpView;

public interface AddRateDialogMvpView extends DialogMvpView {

    void sendRating(String name, String review, float rating, String product_id);
    void closeDialog();
    void ratingDone(int rating, String reviews);


}
