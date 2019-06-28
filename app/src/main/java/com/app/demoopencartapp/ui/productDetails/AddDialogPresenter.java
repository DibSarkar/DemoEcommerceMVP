package com.app.demoopencartapp.ui.productDetails;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AddRatingResponse;
import com.app.demoopencartapp.data.network.models.MessageResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class AddDialogPresenter <V extends AddRateDialogMvpView> extends BasePresenter<V>
        implements AddDialogMvpPresenter<V> {

    public static final String TAG = AddDialogPresenter.class.getSimpleName();

    @Inject
    public AddDialogPresenter(DataManager dataManager,
                              SchedulerProvider schedulerProvider,
                              CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }



    @Override
    public void onSendRating(String name, String review, float rating, String product_id) {


        RequestBody customer_id;
        if(getDataManager().getCurrentUserId()!=null) {

            if (!getDataManager().getCurrentUserId().equals("")) {

                customer_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getCurrentUserId());
            }
            else {
                customer_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

            }
        }
        else {
            customer_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }
        RequestBody name1 = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody review1 = RequestBody.create(MediaType.parse("multipart/form-data"), review);
        RequestBody rating1 = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(rating));
        RequestBody productid1 = RequestBody.create(MediaType.parse("multipart/form-data"), product_id);



        getMvpView().showLoading();
        getDataManager().addRating(Constants.API_TOKEN,productid1,name1,review1,rating1,customer_id).enqueue(new Callback<AddRatingResponse>() {
            @Override
            public void onResponse(Call<AddRatingResponse> call, Response<AddRatingResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().showMessage(response.body().getResponseText());
                            getMvpView().ratingDone(response.body().getRating(),response.body().getReviews());
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
            public void onFailure(Call<AddRatingResponse> call, Throwable t) {
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
    public void onConfirmSendRating(String name, String review, float rating, String product_id) {
        if(!name.equals(""))
        {
            if(!review.equals(""))
            {
                if((review.length()>=25)&&(review.length()<=1000)) {

                    if (rating != 0) {

                        getMvpView().sendRating(name, review, rating,product_id);
                    } else {
                        getMvpView().showMessage("Please give rating");
                    }
                }
                else {
                    getMvpView().showMessage("Warning: Review Text must be between 25 and 1000 characters!");
                }
            }

            else {
                getMvpView().showMessage("Please enter your review");
            }
        }
        else {
            getMvpView().showMessage("Please enter name");
        }
    }

    @Override
    public void onCloseDialog() {


        getMvpView().closeDialog();
    }
}
