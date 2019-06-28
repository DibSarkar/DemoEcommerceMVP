package com.app.demoopencartapp.ui.addAddress;

import com.app.demoopencartapp.data.network.models.CountriesStatesResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface AddAddressMvpView extends MvpView {

    void getAllCountries(List<CountriesStatesResponse.DataBean> data);
    void getAllStatesByCountry(List<CountriesStatesResponse.DataBean> data);
    void confirmAddress(String fname,String lname,String comp,String address1,String address2,String city,String pin, String country_id, String state_id, int default_address,String gstin);
    void openAddressBook();
}
