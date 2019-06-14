package com.app.demoopencartapp.ui.home;

import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void getAllCategories(List<AllCategoriesResponse.DataBean> data);
    void logoutDone();
    void getHomeProducts(List<HomeProductsResponse.TopBannerBean> topBanner, List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes);
    void openProductDetails(String product_id);
}
