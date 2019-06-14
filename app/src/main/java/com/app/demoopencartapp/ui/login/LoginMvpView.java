package com.app.demoopencartapp.ui.login;

import com.app.demoopencartapp.shared.base.MvpView;

public interface LoginMvpView extends MvpView {

    void confirmLogin(String email,String password);
    void loginDone();
}
