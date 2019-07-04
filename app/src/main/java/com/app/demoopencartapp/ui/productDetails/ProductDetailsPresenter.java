package com.app.demoopencartapp.ui.productDetails;

import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.network.models.AddUpdateCartResponse;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.shared.base.BasePresenter;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
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
    public ProductDetailsPresenter(DataManager dataManager,
                                   SchedulerProvider schedulerProvider,
                                   CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onGetProductDetails(String product_id) {

        if(getMvpView().isNetworkConnected())
        {
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


        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().getProductDetails(Constants.API_TOKEN,product_id1,user_id,session_id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<ProductDetailsResponse>() {
                    @Override
                    public void accept(ProductDetailsResponse response) throws Exception {

                        if (!isViewAttached()) {
                            return;
                        }

                        if(response!=null) {
                            if (response.getResponseCode() == 1) {
                                getMvpView().getProductDetails(response.getProduct(), response.getRelatedProduct(), response.getTotal_qty());


                            } else {
                                getMvpView().getProductDetails(null, null, 0);

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

    @Override
    public void onShowAddOpenDialog() {

        getMvpView().showAddReviewDialog();

    }

    @Override
    public void onConfirmOpenReviews(String total_reviews) {

        if(!total_reviews.equals("")) {
            if (!total_reviews.equals("0")) {
                getMvpView().openReviewsActivity(total_reviews);
            }
        }
    }

    @Override
    public void onCheckWishlistByUser() {

        if(getDataManager().getCurrentUserId()!=null) {
            if (getDataManager().getCurrentUserId().equals("")) {
                getMvpView().checkWishlistByUser();


            }
        }
        else {
            getMvpView().checkWishlistByUser();
        }

    }

    @Override
    public void onOpenLoginActivity() {
        getMvpView().openLoginActvity();
    }

    @Override
    public void onCheckWish(ProductDetailsResponse.RelatedProductBean relatedProductBean, int pos) {
        if(getDataManager().getCurrentUserId()!=null) {
            if (getDataManager().getCurrentUserId().equals("")) {
                getMvpView().checkWish(relatedProductBean,pos);
            }
        }
        else {
            getMvpView().checkWish(relatedProductBean,pos);
        }

    }

    @Override
    public void onConfirmSimilarAddCart(String product_id, String quantity, String isStock) {

        if(!isStock.equals(""))
        {
            if(isStock.equals("In Stock"))
            {
               getMvpView().similarAddCart(product_id,quantity);
            }
            else {
                getMvpView().showMessage("Sorry!! This product is out of stock.");

            }
        }

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
    public void onConfirmAddCart(String product_id, String quantity, boolean isCustomizable, String product_option_id, String product_option_value_id, boolean isStock) {
        if(product_id!=null)
        {
            if(!quantity.equals(""))
            {
                if(isStock) {
                    getMvpView().singleAddCart(product_id, quantity, isCustomizable, product_option_id, product_option_value_id);
                }

                else {
                    getMvpView().showMessage("Sorry!! This product is out of stock.");
                }
            }
            else {
                getMvpView().showMessage("Please select quantity");
            }
        }
        else {
            getMvpView().showMessage("Some error occurred");
        }
    }


    @Override
    public void onCustomizableAddCart(String product_id, String quantity, boolean isCustomizable, String product_option_id, String product_option_value_id) {

        if(getMvpView().isNetworkConnected()) {
            Map<String, String> option = new HashMap<>();

            if (isCustomizable) {
                option.put("option" + "[" + product_option_id + "]", product_option_value_id);
            }

            getMvpView().showLoading();
            getCompositeDisposable().add(getDataManager().addCustomizableCart(Constants.API_TOKEN, getDataManager().getCurrentUserId(), product_id, quantity, getDataManager().getSessionId(), option)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<AddUpdateCartResponse>() {
                        @Override
                        public void accept(AddUpdateCartResponse response) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if (response != null) {
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
    public void onMultipleImages(List<ProductDetailsResponse.ProductBean.ImagesBean.ImageBean> image) {

        getMvpView().loadMultipleImages(image);
    }

    @Override
    public void onLoadVariations(List<ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean> product_option_value) {

        getMvpView().loadVariations(product_option_value);
    }

    @Override
    public void onLoadRelatedProducts(List<ProductDetailsResponse.RelatedProductBean> relatedProduct) {

        getMvpView().loadRelatedProducts(relatedProduct);
    }
}
