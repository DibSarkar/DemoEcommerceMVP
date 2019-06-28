package com.app.demoopencartapp.ui.home;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AddUpdateCartResponse;
import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainPresenter <V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    public static final String TAG = MainPresenter.class.getSimpleName();

    @Inject
    public MainPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
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
        getDataManager().destroySessionPref();
        getMvpView().logoutDone();
    }

    @Override
    public void onGetHomeProducts() {

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            RequestBody user_id,session_id;
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

            if(getDataManager().getSessionId()!=null) {

                if (!getDataManager().getSessionId().equals("")) {

                    session_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getSessionId());
                }
                else {
                    session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

                }
            }
            else {
                session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            }
            getDataManager().getHomeProducts(Constants.API_TOKEN,user_id,session_id).enqueue(new Callback<HomeProductsResponse>() {

                @Override
                public void onResponse(Call<HomeProductsResponse> call, Response<HomeProductsResponse> response) {
                    getMvpView().hideLoading();

                    if (response.isSuccessful()) {
                        if (response.body().getResponseCode() == 1) {

                            getMvpView().getHomeProducts(response.body().getTopBanner(),response.body().getSlider(),response.body().getBrand(),response.body().getDealsOfTheDay(),response.body().getBestSeller(),response.body().getTestingProduct(),response.body().getAngelGrindes(),response.body().getTotal_qty());

                        }
                        else {
                            getMvpView().getHomeProducts(response.body().getTopBanner(), new ArrayList<HomeProductsResponse.SliderBean>(),new ArrayList<HomeProductsResponse.BrandBean>(),new ArrayList<HomeProductsResponse.DealsOfTheDayBean>(),new ArrayList<HomeProductsResponse.BestSellerBean>(),new ArrayList<HomeProductsResponse.TestingProductBean>(),new ArrayList<HomeProductsResponse.AngelGrindesBean>(),0);

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

    @Override
    public void onCheckDealsWish(HomeProductsResponse.DealsOfTheDayBean dealsOfTheDayBean, int pos) {


        if(getDataManager().getCurrentUserId()!=null) {
            if (getDataManager().getCurrentUserId().equals("")) {
                getMvpView().checkDealsWish(dealsOfTheDayBean,pos);
            }
        }
        else {
            getMvpView().checkDealsWish(dealsOfTheDayBean,pos);
        }
    }

    @Override
    public void onCheckBestWish(HomeProductsResponse.BestSellerBean bestSellerBean, int pos) {
        if(getDataManager().getCurrentUserId()!=null) {
            if (getDataManager().getCurrentUserId().equals("")) {
                getMvpView().checkBestWish(bestSellerBean,pos);


            }
        }
        else {
            getMvpView().checkBestWish(bestSellerBean,pos);
        }
    }

    @Override
    public void onCheckTestingWish(HomeProductsResponse.TestingProductBean testingProductBean, int pos) {
        if(getDataManager().getCurrentUserId()!=null) {
            if (getDataManager().getCurrentUserId().equals("")) {
                getMvpView().checkTestingWish(testingProductBean,pos);


            }
        }
        else {
            getMvpView().checkTestingWish(testingProductBean,pos);
        }
    }

    @Override
    public void onCheckAngleWish(HomeProductsResponse.AngelGrindesBean angelGrindesBean, int pos) {
        if(getDataManager().getCurrentUserId()!=null) {
            if (getDataManager().getCurrentUserId().equals("")) {
                getMvpView().checkAnglesWish(angelGrindesBean,pos);


            }
        }
        else {
            getMvpView().checkAnglesWish(angelGrindesBean,pos);
        }
    }

    @Override
    public void onOpenLoginActivity() {
         getMvpView().openLoginActivity();
    }

    @Override
    public void onAddtoCart(String product_id, String quantity,ArrayList<Integer> product_option_id) {

        RequestBody user_id,session_id;
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

        if(getDataManager().getSessionId()!=null) {

            if (!getDataManager().getSessionId().equals("")) {

                session_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getSessionId());
            }
            else {
                session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

            }
        }
        else {
            session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }

        RequestBody product_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), product_id);

        RequestBody quantity1 = RequestBody.create(MediaType.parse("multipart/form-data"), quantity);

        getMvpView().showLoading();
        getDataManager().addCart(Constants.API_TOKEN,user_id,product_id1, quantity1,session_id).enqueue(new Callback<AddUpdateCartResponse>() {
            @Override
            public void onResponse(Call<AddUpdateCartResponse> call, Response<AddUpdateCartResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().showMessage(response.body().getResponseText());
                            getDataManager().setSessionId(String.valueOf(response.body().getSession_id()));
                            getMvpView().addToCartDone(response.body().getCartData());
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
            public void onFailure(Call<AddUpdateCartResponse> call, Throwable t) {
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
    public void onSetupUi() {

        getMvpView().setupUI(getDataManager().getCurrentUserId());
    }

    @Override
    public void onLoadProducts(List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes) {

        getMvpView().loadProducts(slider,brand,dealsOfTheDay,bestSeller,testingProduct,angelGrindes);
    }

    @Override
    public void onLoadSliderData(List<HomeProductsResponse.TopBannerBean> topBanner, List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes) {

        getMvpView().loadSliderData(topBanner,slider,brand,dealsOfTheDay,bestSeller,testingProduct,angelGrindes);
    }

    @Override
    public void onLoadOffersBrands(List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand) {

        getMvpView().loadOffersBrands(slider,brand);
    }

}
