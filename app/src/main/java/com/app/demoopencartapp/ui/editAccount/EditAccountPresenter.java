package com.app.demoopencartapp.ui.editAccount;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.GetAccountInfoResponse;
import com.app.demoopencartapp.data.network.models.MessageResponse;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.data.network.models.RegisterResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.ui.myaccount.MyAccountMvpPresenter;
import com.app.demoopencartapp.ui.myaccount.MyAccountMvpView;
import com.app.demoopencartapp.ui.myaccount.MyAccountPresenter;
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

public class EditAccountPresenter <V extends EditAccountMvpView> extends BasePresenter<V>
        implements EditAccountMvpPresenter<V> {

    public static final String TAG = EditAccountPresenter.class.getSimpleName();

    @Inject
    public EditAccountPresenter(DataManager dataManager) {
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
    public void onGetInfo() {
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


        getMvpView().showLoading();
        getDataManager().getAccountInfo(Constants.API_TOKEN,user_id).enqueue(new Callback<GetAccountInfoResponse>() {
            @Override
            public void onResponse(Call<GetAccountInfoResponse> call, Response<GetAccountInfoResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().getInfo(response.body().getResponseData().getFirstname(),response.body().getResponseData().getLastname(),response.body().getResponseData().getEmail(),response.body().getResponseData().getTelephone(),response.body().getResponseData().getGstin(),response.body().getResponseData().getNewsletter());

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
            public void onFailure(Call<GetAccountInfoResponse> call, Throwable t) {
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
    public void onSubmitEditAccount(String firstname, String lastname, String email, String telephone, String gstin, String newsletter) {
        RequestBody gstin1;
        RequestBody customer_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getCurrentUserId());

        RequestBody fname = RequestBody.create(MediaType.parse("multipart/form-data"), firstname);
        RequestBody lname = RequestBody.create(MediaType.parse("multipart/form-data"), lastname);
        RequestBody email1 = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody telephone1 = RequestBody.create(MediaType.parse("multipart/form-data"), telephone);
        RequestBody newsletter1 = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(newsletter));
        if(!gstin.equals("")) {
            gstin1 = RequestBody.create(MediaType.parse("multipart/form-data"), gstin);
        }
        else {
            gstin1 = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }

        getMvpView().showLoading();
        getDataManager().updateAccount(Constants.API_TOKEN,customer_id,fname, lname, email1, telephone1,gstin1,newsletter1).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().showMessage(response.body().getResponseText());



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

    @Override
    public void onConfirmEdit(String firstname, String lastname, String email, String telephone, String gstin, int newsletter) {
        if(!firstname.equals(""))
        {

            if(!lastname.equals(""))
            {
                if(!email.equals(""))
                {
                    if(checkEmail(email)) {

                        if (!telephone.equals("")) {
                            if(telephone.length()==10) {
                                getMvpView().confirmUpdate(firstname,lastname,email,telephone,gstin,newsletter);
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

    @Override
    public void onShowChangePass() {

        getMvpView().showChangePassDialog();

    }
}
