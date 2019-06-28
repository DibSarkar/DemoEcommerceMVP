package com.app.demoopencartapp.ui.home;

import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.shared.base.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

public interface MainMvpPresenter  <V extends MainMvpView> extends MvpPresenter<V> {

    void onGetAllCategories();
    void onLogout();
    void onGetHomeProducts();
    void onOpenProductDetails(String product_id);
    void onCheckDealsWish(HomeProductsResponse.DealsOfTheDayBean dealsOfTheDayBean,int pos);
    void onCheckBestWish(HomeProductsResponse.BestSellerBean bestSellerBean,int pos);
    void onCheckTestingWish(HomeProductsResponse.TestingProductBean testingProductBean,int pos);
    void onCheckAngleWish(HomeProductsResponse.AngelGrindesBean angelGrindesBean,int pos);
    void onOpenLoginActivity();
    void onAddtoCart(String product_id, String quantity, ArrayList<Integer> product_option_id);
    void onSetupUi();
    void onLoadProducts(List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes);
    void onLoadSliderData(List<HomeProductsResponse.TopBannerBean> topBanner, List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes);
    void onLoadOffersBrands(List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand);

}
