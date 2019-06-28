package com.app.demoopencartapp.ui.wishlist;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class WishlistPresenter <V extends WishlistMvpView> extends BasePresenter<V>
        implements WishlistMvpPresenter<V> {

    public static final String TAG = WishlistPresenter.class.getSimpleName();

    @Inject
    public WishlistPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onGetWishlist() {
        getMvpView().showLoading();
        getDataManager().getWishlistProducts().enqueue(new Callback<CategoriesProductsResponse>() {
            @Override
            public void onResponse(Call<CategoriesProductsResponse> call, Response<CategoriesProductsResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            if(response.body().getProduct().size()>0) {
                                getMvpView().getWislist(response.body().getProduct(),response.body().getTotal_qty());
                            }
                            else {
                                getMvpView().getWislist(new ArrayList<CategoriesProductsResponse.ProductBean>(),0);
                            }

                        } else {
                            getMvpView().getWislist(new ArrayList<CategoriesProductsResponse.ProductBean>(),response.body().getTotal_qty());

                            getMvpView().showMessage(response.body().getResponseText());

                        }
                    } else {
                        getMvpView().getWislist(new ArrayList<CategoriesProductsResponse.ProductBean>(),0);

                        getMvpView().onError(response.code() + ":" + response.message());
                    }

                } else {
                    getMvpView().getWislist(new ArrayList<CategoriesProductsResponse.ProductBean>(),0);

                    getMvpView().onError(response.code() + ":" + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoriesProductsResponse> call, Throwable t) {
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
    public void onLoadProducts() {

        getMvpView().loadProducts();

    }

    @Override
    public void onOpenProductDetails(String pro_id) {

    }
}
