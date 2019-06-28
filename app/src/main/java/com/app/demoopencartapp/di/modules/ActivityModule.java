/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.app.demoopencartapp.di.modules;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;


import com.app.demoopencartapp.data.local_models.CartListBean;
import com.app.demoopencartapp.data.network.models.AddressListResponse;
import com.app.demoopencartapp.data.network.models.CartListResponse;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.CountriesStatesResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.data.network.models.ReviewsResponse;
import com.app.demoopencartapp.di.ActivityContext;
import com.app.demoopencartapp.ui.BrandedOffersAdapter;

import com.app.demoopencartapp.ui.HomeOffersListAdapter;
import com.app.demoopencartapp.ui.addAddress.CountryStateAdpater;
import com.app.demoopencartapp.ui.addressBook.AddressBookAdapter;
import com.app.demoopencartapp.ui.cart.CartAdapter;
import com.app.demoopencartapp.ui.home.AngleAdapter;
import com.app.demoopencartapp.ui.home.BestSellerListAdapter;
import com.app.demoopencartapp.ui.home.DealsProductListAdapter;
import com.app.demoopencartapp.ui.home.TestingInstrumentAdapter;
import com.app.demoopencartapp.ui.productDetails.MultipleImagesAdapter;
import com.app.demoopencartapp.ui.productDetails.QuantityAdapter;
import com.app.demoopencartapp.ui.productDetails.SimilarProductsAdapter;
import com.app.demoopencartapp.ui.productDetails.VariationsAdapter;
import com.app.demoopencartapp.ui.reviews.ReviewAdapter;
import com.app.demoopencartapp.ui.wishlist.WishlistAdapter;
import com.app.demoopencartapp.utils.rx.AppSchedulerProvider;
import com.app.demoopencartapp.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

  /*  @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginMvpPresenter(LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    VerifyOTPMvpPresenter<VerifyOTPMvpView> provideOTPMvpPresenter(VerifyOTPPresenter<VerifyOTPMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SendOTPMvpPresenter<SendOTPMvpView> provideSendOTPMvpPresenter(SendOTPPresenter<SendOTPMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    BookingHistoryMvpPresenter<BookingHistoryMvpView> provideBookingHistoryMvpPresenter(BookingHistoryPresenter<BookingHistoryMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    BookingDetailsMvpPresenter<BookingDetailsMvpView> provideBookingDetailsMvpPresenter(BookingDetailsPresenter<BookingDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    RegisterMvpPresenter<RegisterMvpView> provideRegisterMvpPresenter(RegisterPresenter<RegisterMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashMvpPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    HomeMvpPresenter<HomeMvpView> provideHomeMvpPresenter(HomePresenter<HomeMvpView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    MyAccountMvpPresenter<MyAccountMvpView> provideMyAccountMvpPresenter(MyAccountPresenter<MyAccountMvpView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    PackageDetailsMvpPresenter<PackageDetailsMvpView> providePackageDetailsMvpPresenter(PackageDetailsPresenter<PackageDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CartMvpPresenter<CartMvpView> provideCartMvpPresenter(CartPresenter<CartMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    AddressMvpPresenter<AddressMvpView> provideAddressMvpPresenter(AddressPresenter<AddressMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ContactMvpPresenter<ContactMvpView> provideContactMvpPresenter(ContactPresenter<ContactMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ContentMvpPresenter<ContentMvpView> provideContentMvpPresenter(ContentPresenter<ContentMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    NotificationMvpPresenter<NotificationMvpView> provideNotificationMvpPresenter(NotificationPresenter<NotificationMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    OrderSuccessMvpPresenter<OrderSuccessMvpView> provideOrderSuccessMvpPresenter(OrderSuccessPresenter<OrderSuccessMvpView> presenter) {
        return presenter;
    }
*/




    @Provides
    BrandedOffersAdapter provideBrandedOffersAdapter() {
        return new BrandedOffersAdapter(new ArrayList<HomeProductsResponse.BrandBean>());
    }

    @Provides
    DealsProductListAdapter provideDealsProductListAdapter() {
        return new DealsProductListAdapter(new ArrayList<HomeProductsResponse.DealsOfTheDayBean>());
    }

    @Provides
    BestSellerListAdapter provideBestSellerListAdapter() {
        return new BestSellerListAdapter(new ArrayList<HomeProductsResponse.BestSellerBean>());
    }

    @Provides
    TestingInstrumentAdapter provideTestingInstrumentAdapter() {
        return new TestingInstrumentAdapter(new ArrayList<HomeProductsResponse.TestingProductBean>());
    }

    @Provides
    AngleAdapter provideAngleAdapter() {
        return new AngleAdapter(new ArrayList<HomeProductsResponse.AngelGrindesBean>());
    }


    @Provides
    HomeOffersListAdapter provideHomeOffersListAdapter() {
        return new HomeOffersListAdapter(new ArrayList<HomeProductsResponse.SliderBean.BannerImageBean>());
    }

    @Provides
    MultipleImagesAdapter provideMultipleAdapter() {
        return new MultipleImagesAdapter(new ArrayList<ProductDetailsResponse.ProductBean.ImagesBean.ImageBean>());
    }



    @Provides
    QuantityAdapter provideQuantityAdapter() {
        return new QuantityAdapter(new ArrayList<Integer>());
    }

    @Provides
    VariationsAdapter provideVariationsAdapter() {
        return new VariationsAdapter(new ArrayList<ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean>());
    }

    @Provides
    SimilarProductsAdapter provideSimilarProductsAdapter() {
        return new SimilarProductsAdapter(new ArrayList<ProductDetailsResponse.RelatedProductBean>());
    }

    @Provides
    ReviewAdapter provideReviewAdapter() {
        return new ReviewAdapter(new ArrayList<ReviewsResponse.ReviewListBean>());
    }

    @Provides
    CountryStateAdpater provideCountryStateAdpater() {
        return new CountryStateAdpater(new ArrayList<CountriesStatesResponse.DataBean>());
    }


    @Provides
    CartAdapter provideCartAdapter() {
        return new CartAdapter(new ArrayList<CartListResponse.CartlistBean.ProductsBean>());
    }

    @Provides
    AddressBookAdapter provideAddressBookAdapter() {
        return new AddressBookAdapter(new ArrayList<AddressListResponse.ResponseDataBean>());
    }

    @Provides
    WishlistAdapter provideWishlistAdapter() {
        return new WishlistAdapter(new ArrayList<CategoriesProductsResponse.ProductBean>());
    }
/*
    @Provides
    BookingItemsAdapter provideBookingItemsAdapter() {
        return new BookingItemsAdapter(new ArrayList<BookingDetailsResponse.BookingItemsBean>());
    }


    @Provides
    NotificationAdapter provideNotificationAdapter() {
        return new Not ificationAdapter(new ArrayList<BookingHistoryResponse.BookingListBean>());
    }
*/


}
