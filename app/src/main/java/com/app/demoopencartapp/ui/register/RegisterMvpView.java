package com.app.demoopencartapp.ui.register;

import com.app.demoopencartapp.shared.base.MvpView;

public interface RegisterMvpView extends MvpView {

    void registerDone();
    void confirmRegistrationDone(String firstName, String lastName, String email, String telephone, String password, int newsletter, String gstin);
}
