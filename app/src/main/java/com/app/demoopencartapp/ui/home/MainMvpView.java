package com.app.demoopencartapp.ui.home;

import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void getAllCategories(List<AllCategoriesResponse.DataBean> data);
    void logoutDone();
    void getHomeProducts(List<HomeProductsResponse.TopBannerBean> topBanner, List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes, int total_qty);
    void openProductDetails(String product_id);
    void checkDealsWish(HomeProductsResponse.DealsOfTheDayBean dealsOfTheDayBean,int pos);
    void checkBestWish(HomeProductsResponse.BestSellerBean bestSellerBean,int pos);
    void checkTestingWish(HomeProductsResponse.TestingProductBean testingProductBean,int pos);
    void checkAnglesWish(HomeProductsResponse.AngelGrindesBean angelGrindesBean,int pos);
    void openLoginActivity();
    void addToCartDone(int cart_count);
    void setupUI(String currentUserId);
    void loadProducts(List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes);
    void loadSliderData(List<HomeProductsResponse.TopBannerBean> topBanner, List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand, List<HomeProductsResponse.DealsOfTheDayBean> dealsOfTheDay, List<HomeProductsResponse.BestSellerBean> bestSeller, List<HomeProductsResponse.TestingProductBean> testingProduct, List<HomeProductsResponse.AngelGrindesBean> angelGrindes);
    void loadOffersBrands(List<HomeProductsResponse.SliderBean> slider, List<HomeProductsResponse.BrandBean> brand);
    void addDealsWish(String product_id,String product_option_value_id,String product_option_id);
    void addBestSellingWish(String product_id,String product_option_value_id, String product_option_id);
    void addTestingWish(String product_id,String product_option_value_id, String product_option_id);
    void addAngleWish(String product_id,String product_option_value_id, String product_option_id);
    void addDealsWishDone(String product_id,String product_option_value_id,String wishlist_id);
    void addBestSellingDone(String product_id,String product_option_value_id,String wishlist_id);
    void addTestingDone(String product_id,String product_option_value_id,String wishlist_id);
    void addAngleDone(String product_id,String product_option_value_id,String wishlist_id);
    void removeDealsWishDone(String product_id,String product_option_value_id);
    void removeBestSellingWishDone(String product_id,String product_option_value_id);
    void removeTestingDone(String product_id,String product_option_value_id);
    void removeAngleDone(String product_id,String product_option_value_id);
}
