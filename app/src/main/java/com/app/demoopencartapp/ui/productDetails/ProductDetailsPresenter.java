package com.app.demoopencartapp.ui.productDetails;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ProductDetailsPresenter<V extends ProductDetailsMvpView> extends BasePresenter<V>
        implements ProductDetailsMvpPresenter<V>
{
    public static final String TAG = ProductDetailsPresenter.class.getSimpleName();

    @Inject
    public ProductDetailsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onGetProductDetails(String product_id) {

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
        RequestBody product_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), product_id);


        getMvpView().showLoading();
        getDataManager().getProductDetails(Constants.API_TOKEN,product_id1,user_id).enqueue(new Callback<ProductDetailsResponse>() {
            @Override
            public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            getMvpView().getProductDetails(response.body().getProduct(),response.body().getRelatedProduct());


                        } else {
                            getMvpView().getProductDetails(null,null);

                            getMvpView().showMessage(response.body().getResponseText());

                        }
                    } else {
                        getMvpView().getProductDetails(null,null);

                        getMvpView().onError(response.code() + ":" + response.message());
                    }

                } else {
                    getMvpView().getProductDetails(null,null);

                    getMvpView().onError(response.code() + ":" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {
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
    public void onChangeImage(String url) {

        getMvpView().changeImage(url);
    }

    @Override
    public void onHideDesc(boolean showDesc) {
        getMvpView().hideDesc(showDesc);

    }

    @Override
    public void onHideSpec(boolean showSpec) {
        getMvpView().hideSpec(showSpec);
    }

    @Override
    public void onLoadQuantities(String min, String max) {

        getMvpView().loadQuantities(min,max);

    }

    @Override
    public void onOpenProductDetails(String pro_id) {

        getMvpView().openProductDetails(pro_id);
    }

    @Override
    public void onOpenZoom(String product_img) {
        getMvpView().openZoomActivity(product_img);

    }
}
