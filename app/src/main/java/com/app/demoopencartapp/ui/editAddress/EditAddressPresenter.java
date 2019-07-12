package com.app.demoopencartapp.ui.editAddress;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.CountriesStatesResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class EditAddressPresenter <V extends EditAddressMvpView> extends BasePresenter<V>
        implements EditAddressMvpPresenter<V> {

    public static final String TAG = EditAddressPresenter.class.getSimpleName();

    @Inject
    public EditAddressPresenter(DataManager dataManager,
                                SchedulerProvider schedulerProvider,
                                CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onGetAllCountries() {


        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getCompositeDisposable().add(getDataManager().getCountries(Constants.API_TOKEN)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<CountriesStatesResponse>() {
                        @Override
                        public void accept(CountriesStatesResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response != null) {
                                if (response.getResponseCode() == 1) {
                                    getMvpView().getAllCountries(response.getData());

                                } else {
                                    getMvpView().showMessage(response.getResponseText());
                                    getMvpView().getAllCountries(new ArrayList<CountriesStatesResponse.DataBean>());

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
    public void onGetStateByCountry(String country_id) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            RequestBody country_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), country_id);

            getCompositeDisposable().add(getDataManager().getStates(Constants.API_TOKEN,country_id1)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<CountriesStatesResponse>() {
                        @Override
                        public void accept(CountriesStatesResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response != null) {
                                if (response.getResponseCode() == 1) {
                                    getMvpView().getAllStatesByCountry(response.getData());

                                } else {
                                    getMvpView().showMessage(response.getResponseText());
                                    getMvpView().getAllStatesByCountry(new ArrayList<CountriesStatesResponse.DataBean>());

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
    public void onConfirmEditAddress(String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin, String address_id) {
        if(!fname.equals(""))
        {
            if(!lname.equals(""))
            {
                if(!address1.equals(""))
                {
                    if(!city.equals(""))
                    {

                        if(!country_id.equals(""))
                        {
                            if(!pin.equals(""))
                            {
                                if(!state_id.equals(""))
                                {
                                    if(!state_id.equals("0"))
                                    {
                                        getMvpView().confirmEditAddress(fname,lname,comp,address1,address2,city,pin,country_id,state_id,default_address,gstin,address_id);
                                    }
                                    else {
                                        getMvpView().showMessage("Please select region or state");
                                    }
                                }
                                else {
                                    getMvpView().showMessage("Please select region or state");
                                }
                            }
                            else {
                                getMvpView().showMessage("Please enter pin code");
                            }
                        }
                        else {
                            getMvpView().showMessage("Please select country");
                        }
                    }
                    else {
                        getMvpView().showMessage("Please enter city");
                    }
                }
                else {
                    getMvpView().showMessage("Please enter address1");
                }
            }
            else {
                getMvpView().showMessage("Please enter last name");
            }
        }
        else {
            getMvpView().showMessage("Please enter first name");
        }
    }

    @Override
    public void onEditAddress(String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin, String address_id) {
        if (getMvpView().isNetworkConnected()) {
        RequestBody comp1,address22,gstin1;
        RequestBody customer_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getCurrentUserId());
        RequestBody fname1 = RequestBody.create(MediaType.parse("multipart/form-data"), fname);
        RequestBody lname1 = RequestBody.create(MediaType.parse("multipart/form-data"), lname);
        RequestBody addressid1 = RequestBody.create(MediaType.parse("multipart/form-data"), address_id);
        if(!comp.equals("")) {
            comp1 = RequestBody.create(MediaType.parse("multipart/form-data"), comp);
        }
        else {
            comp1 = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }

        RequestBody address11 = RequestBody.create(MediaType.parse("multipart/form-data"), address1);
        if(!address11.equals(""))
        {
            address22 = RequestBody.create(MediaType.parse("multipart/form-data"), address2);

        }
        else {
            address22 = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }

        if(!gstin.equals(""))
        {
            gstin1 = RequestBody.create(MediaType.parse("multipart/form-data"), gstin);
        }
        else {
            gstin1 = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }
        RequestBody city1 = RequestBody.create(MediaType.parse("multipart/form-data"), city);
        RequestBody pin1 = RequestBody.create(MediaType.parse("multipart/form-data"), pin);
        RequestBody country_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), country_id);
        RequestBody state_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), state_id);
        RequestBody default_address1 = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(default_address));

        getMvpView().showLoading();

        getCompositeDisposable().add( getDataManager().editAddress(Constants.API_TOKEN,addressid1,customer_id,fname1,lname1,comp1,gstin1,address11,address22,city1,country_id1,state_id1,pin1,default_address1)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableCompletableObserver() {
                                   @Override
                                   public void onComplete() {
                                       getMvpView().hideLoading();
                                       getMvpView().showMessage("Address updated successfully");
                                       getMvpView().openAddressBook();

                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       getMvpView().hideLoading();
                                       getMvpView().showMessage(e.getMessage());

                                   }
                               }
                ));
        }
        else {
            getMvpView().showMessage("No internet connection");
        }
    }

    @Override
    public void onGetAddressData() {
        getMvpView().getAddressData();
    }

    @Override
    public void onUpdateTextUI() {
       getMvpView().updateTextUI();
    }


}
