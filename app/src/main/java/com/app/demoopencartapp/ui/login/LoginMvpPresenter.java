package com.app.demoopencartapp.ui.login;

import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.ui.register.RegisterMvpView;

public interface LoginMvpPresenter  <V extends LoginMvpView> extends MvpPresenter<V> {

    void onConfirmLogin(String email, String password);
    void onLogin(String email, String password,String device_type,String device_token);
}
