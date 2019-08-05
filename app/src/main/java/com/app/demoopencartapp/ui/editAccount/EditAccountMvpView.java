package com.app.demoopencartapp.ui.editAccount;

import com.app.demoopencartapp.shared.base.MvpView;

public interface EditAccountMvpView extends MvpView {

    void getInfo(String firstname, String lastname, String email, String telephone, String gstin, int newsletter);
    void confirmUpdate(String firstname, String lastname, String email, String telephone, String gstin, int newsletter);
    void showChangePassDialog();


}
