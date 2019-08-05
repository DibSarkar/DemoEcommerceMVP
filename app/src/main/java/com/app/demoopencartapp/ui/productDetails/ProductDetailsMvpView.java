package com.app.demoopencartapp.ui.productDetails;

import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;
import java.util.logging.StreamHandler;

public interface ProductDetailsMvpView extends MvpView {

    void getProductDetails(ProductDetailsResponse.ProductBean product, List<ProductDetailsResponse.RelatedProductBean> relatedProduct, int total_qty);
    void changeImage(String url);
    void hideDesc(boolean showDesc);
    void hideSpec(boolean showSpec);
    void loadQuantities(String min,String max);
    void openProductDetails(String pro_id);
    void openZoomActivity(String product_img);
    void showAddReviewDialog();
    void openReviewsActivity(String total_reviews);
    void checkWishlistByUser();
    void openLoginActvity();
    void checkWish(ProductDetailsResponse.RelatedProductBean relatedProductBean, int pos);
    void similarAddCart(String product_id, String quantity);
    void singleAddCart(String product_id, String quantity, boolean isCustomizable, String product_option_id, String product_option_value_id);
    void addToCartDone(int cart_count);
    void loadMultipleImages(List<ProductDetailsResponse.ProductBean.ImagesBean.ImageBean> image);
    void loadVariations(List<ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean> product_option_value);
    void loadRelatedProducts(List<ProductDetailsResponse.RelatedProductBean> relatedProduct);
    void openCartActivity();
    void addWishlist();
    void updateWishDone(int wishlist_id);
}
