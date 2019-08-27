package com.app.demoopencartapp.ui.addressBook;

import com.app.demoopencartapp.data.network.models.AddressListResponse;
import com.app.demoopencartapp.data.network.models.RegisterResponse;
import com.app.demoopencartapp.shared.base.MvpPresenter;
import com.app.demoopencartapp.ui.addAddress.AddAddressMvpView;

public interface AddressBookMvpPresenter<V extends AddressBookMvpView> extends MvpPresenter<V> {

    void onOpenAddAddress();
    void onGetAddress();
    void onDeleteAddress(AddressListResponse.ResponseDataBean responseDataBean,String address_id);
    void onOpenEditAddress(String address_id,String fname,String lname,String comp,String address1,String address2,String city,String pin, String country_id, String state_id, int default_address,String gstin);
    void onSendDataCheckout(AddressListResponse.ResponseDataBean responseDataBean);
}
