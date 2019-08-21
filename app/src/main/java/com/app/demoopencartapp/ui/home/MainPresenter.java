package com.app.demoopencartapp.ui.home;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AddUpdateCartResponse;
import com.app.demoopencartapp.data.network.models.AddWishlistResponse;
import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.data.network.models.RegisterResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

            getCompositeDisposable().add(getDataManager().getAllCategories()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<AllCategoriesResponse>() {
                        @Override
                        public void accept(AllCategoriesResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if(response!=null) {
                                if (response.getResponseCode() == 1) {

                                    getMvpView().getAllCategories(response.getData());


                                } else {
                                    //  getMvpView().showMessage(response.body().getResponseText());
                                    getMvpView().getAllCategories(new ArrayList<AllCategoriesResponse.DataBean>());
                                }
                            }
                            getMvpView().hideLoading();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception{
                            System.out.println("hjhdf"+throwable.getMessage());

                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();

                            // handle the login error here

                        }
                    }));


        } else {
            getMvpView().showMessage("No internet connection");
        }


    }

    @Override
    public void onLogout() {
        getMvpView().showMessage("Logout successfully");
        getDataManager().destroyPref();
       // getDataManager().destroySessionPref();
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


            getCompositeDisposable().add(getDataManager().getHomeProducts(Constants.API_TOKEN,user_id,session_id)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<HomeProductsResponse>() {
                        @Override
                        public void accept(HomeProductsResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if(response!=null) {
                                if (response.getResponseCode() == 1) {

                                    getMvpView().getHomeProducts(response.getTopBanner(), response.getSlider(), response.getBrand(), response.getDealsOfTheDay(), response.getBestSeller(), response.getTestingProduct(), response.getAngelGrindes(), response.getTotal_qty());

                                } else {
                                    getMvpView().getHomeProducts(response.getTopBanner(), new ArrayList<HomeProductsResponse.SliderBean>(), new ArrayList<HomeProductsResponse.BrandBean>(), new ArrayList<HomeProductsResponse.DealsOfTheDayBean>(), new ArrayList<HomeProductsResponse.BestSellerBean>(), new ArrayList<HomeProductsResponse.TestingProductBean>(), new ArrayList<HomeProductsResponse.AngelGrindesBean>(), 0);
                                }
                            }
                            getMvpView().hideLoading();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception{
                            System.out.println("hjhdf"+throwable.getMessage());

                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();

                        }
                    }));

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
            else {
                if(!dealsOfTheDayBean.getOptions().isEmpty())
                {
                    getMvpView().addDealsWish(dealsOfTheDayBean.getProduct_id(),dealsOfTheDayBean.getOptions().get(0).getProduct_option_value().get(0).getProduct_option_value_id(),dealsOfTheDayBean.getOptions().get(0).getProduct_option_id());

                }
                else {
                    getMvpView().addDealsWish(dealsOfTheDayBean.getProduct_id(),"","");

                }
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
            else {
                if(!bestSellerBean.getOptions().isEmpty())
                {
                    getMvpView().addBestSellingWish(bestSellerBean.getProduct_id(),bestSellerBean.getOptions().get(0).getProduct_option_value().get(0).getProduct_option_value_id(),bestSellerBean.getOptions().get(0).getProduct_option_id());

                }
                else {
                    getMvpView().addBestSellingWish(bestSellerBean.getProduct_id(),"","");

                }

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
            else {
                if(!testingProductBean.getOptions().isEmpty())
                {
                    getMvpView().addTestingWish(testingProductBean.getProduct_id(),testingProductBean.getOptions().get(0).getProduct_option_value().get(0).getProduct_option_value_id(),testingProductBean.getOptions().get(0).getProduct_option_id());

                }
                else {
                    getMvpView().addTestingWish(testingProductBean.getProduct_id(),"","");

                }
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
            else {
                if(!angelGrindesBean.getOptions().isEmpty())
                {
                    getMvpView().addBestSellingWish(angelGrindesBean.getProduct_id(),angelGrindesBean.getOptions().get(0).getProduct_option_value().get(0).getProduct_option_value_id(),angelGrindesBean.getOptions().get(0).getProduct_option_id());

                }
                else {
                    getMvpView().addBestSellingWish(angelGrindesBean.getProduct_id(),"","");

                }
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

    @Override
    public void onAddDealsWish(final String product_id, final String product_option_value_id,String product_option_id) {
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
                                    getMvpView().addDealsWishDone(product_id,product_option_value_id,String.valueOf(response.getWishlist_id()));

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
    public void onAddBestSellerWish(final String product_id, final String product_option_value_id, String product_option_id) {
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
                                    getMvpView().addBestSellingDone(product_id,product_option_value_id,String.valueOf(response.getWishlist_id()));

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
    public void onAddTestingWish(final String product_id,final String product_option_value_id, String product_option_id) {
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
                                    getMvpView().addTestingDone(product_id,product_option_value_id,String.valueOf(response.getWishlist_id()));

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
    public void onAddAngleWish(final String product_id, final String product_option_value_id, String product_option_id) {
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
                                    getMvpView().addAngleDone(product_id,product_option_value_id,String.valueOf(response.getWishlist_id()));

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
    public void onDeleteDealsWish(final String product_id, final String product_option_value_id, String wishlist_id) {
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
                                           getMvpView().removeDealsWishDone(product_id,product_option_value_id);

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
    public void onDeleteBestSellingWish(final String product_id,final String product_option_value_id, String wishlist_id) {
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
                                           getMvpView().removeBestSellingWishDone(product_id,product_option_value_id);

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
    public void onDeleteTestingWish(final String product_id, final String product_option_value_id, String wishlist_id) {
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
                                           getMvpView().removeTestingDone(product_id,product_option_value_id);

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
    public void onDeleteAngleWish(final String product_id,final String product_option_value_id, String wishlist_id) {
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
                                           getMvpView().removeAngleDone(product_id,product_option_value_id);

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
