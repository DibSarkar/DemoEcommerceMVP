package com.app.demoopencartapp.data;

import android.content.Context;


import com.app.demoopencartapp.data.network.ApiHelper;
import com.app.demoopencartapp.data.network.models.AddRatingResponse;
import com.app.demoopencartapp.data.network.models.AddUpdateCartResponse;
import com.app.demoopencartapp.data.network.models.AddressListResponse;
import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.CartListResponse;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.CountriesStatesResponse;
import com.app.demoopencartapp.data.network.models.GetAccountInfoResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.data.network.models.MessageResponse;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.data.network.models.RegisterResponse;
import com.app.demoopencartapp.data.network.models.ReviewsResponse;
import com.app.demoopencartapp.data.prefs.PreferencesHelper;
import com.app.demoopencartapp.data.prefs.SessionPreferenceHelper;
import com.app.demoopencartapp.di.ApplicationContext;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by svk on 5/6/17.
 */
public class AppDataManager implements DataManager {

    ApiHelper mApiHelper;
    PreferencesHelper mPreferencesHelper;
    SessionPreferenceHelper sessionPreferenceHelper;
    Context mContext;



    @Inject
    public AppDataManager(ApiHelper mApiHelper, PreferencesHelper mPreferencesHelper,SessionPreferenceHelper sessionPreferenceHelper, @ApplicationContext Context mContext) {
        this.mApiHelper = mApiHelper;
        this.mPreferencesHelper = mPreferencesHelper;
        this.mContext = mContext;
        this.sessionPreferenceHelper=sessionPreferenceHelper;
    }

    @Override
    public void logout() {
        destroyPref();
    }

    @Override
    public String getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(String userId) {

        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public String getCurrentFirstName() {
        return mPreferencesHelper.getCurrentFirstName();
    }

    @Override
    public void setCurrentFirstName(String firstName) {
        mPreferencesHelper.setCurrentFirstName(firstName);
    }

    @Override
    public String getCurrentLastName() {
        return mPreferencesHelper.getCurrentLastName();
    }

    @Override
    public void setCurrentLastName(String lastName) {
        mPreferencesHelper.setCurrentLastName(lastName);
    }

    @Override
    public void setCurrentUserGender(String gender) {
        mPreferencesHelper.setCurrentUserGender(gender);
    }

    @Override
    public String getCurrentUserGender() {
        return mPreferencesHelper.getCurrentUserGender();
    }

    @Override
    public String getCurrentMobileNumber() {
        return mPreferencesHelper.getCurrentMobileNumber();
    }

    @Override
    public void setCurrentMobileNumber(String mobileNumber) {
        mPreferencesHelper.setCurrentMobileNumber(mobileNumber);
    }

    @Override
    public void setLastIntaractionTimestamp(long timeStamp) {
        mPreferencesHelper.setLastIntaractionTimestamp(timeStamp);
    }

    @Override
    public long getLastIntaractionTimestamp() {
        return mPreferencesHelper.getLastIntaractionTimestamp();
    }

    @Override
    public void setRegistrationType(String registrationType) {
        mPreferencesHelper.setRegistrationType(registrationType);
    }

    @Override
    public String getRegistrationType() {
        return  mPreferencesHelper.getRegistrationType();
    }

    @Override
    public void destroyPref() {

        mPreferencesHelper.destroyPref();
    }

    @Override
    public Observable<AllCategoriesResponse> getAllCategories() {
        return mApiHelper.getAllCategories();
    }

    @Override
    public Call<RegisterResponse> register(String apitoken,RequestBody firstname, RequestBody lastname, RequestBody email, RequestBody telephone, RequestBody password, RequestBody newsletter, RequestBody gstin, RequestBody device_type, RequestBody device_token) {
        return mApiHelper.register(apitoken,firstname,lastname,email,telephone,password,newsletter,gstin,device_type,device_token);
    }

    @Override
    public Single<RegisterResponse> login(RequestBody email, RequestBody password, RequestBody device_type, RequestBody device_token, RequestBody session_id) {
        return mApiHelper.login(email,password,device_type,device_token,session_id);
    }

    @Override
    public Observable<CategoriesProductsResponse> getCategoryProducts(String sort, String order, String apitoken, RequestBody category_id, RequestBody user_id,RequestBody session_id) {
        return mApiHelper.getCategoryProducts(sort,order,apitoken,category_id,user_id,session_id);
    }

    @Override
    public Observable<HomeProductsResponse> getHomeProducts(String apitoken, RequestBody customer_id, RequestBody session_id) {
        return mApiHelper.getHomeProducts(apitoken, customer_id, session_id);
    }


    @Override
    public Observable<ProductDetailsResponse> getProductDetails(String apitoken, RequestBody product_id, RequestBody user_id,RequestBody session_id) {
        return mApiHelper.getProductDetails(apitoken,product_id,user_id,session_id);
    }

    @Override
    public Call<GetAccountInfoResponse> getAccountInfo(String apitoken, RequestBody user_id) {
        return mApiHelper.getAccountInfo(apitoken,user_id);
    }

    @Override
    public Call<MessageResponse> updateAccount(String apitoken, RequestBody customer_id, RequestBody firstname, RequestBody lastname, RequestBody email, RequestBody telephone, RequestBody newsletter, RequestBody gstin) {
        return mApiHelper.updateAccount(apitoken,customer_id,firstname,lastname,email,telephone,newsletter,gstin);
    }

    @Override
    public Call<MessageResponse> changePass(String apitoken, RequestBody customer_id, RequestBody password) {
        return mApiHelper.changePass(apitoken,customer_id,password);
    }

    @Override
    public Call<AddRatingResponse> addRating(String apitoken, RequestBody product_id, RequestBody name, RequestBody review, RequestBody rating, RequestBody customer_id) {
        return mApiHelper.addRating(apitoken,product_id,name,review,rating,customer_id);
    }

    @Override
    public Call<ReviewsResponse> getReviews(String apitoken, RequestBody product_id) {
        return mApiHelper.getReviews(apitoken,product_id);
    }

    @Override
    public Call<CountriesStatesResponse> getCountries(String apitoken) {
        return mApiHelper.getCountries(apitoken);
    }

    @Override
    public Call<CountriesStatesResponse> getStates(String apitoken, RequestBody country_id) {
        return mApiHelper.getStates(apitoken, country_id);
    }

    @Override
    public Call<MessageResponse> addAddress(String apitoken, RequestBody customer_id, RequestBody firstname, RequestBody lastname, RequestBody company, RequestBody gst_no, RequestBody address_1, RequestBody address_2, RequestBody city, RequestBody country_id, RequestBody state_id, RequestBody postcode, RequestBody default_address) {
        return mApiHelper.addAddress(apitoken, customer_id, firstname, lastname, company, gst_no, address_1, address_2, city, country_id, state_id, postcode, default_address);
    }

    @Override
    public Call<MessageResponse> editAddress(String apitoken, RequestBody address_id, RequestBody customer_id, RequestBody firstname, RequestBody lastname, RequestBody company, RequestBody gst_no, RequestBody address_1, RequestBody address_2, RequestBody city, RequestBody country_id, RequestBody state_id, RequestBody postcode, RequestBody default_address) {
        return mApiHelper.editAddress(apitoken, address_id, customer_id, firstname, lastname, company, gst_no, address_1, address_2, city, country_id, state_id, postcode, default_address);
    }

    @Override
    public Call<AddressListResponse> getAddress(String apitoken, RequestBody customer_id) {
        return mApiHelper.getAddress(apitoken, customer_id);
    }

    @Override
    public Call<MessageResponse> deleteAddress(String apitoken, RequestBody customer_id, RequestBody address_id) {
        return mApiHelper.deleteAddress(apitoken, customer_id, address_id);
    }

    @Override
    public Single<AddUpdateCartResponse> addCart(String apitoken, RequestBody customer_id, RequestBody product_id, RequestBody quantity, RequestBody session_id) {
        return mApiHelper.addCart(apitoken, customer_id, product_id, quantity, session_id);
    }

    @Override
    public Single<AddUpdateCartResponse> addCustomizableCart(String apitoken, String customer_id, String product_id, String quantity, String session_id, Map<String, String> option) {
        return mApiHelper.addCustomizableCart(apitoken, customer_id, product_id, quantity, session_id, option);
    }

    @Override
    public Call<CategoriesProductsResponse> getWishlistProducts() {
        return mApiHelper.getWishlistProducts();
    }

    @Override
    public Observable<CartListResponse> getCartList(String apitoken, RequestBody customer_id, RequestBody session_id) {
        return mApiHelper.getCartList(apitoken, customer_id, session_id);
    }

    @Override
    public Completable updateCart(String apitoken, RequestBody cart_id, RequestBody quantity) {
        return mApiHelper.updateCart(apitoken, cart_id, quantity);
    }

    @Override
    public Completable deleteCart(String apitoken, RequestBody cart_id) {
        return mApiHelper.deleteCart(apitoken, cart_id);
    }


    @Override
    public String getSessionId() {
        return sessionPreferenceHelper.getSessionId();
    }

    @Override
    public void setSessionId(String sessionId) {
        sessionPreferenceHelper.setSessionId(sessionId);
    }

    @Override
    public void destroySessionPref() {
        sessionPreferenceHelper.destroySessionPref();
    }








  /*  @Override
    public Call<OTPResponse> sendOTP(SendOTPRequest req) {
        return mApiHelper.sendOTP(req);
    }

    @Override
    public Call<RegisterResponse> register(MultipartBody.Part profile_pic, RequestBody f_name, RequestBody l_name, RequestBody email, RequestBody mobile, RequestBody password, RequestBody device_type, RequestBody device_token) {
        return mApiHelper.register(profile_pic,f_name,l_name,email,mobile,password,device_type,device_token);
    }

    @Override
    public Call<EditProfileResponse> editProfile(MultipartBody.Part prof_img, RequestBody f_name, RequestBody l_name, RequestBody email, RequestBody mobile, RequestBody user_id) {
        return mApiHelper.editProfile(prof_img,f_name,l_name,email,mobile,user_id);
    }

    @Override
    public Call<LoginResponse> login(LoginRequest req) {
        return mApiHelper.login(req);
    }

    @Override
    public Call<CommonResponse> forgotPass(ForgotPassRequest req) {
        return mApiHelper.forgotPass(req);
    }

    @Override
    public Call<PackageListResponse> getPackages(PackageListRequest req) {
        return mApiHelper.getPackages(req);
    }

    @Override
    public Call<MyProfileResponse> getProfileInfo(SendUserRequest req) {
        return mApiHelper.getProfileInfo(req);
    }

    @Override
    public Call<CommonResponse> changePass(ChangePassRequest req) {
        return mApiHelper.changePass(req);
    }

    @Override
    public Call<CommonResponse> addtoCart(AddToCartRequest req) {
        return mApiHelper.addtoCart(req);
    }

    @Override
    public Call<PackageCartStatusResponse> getCartStatus(AddToCartRequest req) {
        return mApiHelper.getCartStatus(req);
    }

    @Override
    public Call<CartListResponse> getCartList(SendUserRequest req) {
        return mApiHelper.getCartList(req);
    }

    @Override
    public Call<CommonResponse> deleteCartItem(DeleteCartItemRequest req) {
        return mApiHelper.deleteCartItem(req);
    }

    @Override
    public Call<CommonResponse> changeAddress(AddressRequest req) {
        return mApiHelper.changeAddress(req);
    }

    @Override
    public Call<CommonResponse> confirmBooking(BookingRequest req) {
        return mApiHelper.confirmBooking(req);
    }

    @Override
    public Call<CommonResponse> clearCart(SendUserRequest req) {
        return mApiHelper.clearCart(req);
    }

    @Override
    public Call<BookingHistoryResponse> getBookingHistory(BookingHistoryRequest req) {
        return mApiHelper.getBookingHistory(req);
    }

    @Override
    public Call<BookingDetailsResponse> getBookingDetails(BookingDetailsRequest req) {
        return mApiHelper.getBookingDetails(req);
    }

    @Override
    public Call<BookingCancelResponse> cancelBooking(BookingDetailsRequest req) {
        return mApiHelper.cancelBooking(req);
    }

    @Override
    public Call<BookingRatingResponse> sendRating(BookingRatingRequest req) {
        return mApiHelper.sendRating(req);
    }

    @Override
    public Call<CommonResponse> getContents(ContentRequest req) {
        return mApiHelper.getContents(req);
    }

    @Override
    public Call<CommonResponse> checkAddress(AddressCheckRequest req) {
        return mApiHelper.checkAddress(req);
    }

    @Override
    public Call<ContactResponse> getContactData() {
        return mApiHelper.getContactData();
    }*/
}

