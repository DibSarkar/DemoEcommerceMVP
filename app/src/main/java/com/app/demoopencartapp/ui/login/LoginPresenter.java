package com.app.demoopencartapp.ui.login;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.RegisterResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.ui.register.RegisterMvpPresenter;
import com.app.demoopencartapp.ui.register.RegisterMvpView;
import com.app.demoopencartapp.ui.register.RegisterPresenter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoginPresenter <V extends LoginMvpView> extends BasePresenter<V>
        implements LoginMvpPresenter<V> {

    public static final String TAG = LoginPresenter.class.getSimpleName();

    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
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
    public void onConfirmLogin(String email, String password) {


        if(!email.equals("")){
            if(checkEmail(email))
            {

            if(!password.equals("")) {

                getMvpView().confirmLogin(email,password);
            }
            else {
                getMvpView().showMessage("Enter your password");
            }

            }
            else
            {
                getMvpView().showMessage("Invalid email-id");

            }
        }
        else {
            getMvpView().showMessage("Enter your email-id");
        }

    }

    @Override
    public void onLogin(String email, String password,String device_type,String device_token) {

        RequestBody email1 = RequestBody.create(MediaType.parse("multipart/form-data"), email);

        RequestBody password1 = RequestBody.create(MediaType.parse("multipart/form-data"), password);

        RequestBody device_type1 = RequestBody.create(MediaType.parse("multipart/form-data"), device_type);
        RequestBody device_token1 = RequestBody.create(MediaType.parse("multipart/form-data"), device_token);

        getMvpView().showLoading();
        getDataManager().login(email1,password1,device_type1, device_token1).enqueue(new Callback<RegisterResponse>() {
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
                            getMvpView().loginDone();

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
}
