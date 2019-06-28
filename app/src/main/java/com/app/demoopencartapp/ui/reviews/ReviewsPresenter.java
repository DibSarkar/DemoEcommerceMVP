package com.app.demoopencartapp.ui.reviews;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.ReviewsResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpPresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpView;
import com.app.demoopencartapp.ui.productList.ProductListPresenter;
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

public class ReviewsPresenter <V extends ReviewsListMvpView> extends BasePresenter<V>
        implements ReviewsListMvpPresenter<V> {

    public static final String TAG = ReviewsPresenter.class.getSimpleName();

    @Inject
    public ReviewsPresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onGetReviews(String product_id) {
        if(product_id!=null)
        {


            RequestBody product_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), product_id);


            getMvpView().showLoading();
            getDataManager().getReviews(Constants.API_TOKEN,product_id1).enqueue(new Callback<ReviewsResponse>() {
                @Override
                public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                    getMvpView().hideLoading();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getResponseCode() == 1) {
                                if(response.body().getReviewList().size()>0) {
                                    getMvpView().getReviews(response.body().getReviewList());
                                }
                                else {
                                    getMvpView().getReviews(new ArrayList<ReviewsResponse.ReviewListBean>());
                                }

                            } else {
                                getMvpView().getReviews(new ArrayList<ReviewsResponse.ReviewListBean>());

                                getMvpView().showMessage(response.body().getResponseText());

                            }
                        } else {
                            getMvpView().getReviews(new ArrayList<ReviewsResponse.ReviewListBean>());

                            getMvpView().onError(response.code() + ":" + response.message());
                        }

                    } else {
                        getMvpView().getReviews(new ArrayList<ReviewsResponse.ReviewListBean>());

                        getMvpView().onError(response.code() + ":" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ReviewsResponse> call, Throwable t) {
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
}
