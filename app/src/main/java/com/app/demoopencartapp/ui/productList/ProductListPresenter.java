package com.app.demoopencartapp.ui.productList;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AddUpdateCartResponse;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
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

public class ProductListPresenter <V extends ProductListMvpView> extends BasePresenter<V>
        implements ProductListMvpPresenter<V> {

    public static final String TAG = ProductListPresenter.class.getSimpleName();

    @Inject
    public ProductListPresenter(DataManager dataManager,
                                SchedulerProvider schedulerProvider,
                                CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onGetProductList(String priority,String order,String sub_category_id) {
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
        RequestBody sub_category_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), sub_category_id);


        getMvpView().showLoading();
        getDataManager().getCategoryProducts(priority,order, Constants.API_TOKEN,sub_category_id1,user_id,session_id).enqueue(new Callback<CategoriesProductsResponse>() {
            @Override
            public void onResponse(Call<CategoriesProductsResponse> call, Response<CategoriesProductsResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            if(response.body().getProduct().size()>0) {
                                getMvpView().getProducts(response.body().getProduct(),response.body().getTotal_qty());
                            }
                            else {
                                getMvpView().getProducts(new ArrayList<CategoriesProductsResponse.ProductBean>(),0);
                            }

                        } else {
                            getMvpView().getProducts(new ArrayList<CategoriesProductsResponse.ProductBean>(),response.body().getTotal_qty());

                            getMvpView().showMessage(response.body().getResponseText());

                        }
                    } else {
                        getMvpView().getProducts(new ArrayList<CategoriesProductsResponse.ProductBean>(),0);

                        getMvpView().onError(response.code() + ":" + response.message());
                    }

                } else {
                    getMvpView().getProducts(new ArrayList<CategoriesProductsResponse.ProductBean>(),0);

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
    public void onOpenSort() {

        getMvpView().openSortUI();

    }

    @Override
    public void onOpenProductDetails(String pro_id) {

        getMvpView().openProductDetails(pro_id);

    }

    @Override
    public void onCheckWish(CategoriesProductsResponse.ProductBean productBean, int pos) {


        if(getDataManager().getCurrentUserId()!=null) {
            if (getDataManager().getCurrentUserId().equals("")) {
                getMvpView().checkWish(productBean,pos);
            }
        }
        else {
            getMvpView().checkWish(productBean,pos);
        }


    }

    @Override
    public void onOpenLoginActivity() {

        getMvpView().openLoginActivity();

    }

    @Override
    public void onAddtoCart(String product_id, String quantity) {
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
    public void onConfirmAddCart(String product_id, String quantity, String stock) {

        if (!stock.equals("")) {
            if (stock.equals("In Stock")) {
                getMvpView().addToCart(product_id, quantity);
            } else {
                getMvpView().showMessage("Sorry!! This product is out of stock.");

            }
        }
    }


}
