package com.app.demoopencartapp.ui.editAccount;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.GetAccountInfoResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class EditAccountPresenter <V extends EditAccountMvpView> extends BasePresenter<V>
        implements EditAccountMvpPresenter<V> {

    public static final String TAG = EditAccountPresenter.class.getSimpleName();

    @Inject
    public EditAccountPresenter(DataManager dataManager,
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
    public void onGetInfo() {
        if(getMvpView().isNetworkConnected())
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

        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager().getAccountInfo(Constants.API_TOKEN,user_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<GetAccountInfoResponse>() {
                    @Override
                    public void accept(GetAccountInfoResponse response) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                        if (response != null) {
                            if (response.getResponseCode() == 1) {
                                getMvpView().getInfo(response.getResponseData().getFirstname(),response.getResponseData().getLastname(),response.getResponseData().getEmail(),response.getResponseData().getTelephone(),response.getResponseData().getGstin(),response.getResponseData().getNewsletter());

                            } else {
                                getMvpView().showMessage(response.getResponseText());

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
    public void onSubmitEditAccount(String firstname, String lastname, String email, String telephone, String gstin, String newsletter) {
        if(getMvpView().isNetworkConnected())
        {
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

        getCompositeDisposable().add(getDataManager().updateAccount(Constants.API_TOKEN,customer_id,fname, lname, email1, telephone1,gstin1,newsletter1)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableCompletableObserver() {
                                   @Override
                                   public void onComplete() {
                                       getMvpView().hideLoading();
                                       getMvpView().showMessage("Account updated successfully");
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
