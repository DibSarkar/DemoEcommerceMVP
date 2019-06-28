package com.app.demoopencartapp.ui.editAddress;

import com.app.demoopencartapp.data.network.models.CountriesStatesResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface EditAddressMvpView extends MvpView {
    void getAllCountries(List<CountriesStatesResponse.DataBean> data);
    void getAllStatesByCountry(List<CountriesStatesResponse.DataBean> data);
    void confirmEditAddress(String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin, String address_id);
    void openAddressBook();
    void getAddressData();
    void updateTextUI();
    void updateDefaultAddress();

}
