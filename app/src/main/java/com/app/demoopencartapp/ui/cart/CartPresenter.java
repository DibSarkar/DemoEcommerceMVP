package com.app.demoopencartapp.ui.cart;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.CartListResponse;
import com.app.demoopencartapp.data.network.models.MessageResponse;
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

public class CartPresenter <V extends CartMvpView> extends BasePresenter<V>
        implements CartMvpPresenter<V> {

    public static final String TAG = CartPresenter.class.getSimpleName();

    @Inject
    public CartPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onGetCartList() {
        RequestBody user_id,session_id;
        if(getDataManager().getCurrentUserId()!=null) {

            if (!getDataManager().getCurrentUserId().equals("")) {

                user_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getCurrentUserId());
                session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

            }
            else {
                user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
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

            }
        }
        else {
            user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
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
        }

        getMvpView().showLoading();
        getDataManager().getCartList(Constants.API_TOKEN,user_id,session_id).enqueue(new Callback<CartListResponse>() {
            @Override
            public void onResponse(Call<CartListResponse> call, Response<CartListResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            if(response.body().getCartlist().getProducts().size()>0) {
                                getMvpView().getCartList(response.body().getCartlist().getProducts(),response.body().getTotal(),response.body().getSubtotal(),response.body().getTaxdata());
                            }
                            else {
                                getMvpView().getCartList(new ArrayList<CartListResponse.CartlistBean.ProductsBean>(), "","",null);
                            }

                        } else {
                            getMvpView().getCartList(new ArrayList<CartListResponse.CartlistBean.ProductsBean>(), "","",null);
                            getMvpView().showMessage(response.body().getResponseText());

                        }
                    } else {
                        getMvpView().getCartList(new ArrayList<CartListResponse.CartlistBean.ProductsBean>(), "","",null);
                        getMvpView().onError(response.code() + ":" + response.message());
                    }

                } else {
                    getMvpView().getCartList(new ArrayList<CartListResponse.CartlistBean.ProductsBean>(), "","",null);

                    getMvpView().onError(response.code() + ":" + response.message());
                }
            }

            @Override
            public void onFailure(Call<CartListResponse> call, Throwable t) {
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
    public void onUpdateCart(String cart_id, int quantity) {

       RequestBody cart_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), cart_id);
        RequestBody quantity1 = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(quantity));


        getMvpView().showLoading();
        getDataManager().updateCart(Constants.API_TOKEN,cart_id1,quantity1).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {

                            getMvpView().showMessage(response.body().getResponseText());
                            getMvpView().updateDone();

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
            public void onFailure(Call<MessageResponse> call, Throwable t) {
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
    public void onCartDelete(String cart_id) {
        RequestBody cart_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), cart_id);


        getMvpView().showLoading();
        getDataManager().deleteCart(Constants.API_TOKEN,cart_id1).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponseCode() == 1) {

                            getMvpView().showMessage(response.body().getResponseText());
                            getMvpView().updateDone();

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
            public void onFailure(Call<MessageResponse> call, Throwable t) {
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
    public void onOpenProductDetails(String product_id) {

        getMvpView().openProductDetails(product_id);

    }

    @Override
    public void onOpenCheckoutAsUI() {

        getMvpView().openCheckoutAsUI(getDataManager().getCurrentUserId());

    }

    @Override
    public void onOpenLogin() {


        getMvpView().openLogin();
    }


}
