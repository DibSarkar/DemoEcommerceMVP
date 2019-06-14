package com.app.demoopencartapp.ui.register;

import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.ui.home.MainMvpView;

public interface RegisterMvpPresenter  <V extends RegisterMvpView> extends MvpPresenter<V> {

    void onSubmitRegistration(String firstName, String lastName, String email, String telephone, String password, int newsletter, String gstin,String device_type,String device_token);

    void onConfirmRegistration(String firstName, String lastName, String email, String telephone, String password, int newsletter, String gstin,String oldPassword);
}
