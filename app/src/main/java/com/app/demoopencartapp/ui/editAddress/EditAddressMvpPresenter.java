package com.app.demoopencartapp.ui.editAddress;

import com.app.demoopencartapp.shared.base.MvpPresenter;

public interface EditAddressMvpPresenter <V extends EditAddressMvpView> extends MvpPresenter<V> {


    void onGetAllCountries();
    void onGetStateByCountry(String country_id);
    void onConfirmEditAddress(String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin, String address_id);
    void onEditAddress(String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin, String address_id);
    void onGetAddressData();
    void onUpdateTextUI();
}