package com.app.demoopencartapp.ui.addressBook;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AddressListResponse;
import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.MessageResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.ui.addAddress.AddAddressMvpPresenter;
import com.app.demoopencartapp.ui.addAddress.AddAddressMvpView;
import com.app.demoopencartapp.ui.addAddress.AddAddressPresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class AddressBookPresenter <V extends AddressBookMvpView> extends BasePresenter<V>
        implements AddressBookMvpPresenter<V> {

    public static final String TAG = AddressBookPresenter.class.getSimpleName();

    @Inject
    public AddressBookPresenter(DataManager dataManager,
                                SchedulerProvider schedulerProvider,
                                CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onOpenAddAddress() {
        getMvpView().openAddAddress();
    }

    @Override
    public void onGetAddress() {

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            RequestBody customer_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getCurrentUserId());

            getDataManager().getAddress(Constants.API_TOKEN,customer_id).enqueue(new Callback<AddressListResponse>() {

                @Override
                public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                    getMvpView().hideLoading();

                    if (response.isSuccessful()) {
                        if (response.body().getResponseCode() == 1) {

                            getMvpView().getAddress(response.body().getResponseData());

                        }
                        else {
                            // getMvpView().showMessage(response.body().getResponseText());
                            getMvpView().getAddress(new ArrayList<AddressListResponse.ResponseDataBean>());
                        }
                    } else {
                        getMvpView().getAddress(new ArrayList<AddressListResponse.ResponseDataBean>());

                        getMvpView().onError(response.code() + ":" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<AddressListResponse> call, Throwable t) {
                    getMvpView().hideLoading();

                    Timber.tag(TAG).w(t);

                    if (t instanceof IOException) {
                        if (t.getMessage() != null) {
                            getMvpView().onError(t.getMessage());
                        } else {
                            getMvpView().onError("Network Failure");
                        }
                        return;
                    }
                    System.out.println("errre"+" "+t.getMessage());
                    getMvpView().onError("Retrofit failure.Check LOG"+t.getMessage());
                }
            });
        } else {
            getMvpView().showMessage("No internet connection");
        }


    }

    @Override
    public void onDeleteAddress(final AddressListResponse.ResponseDataBean responseDataBean, String address_id) {

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            RequestBody customer_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getCurrentUserId());
            RequestBody address_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), address_id);

            getDataManager().deleteAddress(Constants.API_TOKEN,customer_id,address_id1).enqueue(new Callback<MessageResponse>() {

                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    getMvpView().hideLoading();

                    if (response.isSuccessful()) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().showMessage(response.body().getResponseText());
                            getMvpView().deleteAddress(responseDataBean);

                        }
                        else {
                            getMvpView().showMessage(response.body().getResponseText());
                        }
                    } else {
                        getMvpView().onError(response.code() + ":" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    getMvpView().hideLoading();

                    Timber.tag(TAG).w(t);

                    if (t instanceof IOException) {
                        if (t.getMessage() != null) {
                            getMvpView().onError(t.getMessage());
                        } else {
                            getMvpView().onError("Network Failure");
                        }
                        return;
                    }
                    System.out.println("errre"+" "+t.getMessage());
                    getMvpView().onError("Retrofit failure.Check LOG"+t.getMessage());
                }
            });
        } else {
            getMvpView().showMessage("No internet connection");
        }

    }

    @Override
    public void onOpenEditAddress(String address_id, String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin) {

        getMvpView().openEditAddress(address_id, fname, lname, comp, address1, address2, city, pin, country_id, state_id, default_address, gstin);
    }
}
