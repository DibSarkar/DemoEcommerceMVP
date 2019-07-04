package com.app.demoopencartapp.ui.cart;

import android.util.Log;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.CartListResponse;
import com.app.demoopencartapp.data.network.models.MessageResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
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
        if(getMvpView().isNetworkConnected()) {
            RequestBody user_id, session_id;
            if (getDataManager().getCurrentUserId() != null) {

                if (!getDataManager().getCurrentUserId().equals("")) {

                    user_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getCurrentUserId());
                    session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

                } else {
                    user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                    if (getDataManager().getSessionId() != null) {

                        if (!getDataManager().getSessionId().equals("")) {

                            session_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getSessionId());
                        } else {
                            session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

                        }
                    } else {
                        session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                    }

                }
            } else {
                user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                if (getDataManager().getSessionId() != null) {

                    if (!getDataManager().getSessionId().equals("")) {

                        session_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getSessionId());
                    } else {
                        session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

                    }
                } else {
                    session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                }
            }

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager().getCartList(Constants.API_TOKEN, user_id, session_id)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<CartListResponse>() {
                        @Override
                        public void accept(CartListResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response != null) {
                                if (response.getResponseCode() == 1) {
                                    if (response.getCartlist().getProducts().size() > 0) {
                                        getMvpView().getCartList(response.getCartlist().getProducts(), response.getTotal(), response.getSubtotal(), response.getTaxdata());
                                    } else {
                                        getMvpView().getCartList(new ArrayList<CartListResponse.CartlistBean.ProductsBean>(), "", "", null);
                                    }

                                } else {
                                    getMvpView().getCartList(new ArrayList<CartListResponse.CartlistBean.ProductsBean>(), "", "", null);
                                    getMvpView().showMessage(response.getResponseText());

                                }
                            }
                            getMvpView().hideLoading();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            System.out.println("hjhdf" + throwable.getMessage());

                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();

                            // handle the login error here

                        }
                    }));


        }
        else {
            getMvpView().showMessage("No internet connection");
        }

    }

    @Override
    public void onUpdateCart(String cart_id, int quantity) {

        if(getMvpView().isNetworkConnected()) {
            RequestBody cart_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), cart_id);
            RequestBody quantity1 = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(quantity));
            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager().updateCart(Constants.API_TOKEN, cart_id1, quantity1)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribeWith(new DisposableCompletableObserver() {
                                   @Override
                                   public void onComplete() {
                                       getMvpView().hideLoading();
                                       getMvpView().showMessage("Cart updated successfully");
                                       getMvpView().updateDone();
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       getMvpView().hideLoading();
                                       getMvpView().showMessage(e.getMessage());

                                   }
                               }
                    ));

        }
        else {
            getMvpView().showMessage("No internet connection");
        }


    }


    @Override
    public void onCartDelete(String cart_id) {
        if(getMvpView().isNetworkConnected()) {
            RequestBody cart_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), cart_id);
            getMvpView().showLoading();
            getCompositeDisposable().add(getDataManager().deleteCart(Constants.API_TOKEN, cart_id1)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribeWith(new DisposableCompletableObserver() {
                                       @Override
                                       public void onComplete() {
                                           getMvpView().hideLoading();
                                           getMvpView().showMessage("Item deleted successfully");
                                           getMvpView().updateDone();
                                   }

                                       @Override
                                       public void onError(Throwable e) {
                                           getMvpView().hideLoading();
                                           getMvpView().showMessage(e.getMessage());

                                       }
                                   }
                    ));

        }
        else {
            getMvpView().showMessage("No internet connection");
        }

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
