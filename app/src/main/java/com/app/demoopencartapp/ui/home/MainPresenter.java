package com.app.demoopencartapp.ui.home;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainPresenter <V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    public static final String TAG = MainPresenter.class.getSimpleName();

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onGetAllCategories() {

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().getAllCategories().enqueue(new Callback<AllCategoriesResponse>() {

                @Override
                public void onResponse(Call<AllCategoriesResponse> call, Response<AllCategoriesResponse> response) {
                    getMvpView().hideLoading();

                    if (response.isSuccessful()) {
                        if (response.body().getResponseCode() == 1) {

                            getMvpView().getAllCategories(response.body().getData());


                        }
                        else {
                            //  getMvpView().showMessage(response.body().getResponseText());
                            getMvpView().getAllCategories(new ArrayList<AllCategoriesResponse.DataBean>());
                        }
                    } else {
                        getMvpView().onError(response.code() + ":" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<AllCategoriesResponse> call, Throwable t) {
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
                    System.out.println("errre"+" "+t.getMessage());
                    getMvpView().onError("Retrofit failure.Check LOG"+t.getMessage());
                }
            });
        } else {
            getMvpView().showMessage("No internet connection");
        }


    }

    @Override
    public void onLogout() {
        getMvpView().showMessage("Logout successfully");
        getDataManager().destroyPref();
    }

    @Override
    public void onGetHomeProducts() {

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            getDataManager().getHomeProducts(Constants.API_TOKEN).enqueue(new Callback<HomeProductsResponse>() {

                @Override
                public void onResponse(Call<HomeProductsResponse> call, Response<HomeProductsResponse> response) {
                    getMvpView().hideLoading();

                    if (response.isSuccessful()) {
                        if (response.body().getResponseCode() == 1) {

                            getMvpView().getHomeProducts(response.body().getTopBanner(),response.body().getSlider(),response.body().getBrand(),response.body().getDealsOfTheDay(),response.body().getBestSeller(),response.body().getTestingProduct(),response.body().getAngelGrindes());

                        }
                        else {
                            getMvpView().getHomeProducts(response.body().getTopBanner(), new ArrayList<HomeProductsResponse.SliderBean>(),new ArrayList<HomeProductsResponse.BrandBean>(),new ArrayList<HomeProductsResponse.DealsOfTheDayBean>(),new ArrayList<HomeProductsResponse.BestSellerBean>(),new ArrayList<HomeProductsResponse.TestingProductBean>(),new ArrayList<HomeProductsResponse.AngelGrindesBean>());

                        }
                    } else {
                        getMvpView().onError(response.code() + ":" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<HomeProductsResponse> call, Throwable t) {
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
        } else {
            getMvpView().showMessage("No internet connection");
        }

    }

    @Override
    public void onOpenProductDetails(String product_id) {

        getMvpView().openProductDetails(product_id);
    }
}
