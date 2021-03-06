package com.app.demoopencartapp.ui.productList;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AddUpdateCartResponse;
import com.app.demoopencartapp.data.network.models.AddWishlistResponse;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
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

        getCompositeDisposable().add(getDataManager().getCategoryProducts(priority,order, Constants.API_TOKEN,sub_category_id1,user_id,session_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<CategoriesProductsResponse>() {
                    @Override
                    public void accept(CategoriesProductsResponse response) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                        if(response!=null)
                        {
                        if (response.getResponseCode() == 1) {
                            if (response.getProduct().size() > 0) {
                                getMvpView().getProducts(response.getProduct(), response.getTotal_qty());
                            } else {
                                getMvpView().getProducts(new ArrayList<CategoriesProductsResponse.ProductBean>(), 0);
                            }
                        }
                        else {
                            getMvpView().getProducts(new ArrayList<CategoriesProductsResponse.ProductBean>(), response.getTotal_qty());

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
            else {
                if(!productBean.getOptions().isEmpty())
                {
                    getMvpView().addWish(productBean.getProduct_id(),productBean.getOptions().get(0).getProduct_option_value().get(0).getProduct_option_value_id(),productBean.getOptions().get(0).getProduct_option_id());

                }
                else {
                    getMvpView().addWish(productBean.getProduct_id(),"","");

                }
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
    public void onConfirmAddCart(String product_id, String quantity, String stock) {

        if (!stock.equals("")) {
            if (stock.equals("In Stock")) {
                getMvpView().addToCart(product_id, quantity);
            } else {
                getMvpView().showMessage("Sorry!! This product is out of stock.");

            }
        }
    }

    @Override
    public void onOpenCartActivity() {

        getMvpView().openCartActivity();
    }

    @Override
    public void onAddWish(final String product_id,final String product_option_value_id, String product_option_id) {

        if(getMvpView().isNetworkConnected()) {
            Map<String, String> option = new HashMap<>();

            if (!product_option_id.equals("")) {
                option.put("option" + "[" + product_option_id + "]", product_option_value_id);
            }

            getMvpView().showLoading();
            getCompositeDisposable().add(getDataManager().addWish(Constants.API_TOKEN, getDataManager().getCurrentUserId(), product_id, option)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<AddWishlistResponse>() {
                        @Override
                        public void accept(AddWishlistResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response != null) {
                                if (response.getResponseCode() == 1) {
                                    getMvpView().showMessage(response.getResponseText());
                                    getMvpView().addWishDone(product_id,product_option_value_id,String.valueOf(response.getWishlist_id()));

                                } else {
                                    getMvpView().showMessage(response.getResponseText());

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
        else {
            getMvpView().showMessage("No internet connection");
        }


    }

    @Override
    public void onDeleteWish(final String product_id, final String product_option_value_id, String wishlist_id) {
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
                                           getMvpView().removeWishDone(product_id,product_option_value_id);

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
