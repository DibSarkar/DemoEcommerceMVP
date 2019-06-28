package com.app.demoopencartapp.data.network;




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

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
   Single<RegisterResponse> login(@Part("email") RequestBody email,
                                @Part("password") RequestBody password,
                                @Part("device_type") RequestBody device_type,
                                @Part("device_token") RequestBody device_token,
                                @Part("session_id") RequestBody session_id);

    @Multipart
    @POST("index.php?route=api/category/catProduct")
    Call<CategoriesProductsResponse> getCategoryProducts(
                                    @Query("sort") String sort,
                                    @Query("order") String order,
                                    @Query("api_token") String apitoken,
                                    @Part("category_id") RequestBody category_id,
                                    @Part("customer_id") RequestBody customer_id,
                                    @Part("session_id") RequestBody session_id);
    @Multipart
    @POST("index.php?route=api/home/homePage")
    Call<HomeProductsResponse> getHomeProducts(@Query("api_token") String apitoken,
                                               @Part("customer_id") RequestBody customer_id,
                                               @Part("session_id") RequestBody session_id);

    @Multipart
    @POST("index.php?route=api/product/getproduct")
    Call<ProductDetailsResponse> getProductDetails(@Query("api_token") String apitoken,
                                                   @Part("product_id") RequestBody product_id,
                                                   @Part("customer_id") RequestBody customer_id,
                                                   @Part("session_id") RequestBody session_id);

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

    @Multipart
    @POST("index.php?route=api/product/reviewadd")
    Call<AddRatingResponse> addRating(@Query("api_token") String apitoken,
                                       @Part("product_id") RequestBody product_id,
                                       @Part("name") RequestBody name,
                                       @Part("review") RequestBody review,
                                       @Part("rating") RequestBody rating,
                                       @Part("customer_id") RequestBody customer_id);

    @Multipart
    @POST("index.php?route=api/product/reviewList")
    Call<ReviewsResponse> getReviews(@Query("api_token") String apitoken,
                                     @Part("product_id") RequestBody product_id);


    @POST("index.php?route=api/address/countryList")
    Call<CountriesStatesResponse> getCountries(@Query("api_token") String apitoken);

    @Multipart
    @POST("index.php?route=api/address/stateList")
    Call<CountriesStatesResponse> getStates(@Query("api_token") String apitoken,
                                            @Part("country_id") RequestBody country_id);

    @Multipart
    @POST("index.php?route=api/address/addressAdd")
    Call<MessageResponse> addAddress(@Query("api_token") String apitoken,
                                            @Part("customer_id") RequestBody customer_id,
                                            @Part("firstname") RequestBody firstname,
                                            @Part("lastname") RequestBody lastname,
                                            @Part("company") RequestBody company,
                                            @Part("gst_no") RequestBody gst_no,
                                            @Part("address_1") RequestBody address_1,
                                            @Part("address_2") RequestBody address_2,
                                            @Part("city") RequestBody city,
                                            @Part("country_id") RequestBody country_id,
                                            @Part("state_id") RequestBody state_id,
                                            @Part("postcode") RequestBody postcode,
                                            @Part("default_address") RequestBody default_address);

    @Multipart
    @POST("index.php?route=api/address/addressEdit")
    Call<MessageResponse> editAddress(@Query("api_token") String apitoken,
                                      @Part("address_id") RequestBody address_id,
                                      @Part("customer_id") RequestBody customer_id,
                                      @Part("firstname") RequestBody firstname,
                                      @Part("lastname") RequestBody lastname,
                                      @Part("company") RequestBody company,
                                      @Part("gst_no") RequestBody gst_no,
                                      @Part("address_1") RequestBody address_1,
                                      @Part("address_2") RequestBody address_2,
                                      @Part("city") RequestBody city,
                                      @Part("country_id") RequestBody country_id,
                                      @Part("state_id") RequestBody state_id,
                                      @Part("postcode") RequestBody postcode,
                                      @Part("default_address") RequestBody default_address);

    @Multipart
    @POST("index.php?route=api/address/getAddress")
    Call<AddressListResponse> getAddress(@Query("api_token") String apitoken,
                                         @Part("customer_id") RequestBody customer_id);

    @Multipart
    @POST("index.php?route=api/address/deleteAddress")
    Call<MessageResponse> deleteAddress(@Query("api_token") String apitoken,
                                            @Part("customer_id") RequestBody customer_id,
                                            @Part("address_id") RequestBody address_id);

    @Multipart
    @POST("index.php?route=api/cart/add")
    Call<AddUpdateCartResponse> addCart(@Query("api_token") String apitoken,
                                        @Part("customer_id") RequestBody customer_id,
                                        @Part("product_id") RequestBody product_id,
                                        @Part("quantity") RequestBody quantity,
                                        @Part("session_id") RequestBody session_id);

    @FormUrlEncoded
    @POST("index.php?route=api/cart/add")
    Call<AddUpdateCartResponse> addCustomizableCart(@Query("api_token") String apitoken,
                                        @Field("customer_id") String customer_id,
                                        @Field("product_id") String product_id,
                                        @Field("quantity") String quantity,
                                        @Field("session_id") String session_id,
                                        @FieldMap Map<String,String> option);


    @GET("http://www.mocky.io/v2/5d0db7083400006800ca4a42")
    Call<CategoriesProductsResponse> getWishlistProducts();

    @Multipart
    @POST("index.php?route=api/cart/cartList")
    Call<CartListResponse> getCartList(@Query("api_token") String apitoken,
                                       @Part("customer_id") RequestBody customer_id,
                                       @Part("session_id") RequestBody session_id);

    @Multipart
    @POST("index.php?route=api/cart/updateCart")
    Call<MessageResponse> updateCart(@Query("api_token") String apitoken,
                                     @Part("cart_id") RequestBody cart_id,
                                     @Part("quantity") RequestBody quantity);

    @Multipart
    @POST("index.php?route=api/cart/deleteCart")
    Call<MessageResponse> deleteCart(@Query("api_token") String apitoken,
                                     @Part("cart_id") RequestBody cart_id);



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
