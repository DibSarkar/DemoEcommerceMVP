package com.app.demoopencartapp.ui.addressBook;

import com.app.demoopencartapp.data.network.models.AddressListResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface AddressBookMvpView extends MvpView {

    void openAddAddress();
    void getAddress(List<AddressListResponse.ResponseDataBean> responseData);
    void deleteAddress(AddressListResponse.ResponseDataBean responseDataBean);
    void openEditAddress(String address_id,String fname,String lname,String comp,String address1,String address2,String city,String pin, String country_id, String state_id, int default_address,String gstin);
}
