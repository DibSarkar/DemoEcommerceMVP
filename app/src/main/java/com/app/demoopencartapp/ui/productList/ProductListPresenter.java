package com.app.demoopencartapp.ui.productList;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.RegisterResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.ui.login.LoginMvpPresenter;
import com.app.demoopencartapp.ui.login.LoginMvpView;
import com.app.demoopencartapp.ui.login.LoginPresenter;
import com.app.demoopencartapp.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

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
    public ProductListPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onGetProductList(String priority,String order,String sub_category_id) {
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
        RequestBody sub_category_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), sub_category_id);


        getMvpView().showLoading();
        getDataManager().getCategoryProducts(priority,order, Constants.API_TOKEN,sub_category_id1,user_id).enqueue(new Callback<CategoriesProductsResponse>() {
            @Override
            public void onResponse(Call<CategoriesProductsResponse> call, Response<CategoriesProductsResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            if(response.body().getProduct().size()>0) {
                                getMvpView().getProducts(response.body().getProduct());
                            }
                            else {
                                getMvpView().getProducts(new ArrayList<CategoriesProductsResponse.ProductBean>());
                            }

                        } else {
                            getMvpView().getProducts(new ArrayList<CategoriesProductsResponse.ProductBean>());

                            getMvpView().showMessage(response.body().getResponseText());

                        }
                    } else {
                        getMvpView().getProducts(new ArrayList<CategoriesProductsResponse.ProductBean>());

                        getMvpView().onError(response.code() + ":" + response.message());
                    }

                } else {
                    getMvpView().getProducts(new ArrayList<CategoriesProductsResponse.ProductBean>());

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


}
