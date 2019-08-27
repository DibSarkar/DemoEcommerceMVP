package com.app.demoopencartapp.ui.addressBook;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AddUpdateCartResponse;
import com.app.demoopencartapp.data.network.models.AddressListResponse;
import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
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
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
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
            getCompositeDisposable().add(getDataManager().getAddress(Constants.API_TOKEN,customer_id)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<AddressListResponse>() {
                        @Override
                        public void accept(AddressListResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response != null) {
                                if (response.getResponseCode() == 1) {

                                    getMvpView().getAddress(response.getResponseData());

                                }
                                else {
                                    // getMvpView().showMessage(response.body().getResponseText());
                                    getMvpView().getAddress(new ArrayList<AddressListResponse.ResponseDataBean>());
                                }
                            }
                            getMvpView().hideLoading();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            System.out.println("hjhdf" + throwable.getMessage());

                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();

                        }
                    }));

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

            getCompositeDisposable().add(getDataManager().deleteAddress(Constants.API_TOKEN,customer_id,address_id1)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribeWith(new DisposableCompletableObserver() {
                                       @Override
                                       public void onComplete() {
                                           getMvpView().hideLoading();
                                           getMvpView().showMessage("Address deleted successfully");
                                           getMvpView().deleteAddress(responseDataBean);

                                       }
                                       @Override
                                       public void onError(Throwable e) {
                                           getMvpView().hideLoading();
                                           getMvpView().showMessage(e.getMessage());

                                       }
                                   }
                              ));
        } else {
            getMvpView().showMessage("No internet connection");
        }

    }

    @Override
    public void onOpenEditAddress(String address_id, String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin) {

        getMvpView().openEditAddress(address_id, fname, lname, comp, address1, address2, city, pin, country_id, state_id, default_address, gstin);
    }

    @Override
    public void onSendDataCheckout(AddressListResponse.ResponseDataBean responseDataBean) {

        getMvpView().sendDataCheckout(responseDataBean);

    }
}
