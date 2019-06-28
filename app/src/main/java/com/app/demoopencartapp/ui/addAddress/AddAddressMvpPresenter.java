package com.app.demoopencartapp.ui.addAddress;

import com.app.demoopencartapp.shared.base.MvpPresenter;

public interface AddAddressMvpPresenter <V extends AddAddressMvpView> extends MvpPresenter<V> {

    void onGetAllCountries();
    void onGetStateByCountry(String country_id);
    void onConfirmAddress(String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin);
    void onAddAddress(String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin);


}
