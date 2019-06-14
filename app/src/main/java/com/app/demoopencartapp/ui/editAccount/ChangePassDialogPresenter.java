package com.app.demoopencartapp.ui.editAccount;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.MessageResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ChangePassDialogPresenter <V extends ChangePassDialogMvpView> extends BasePresenter<V>
        implements ChangePassDialogMvpPresenter<V> {

    public static final String TAG = ChangePassDialogPresenter.class.getSimpleName();

    @Inject
    public ChangePassDialogPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onChangePass(String pass, String confirm_pass) {
        if(!pass.equals(""))
        {
            if(!confirm_pass.equals(""))
            {
                if(pass.equals(confirm_pass))
                {
                    changePass(pass);
                }
                else {
                    getMvpView().showMessage("Password and confirm password mismatches.");
                }
            }
            else {
                getMvpView().showMessage("Please confirm your password");
            }
        }
        else {
            getMvpView().showMessage("Please enter your new password");
        }

    }

    private void changePass(String pass)
    {
        RequestBody user_id;
        if(getDataManager().getCurrentUserId()!=null) {

            if (!getDataManager().getCurrentUserId().equals("")) {

                user_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getCurrentUserId());
            }
            else {
                user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

            }
        }
        else {
            user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }
        RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), pass);



        getMvpView().showLoading();
        getDataManager().changePass(Constants.API_TOKEN,user_id,password).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().showMessage(response.body().getResponseText());
                            getMvpView().changePassDone();
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
}
