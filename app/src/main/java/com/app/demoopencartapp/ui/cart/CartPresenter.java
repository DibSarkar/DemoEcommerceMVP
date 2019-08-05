package com.app.demoopencartapp.ui.cart;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.CartListResponse;
import com.app.demoopencartapp.data.network.models.UpdateCartResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
            RequestBody session_id;
            RequestBody cart_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), cart_id);
            RequestBody quantity1 = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(quantity));
            if (!getDataManager().getSessionId().equals("")) {

                session_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getSessionId());
            } else {
                session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

            }
            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager().updateCart(Constants.API_TOKEN, cart_id1, quantity1,session_id)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<UpdateCartResponse>() {
                        @Override
                        public void accept(UpdateCartResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response != null) {
                                if (response.getResponseCode() == 1) {
                                    getMvpView().showMessage(response.getResponseText());
                                     getMvpView().cartUpdate(response.getCartData());
                                    } else {
                                    getMvpView().showMessage(response.getResponseText());                                    }

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
    public void onCartDelete(String cart_id) {
        if(getMvpView().isNetworkConnected()) {
            RequestBody session_id;
            if (!getDataManager().getSessionId().equals("")) {

                session_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getSessionId());
            } else {
                session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

            }
            RequestBody cart_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), cart_id);
            getMvpView().showLoading();
            getCompositeDisposable().add(getDataManager().deleteCart(Constants.API_TOKEN, cart_id1,session_id)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<UpdateCartResponse>() {
                @Override
                public void accept(UpdateCartResponse response) throws Exception {

                    if (!isViewAttached()) {
                        return;
                    }

                    if (response != null) {
                        if (response.getResponseCode() == 1) {
                            getMvpView().showMessage(response.getResponseText());
                            getMvpView().cartUpdate(response.getCartData());
                        } else {
                            getMvpView().showMessage(response.getResponseText());                                    }

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

    @Override
    public void onClearSession() {
    //    getDataManager().destroySessionPref();
        getMvpView().clearSession();
    }


}
