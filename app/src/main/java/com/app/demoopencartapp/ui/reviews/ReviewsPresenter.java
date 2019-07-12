package com.app.demoopencartapp.ui.reviews;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.RegisterResponse;
import com.app.demoopencartapp.data.network.models.ReviewsResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpPresenter;
import com.app.demoopencartapp.ui.productList.ProductListMvpView;
import com.app.demoopencartapp.ui.productList.ProductListPresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
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



            getCompositeDisposable().add(getDataManager().getReviews(Constants.API_TOKEN,product_id1)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribeWith(new DisposableSingleObserver<ReviewsResponse>() {
                        @Override
                        public void onSuccess(ReviewsResponse reviewsResponse) {
                            if (reviewsResponse.getResponseCode() == 1) {
                                if (reviewsResponse.getReviewList().size() > 0) {
                                    getMvpView().getReviews(reviewsResponse.getReviewList());
                                } else {
                                    getMvpView().getReviews(new ArrayList<ReviewsResponse.ReviewListBean>());
                                }
                            }

                            getMvpView().hideLoading();
                        }

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().showMessage(e.getMessage());
                            getMvpView().getReviews(new ArrayList<ReviewsResponse.ReviewListBean>());
                            getMvpView().hideLoading();
                            // Network error
                        }
                    }));




        }



    }
}
