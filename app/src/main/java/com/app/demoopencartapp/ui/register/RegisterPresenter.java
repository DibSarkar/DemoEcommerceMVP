package com.app.demoopencartapp.ui.register;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.RegisterResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.ui.home.MainMvpPresenter;
import com.app.demoopencartapp.ui.home.MainMvpView;
import com.app.demoopencartapp.ui.home.MainPresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RegisterPresenter <V extends RegisterMvpView> extends BasePresenter<V>
        implements RegisterMvpPresenter<V> {

    public static final String TAG = RegisterPresenter.class.getSimpleName();

    @Inject
    public RegisterPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );


    private boolean checkEmail(String m) {
        return EMAIL_ADDRESS_PATTERN.matcher(m).matches();
    }


    @Override
    public void onSubmitRegistration(String firstName, String lastName, String email, String telephone, String password, int newsletter, String gstin, String device_type, String device_token) {
        RequestBody gstin1;
        RequestBody fname = RequestBody.create(MediaType.parse("multipart/form-data"), firstName);
        RequestBody lname = RequestBody.create(MediaType.parse("multipart/form-data"), lastName);
        RequestBody email1 = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody telephone1 = RequestBody.create(MediaType.parse("multipart/form-data"), telephone);
        RequestBody password1 = RequestBody.create(MediaType.parse("multipart/form-data"), password);
        RequestBody newsletter1 = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(newsletter));
       if(!gstin.equals("")) {
           gstin1 = RequestBody.create(MediaType.parse("multipart/form-data"), gstin);
       }
       else {
           gstin1 = RequestBody.create(MediaType.parse("multipart/form-data"), "");
       }
        RequestBody device_type1 = RequestBody.create(MediaType.parse("multipart/form-data"), device_type);
        RequestBody device_token1 = RequestBody.create(MediaType.parse("multipart/form-data"), device_token);

        getMvpView().showLoading();
        getDataManager().register(Constants.API_TOKEN,fname, lname, email1, telephone1, password1,newsletter1,gstin1, device_type1, device_token1).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().showMessage(response.body().getResponseText());
                            getDataManager().setCurrentUserId(String.valueOf(response.body().getResponseData().getId()));
                            getDataManager().setCurrentMobileNumber(response.body().getResponseData().getTelephone());
                            getDataManager().setCurrentUserEmail(response.body().getResponseData().getEmail());
                            getMvpView().registerDone();

                        } else {
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
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
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
    public void onConfirmRegistration(String firstName, String lastName, String email, String telephone, String password, int newsletter, String gstin,String oldpassword) {

        if(!firstName.equals(""))
        {

            if(!lastName.equals(""))
            {
                if(!email.equals(""))
                {
                    if(checkEmail(email)) {

                        if (!telephone.equals("")) {
                            if(telephone.length()==10) {
                                if (!password.equals("")) {
                                    if (!oldpassword.equals("")) {
                                        if (password.equals(oldpassword)) {

                                            getMvpView().confirmRegistrationDone(firstName,lastName,email,telephone,password,newsletter,gstin);

                                        }
                                        else {
                                            getMvpView().showMessage("Confirm password mismatches with password");
                                        }
                                    }
                                    else {
                                        getMvpView().showMessage("Enter your confirm password");
                                    }
                                }
                                else {
                                    getMvpView().showMessage("Enter your password");
                                }
                            }
                            else {
                                getMvpView().showMessage("Invalid telephone no");
                            }
                        }
                        else {
                            getMvpView().showMessage("Please enter your telephone no");
                        }
                    }
                    else {
                        getMvpView().showMessage("Invalid email-id");
                    }
                }
                else {
                    getMvpView().showMessage("Please enter your email-id");
                }
            }
            else {
                getMvpView().showMessage("Please enter your lastname");
            }
        }
        else {
            getMvpView().showMessage("Please enter your firstname");
        }


    }
}

