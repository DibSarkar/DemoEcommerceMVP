package com.app.demoopencartapp.data;

import android.content.Context;


import com.app.demoopencartapp.data.network.ApiHelper;
import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.GetAccountInfoResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.data.network.models.MessageResponse;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.data.network.models.RegisterResponse;
import com.app.demoopencartapp.data.prefs.PreferencesHelper;
import com.app.demoopencartapp.di.ApplicationContext;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by svk on 5/6/17.
 */
public class AppDataManager implements DataManager {

    ApiHelper mApiHelper;
    PreferencesHelper mPreferencesHelper;
    Context mContext;



    @Inject
    public AppDataManager(ApiHelper mApiHelper, PreferencesHelper mPreferencesHelper, @ApplicationContext Context mContext) {
        this.mApiHelper = mApiHelper;
        this.mPreferencesHelper = mPreferencesHelper;
        this.mContext = mContext;
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
    public Call<AllCategoriesResponse> getAllCategories() {
        return mApiHelper.getAllCategories();
    }

    @Override
    public Call<RegisterResponse> register(String apitoken,RequestBody firstname, RequestBody lastname, RequestBody email, RequestBody telephone, RequestBody password, RequestBody newsletter, RequestBody gstin, RequestBody device_type, RequestBody device_token) {
        return mApiHelper.register(apitoken,firstname,lastname,email,telephone,password,newsletter,gstin,device_type,device_token);
    }

    @Override
    public Call<RegisterResponse> login(RequestBody email, RequestBody password, RequestBody device_type, RequestBody device_token) {
        return mApiHelper.login(email,password,device_type,device_token);
    }

    @Override
    public Call<CategoriesProductsResponse> getCategoryProducts(String sort, String order, String apitoken, RequestBody category_id, RequestBody user_id) {
        return mApiHelper.getCategoryProducts(sort,order,apitoken,category_id,user_id);
    }

    @Override
    public Call<HomeProductsResponse> getHomeProducts(String apitoken) {
        return mApiHelper.getHomeProducts(apitoken);
    }

    @Override
    public Call<ProductDetailsResponse> getProductDetails(String apitoken, RequestBody product_id, RequestBody user_id) {
        return mApiHelper.getProductDetails(apitoken,product_id,user_id);
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

