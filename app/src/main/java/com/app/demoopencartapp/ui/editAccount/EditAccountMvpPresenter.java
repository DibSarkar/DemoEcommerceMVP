package com.app.demoopencartapp.ui.editAccount;

import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.ui.myaccount.MyAccountMvpView;

public interface EditAccountMvpPresenter <V extends EditAccountMvpView> extends MvpPresenter<V> {

    void onGetInfo();
    void onSubmitEditAccount(String fname, String lname, String email, String telephone, String gstin, String newsletter);
    void onConfirmEdit(String firstname, String lastname, String email, String telephone, String gstin, int newsletter);
    void onShowChangePass();

}
