package com.app.demoopencartapp.ui.wishlist;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AddUpdateCartResponse;
import com.app.demoopencartapp.data.network.models.WishlistResponse;
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


        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            RequestBody user_id;
            if(getDataManager().getCurrentUserId()!=null) {

                if (!getDataManager().getCurrentUserId().equals("")) {

                    user_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getCurrentUserId());



                    getCompositeDisposable().add(getDataManager().getWishlistProducts(Constants.API_TOKEN, user_id)
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(new Consumer<WishlistResponse>() {
                                @Override
                                public void accept(WishlistResponse response) throws Exception {

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    if (response != null) {
                                        if (response.getResponseCode() == 1) {

                                            if(response.getProductList().size()>0) {

                                                getMvpView().getWislist(response.getProductList(),response.getTotal_qty());

                                            }
                                            else {
                                                getMvpView().getWislist(new ArrayList<WishlistResponse.ProductListBean>(),response.getTotal_qty());

                                            }
                                        } else {
                                            getMvpView().getWislist(new ArrayList<WishlistResponse.ProductListBean>(),response.getTotal_qty());
                                        }
                                    }
                                    getMvpView().hideLoading();
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    System.out.println("Error" + throwable.getMessage());

                                    if (!isViewAttached()) {
                                        return;
                                    }

                                    getMvpView().hideLoading();

                                }
                            }));
                }
            }

        } else {
            getMvpView().showMessage("No internet connection");
        }


    }

    @Override
    public void onLoadProducts() {

        getMvpView().loadProducts();

    }

    @Override
    public void onOpenProductDetails(String pro_id) {
        getMvpView().openProductDetails(pro_id);

    }

    @Override
    public void onAddtoCart(String product_id, String quantity, ArrayList<Integer> product_option_id) {
        if(getMvpView().isNetworkConnected()) {
            RequestBody user_id, session_id;
            if (getDataManager().getCurrentUserId() != null) {

                if (!getDataManager().getCurrentUserId().equals("")) {

                    user_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getCurrentUserId());
                } else {
                    user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

                }
            } else {
                user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            }

            if (getDataManager().getSessionId() != null) {

                if (!getDataManager().getSessionId().equals("")) {

                    session_id = RequestBody.create(MediaType.parse("multipart/form-data"), getDataManager().getSessionId());
                } else {
                    session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");

                }
            } else {
                session_id = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            }

            RequestBody product_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), product_id);

            RequestBody quantity1 = RequestBody.create(MediaType.parse("multipart/form-data"), quantity);

            getMvpView().showLoading();


            getCompositeDisposable().add(getDataManager().addCart(Constants.API_TOKEN,user_id,product_id1, quantity1,session_id)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<AddUpdateCartResponse>() {
                        @Override
                        public void accept(AddUpdateCartResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if(response!=null) {
                                if (response.getResponseCode() == 1) {
                                    getMvpView().showMessage(response.getResponseText());
                                    getDataManager().setSessionId(String.valueOf(response.getSession_id()));
                                    getMvpView().addToCartDone(response.getCartData());
                                } else {
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

                        }
                    }));
        }
        else {
            getMvpView().showMessage("No internet connection");
        }

    }

    @Override
    public void onDeleteWish(String wishlist_id, final int pos, final WishlistResponse.ProductListBean item) {
        if(getMvpView().isNetworkConnected()) {

            getMvpView().showLoading();
            RequestBody wishlist_id1 = RequestBody.create(MediaType.parse("multipart/form-data"), wishlist_id);

            getCompositeDisposable().add(getDataManager().removeWish(Constants.API_TOKEN, wishlist_id1)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribeWith(new DisposableCompletableObserver() {
                                       @Override
                                       public void onComplete() {
                                           getMvpView().hideLoading();
                                           getMvpView().showMessage("Item removed from your wishlist");
                                           getMvpView().removeWishDone(pos,item);

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
}
