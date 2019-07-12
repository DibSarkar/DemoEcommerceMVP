package com.app.demoopencartapp.ui.editAccount;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ChangePassDialogPresenter <V extends ChangePassDialogMvpView> extends BasePresenter<V>
        implements ChangePassDialogMvpPresenter<V> {

    public static final String TAG = ChangePassDialogPresenter.class.getSimpleName();

    @Inject
    public ChangePassDialogPresenter(DataManager dataManager,
                                     SchedulerProvider schedulerProvider,
                                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
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


        getCompositeDisposable().add(getDataManager().changePass(Constants.API_TOKEN,user_id,password)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableCompletableObserver() {
                                   @Override
                                   public void onComplete() {
                                       getMvpView().hideLoading();
                                       getMvpView().showMessage("Password changed successfully");
                                       getMvpView().changePassDone();
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       getMvpView().hideLoading();
                                       getMvpView().showMessage(e.getMessage());

                                   }
                               }
                ));

    }
}
