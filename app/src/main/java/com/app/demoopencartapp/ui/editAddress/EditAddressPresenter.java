package com.app.demoopencartapp.ui.editAddress;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.CountriesStatesResponse;
import com.app.demoopencartapp.data.network.models.MessageResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
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
        getMvpView().showLoading();
        getDataManager().getCountries(Constants.API_TOKEN).enqueue(new Callback<CountriesStatesResponse>() {
            @Override
            public void onResponse(Call<CountriesStatesResponse> call, Response<CountriesStatesResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().getAllCountries(response.body().getData());

                        } else {
                            getMvpView().showMessage(response.body().getResponseText());
                            getMvpView().getAllCountries(new ArrayList<CountriesStatesResponse.DataBean>());

                        }
                    } else {
                        getMvpView().getAllCountries(new ArrayList<CountriesStatesResponse.DataBean>());

                        getMvpView().onError(response.code() + ":" + response.message());
                    }

                } else {

                    getMvpView().getAllCountries(new ArrayList<CountriesStatesResponse.DataBean>());
                    getMvpView().onError(response.code() + ":" + response.message());
                }
            }

            @Override
            public void onFailure(Call<CountriesStatesResponse> call, Throwable t) {
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
                getMvpView().onError("Retrofit failure.Check LOG");
            }
        });


    }

    @Override
    public void onGetStateByCountry(String country_id) {
        RequestBody country_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), country_id);

        getMvpView().showLoading();
        getDataManager().getStates(Constants.API_TOKEN,country_id1).enqueue(new Callback<CountriesStatesResponse>() {
            @Override
            public void onResponse(Call<CountriesStatesResponse> call, Response<CountriesStatesResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().getAllStatesByCountry(response.body().getData());

                        } else {
                            getMvpView().showMessage(response.body().getResponseText());
                            getMvpView().getAllStatesByCountry(new ArrayList<CountriesStatesResponse.DataBean>());

                        }
                    } else {
                        getMvpView().getAllStatesByCountry(new ArrayList<CountriesStatesResponse.DataBean>());

                        getMvpView().onError(response.code() + ":" + response.message());
                    }

                } else {

                    getMvpView().getAllStatesByCountry(new ArrayList<CountriesStatesResponse.DataBean>());
                    getMvpView().onError(response.code() + ":" + response.message());
                }
            }

            @Override
            public void onFailure(Call<CountriesStatesResponse> call, Throwable t) {
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
                getMvpView().onError("Retrofit failure.Check LOG");
            }
        });
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
        getDataManager().editAddress(Constants.API_TOKEN,addressid1,customer_id,fname1,lname1,comp1,gstin1,address11,address22,city1,country_id1,state_id1,pin1,default_address1).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().showMessage(response.body().getResponseText());
                            getMvpView().openAddressBook();

                        } else {
                            getMvpView().updateDefaultAddress();
                            getMvpView().showMessage(response.body().getResponseText());

                        }
                    } else {

                        getMvpView().onError(response.code() + ":" + response.message());
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
                getMvpView().onError("Retrofit failure.Check LOG");
            }
        });

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
