package com.app.demoopencartapp.ui.checkout;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AddressListResponse;
import com.app.demoopencartapp.data.network.models.PaymentMethodsResponse;
import com.app.demoopencartapp.data.network.models.ShippingMethodsResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CheckoutPresenter <V extends CheckoutMvpView> extends BasePresenter<V>
        implements CheckoutMvpPresenter<V> {

    public static final String TAG = CheckoutPresenter.class.getSimpleName();

    @Inject
    public CheckoutPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onGetPaymentMethods(String country_id,String zone_id) {

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();


            getCompositeDisposable().add(getDataManager().getPaymentMethods(Constants.API_TOKEN,country_id,zone_id)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<PaymentMethodsResponse>() {
                        @Override
                        public void accept(PaymentMethodsResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response != null) {
                                if (response.getResponseCode() == 1) {
                                    getMvpView().getPaymentMethods(response.getPayment());

                                } else {
                                    getMvpView().showMessage(response.getResponseText());
                                    getMvpView().getPaymentMethods(null);

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
    public void onGetShippingMethods(String country_id, String zone_id) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();


            getCompositeDisposable().add(getDataManager().getShippingMethods(Constants.API_TOKEN,country_id,zone_id)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<ShippingMethodsResponse>() {
                        @Override
                        public void accept(ShippingMethodsResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response != null) {
                                if (response.getResponseCode() == 1) {
                                    getMvpView().getShippingMethods(response.getShipping().getWeight());

                                } else {
                                    getMvpView().showMessage(response.getResponseText());
                                    getMvpView().getShippingMethods(null);

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
    public void onOpenAddress(int flag) {

        getMvpView().openAddress(flag);
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

                                    if(response.getResponseData().size()>0) {
                                        for (int i = 0; i < response.getResponseData().size(); i++) {

                                            if(response.getResponseData().get(i).getDefault_address()==1)
                                            {
                                                getMvpView().getAddress(response.getResponseData().get(i).getCountry_id(),response.getResponseData().get(i).getZone_id(),response.getResponseData().get(i).getAddress_1(),response.getResponseData().get(i).getAddress_2(),response.getResponseData().get(i).getZone(),response.getResponseData().get(i).getCity(),response.getResponseData().get(i).getCountry(),response.getResponseData().get(i).getPostcode());
                                            }

                                        }

                                    }
                                    else {
                                        getMvpView().getAddress("","", "","","","","","");

                                    }


                                }
                                else {
                                    // getMvpView().showMessage(response.body().getResponseText());
                                    getMvpView().getAddress("","", "","","","","","");
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
}
