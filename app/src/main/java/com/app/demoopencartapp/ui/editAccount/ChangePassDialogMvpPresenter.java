package com.app.demoopencartapp.ui.editAccount;

import com.app.demoopencartapp.shared.base.MvpPresenter;

public interface ChangePassDialogMvpPresenter <V extends ChangePassDialogMvpView> extends MvpPresenter<V> {

    void onChangePass(String pass, String confirm_pass);


}
