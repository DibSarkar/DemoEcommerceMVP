package com.app.demoopencartapp.data.network;




import com.app.demoopencartapp.data.network.models.AllCategoriesResponse;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.GetAccountInfoResponse;
import com.app.demoopencartapp.data.network.models.HomeProductsResponse;
import com.app.demoopencartapp.data.network.models.MessageResponse;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.data.network.models.RegisterResponse;
import com.app.demoopencartapp.utils.Constants;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by svk on 5/6/17.
 */

public interface ApiHelper {



    @GET("index.php?route=api/category/allcategory&api_token=b1f24cb121c30a6765791ec1b0")
    Call<AllCategoriesResponse> getAllCategories();


    @Multipart
    @POST("index.php?route=api/customer/add")
    Call<RegisterResponse> register(@Query("api_token") String apitoken,
                                    @Part("firstname") RequestBody firstname,
                                    @Part("lastname") RequestBody lastname,
                                    @Part("email") RequestBody email,
                                    @Part("telephone") RequestBody telephone,
                                    @Part("password") RequestBody password,
                                    @Part("newsletter") RequestBody newsletter,
                                    @Part("gstin") RequestBody gstin,
                                    @Part("device_type") RequestBody device_type,
                                    @Part("device_token") RequestBody device_token);

    @Multipart
    @POST("index.php?route=api/customer/login&api_token=b1f24cb121c30a6765791ec1b0")
    Call<RegisterResponse> login(@Part("email") RequestBody email,
                                    @Part("password") RequestBody password,
                                    @Part("device_type") RequestBody device_type,
                                    @Part("device_token") RequestBody device_token);

    @Multipart
    @POST("index.php?route=api/category/catProduct")
    Call<CategoriesProductsResponse> getCategoryProducts(
                                    @Query("sort") String sort,
                                    @Query("order") String order,
                                    @Query("api_token") String apitoken,
                                    @Part("category_id") RequestBody category_id,
                                    @Part("customer_id") RequestBody customer_id);

    @POST("index.php?route=api/home/homePage")
    Call<HomeProductsResponse> getHomeProducts(@Query("api_token") String apitoken);

    @Multipart
    @POST("index.php?route=api/product/getproduct")
    Call<ProductDetailsResponse> getProductDetails(@Query("api_token") String apitoken,
                                                   @Part("product_id") RequestBody product_id,
                                                   @Part("customer_id") RequestBody customer_id);

    @Multipart
    @POST("index.php?route=api/customer/myAccount")
    Call<GetAccountInfoResponse> getAccountInfo(@Query("api_token") String apitoken,
                                                @Part("customer_id") RequestBody customer_id);

    @Multipart
    @POST("index.php?route=api/customer/editAccount")
    Call<MessageResponse> updateAccount(@Query("api_token") String apitoken,
                                        @Part("customer_id") RequestBody customer_id,
                                        @Part("firstname") RequestBody firstname,
                                        @Part("lastname") RequestBody lastname,
                                        @Part("email") RequestBody email,
                                        @Part("telephone") RequestBody telephone,
                                        @Part("newsletter") RequestBody newsletter,
                                        @Part("gstin") RequestBody gstin);

    @Multipart
    @POST("index.php?route=api/customer/changePassword")
    Call<MessageResponse> changePass(@Query("api_token") String apitoken,
                                     @Part("customer_id") RequestBody customer_id,
                                     @Part("password") RequestBody password);
 /*
    @Multipart
    @POST("editProfile")
    Call<EditProfileResponse> editProfile(@Part MultipartBody.Part prof_img,
                                          @Part("f_name") RequestBody f_name,
                                          @Part("l_name") RequestBody l_name,
                                          @Part("email") RequestBody email,
                                          @Part("mobile") RequestBody mobile,
                                          @Part("user_id") RequestBody user_id);

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest req);

    @POST("forgot_pass")
    Call<CommonResponse> forgotPass(@Body ForgotPassRequest req);

    @POST("packageList")
    Call<PackageListResponse> getPackages(@Body PackageListRequest req);

    @POST("getProfile")
    Call<MyProfileResponse> getProfileInfo(@Body SendUserRequest req);

    @POST("changePassword")
    Call<CommonResponse> changePass(@Body ChangePassRequest req);

    @POST("addToCart")
    Call<CommonResponse> addtoCart(@Body AddToCartRequest req);

    @POST("getCartStatus")
    Call<PackageCartStatusResponse> getCartStatus(@Body AddToCartRequest req);

    @POST("cartList")
    Call<CartListResponse> getCartList(@Body SendUserRequest req);

    @POST("deleteCart")
    Call<CommonResponse> deleteCartItem(@Body DeleteCartItemRequest req);

    @POST("changeAddress")
    Call<CommonResponse> changeAddress(@Body AddressRequest req);

    @POST("booking")
    Call<CommonResponse> confirmBooking(@Body BookingRequest req);

    @POST("clearCart")
    Call<CommonResponse> clearCart(@Body SendUserRequest req);

    @POST("getBookingHistory")
    Call<BookingHistoryResponse> getBookingHistory(@Body BookingHistoryRequest req);

    @POST("bookingDetails")
    Call<BookingDetailsResponse> getBookingDetails(@Body BookingDetailsRequest req);

    @POST("cancel")
    Call<BookingCancelResponse> cancelBooking(@Body BookingDetailsRequest req);

    @POST("submitRating")
    Call<BookingRatingResponse> sendRating(@Body BookingRatingRequest req);

    @POST("all_page")
    Call<CommonResponse> getContents(@Body ContentRequest req);

    @POST("area_check")
    Call<CommonResponse> checkAddress(@Body AddressCheckRequest req);

    @POST("contact")
    Call<ContactResponse> getContactData();
*/


}
