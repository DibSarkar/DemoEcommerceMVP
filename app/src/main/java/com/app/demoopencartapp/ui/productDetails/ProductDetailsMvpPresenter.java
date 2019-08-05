package com.app.demoopencartapp.ui.productDetails;

import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.shared.base.MvpPresenter;

import java.util.List;

public interface ProductDetailsMvpPresenter  <V extends ProductDetailsMvpView> extends MvpPresenter<V> {

    void onGetProductDetails(String product_id);
    void onChangeImage(String url);
    void onHideDesc(boolean showDesc);
    void onHideSpec(boolean showSpec);
    void onLoadQuantities(String min,String max);
    void onOpenProductDetails(String pro_id);
    void onOpenZoom(String product_img);
    void onShowAddOpenDialog();
    void onConfirmOpenReviews(String total_reviews);
    void onCheckWishlistByUser();
    void onOpenLoginActivity();
    void onCheckWish(ProductDetailsResponse.RelatedProductBean relatedProductBean,int pos);
    void onConfirmSimilarAddCart(String product_id, String quantity, String isStock);
    void onAddtoCart(String product_id, String quantity);
    void onConfirmAddCart(String product_id, String quantity, boolean isCustomizable, String product_option_id, String product_option_value_id, boolean isStock);
    void onCustomizableAddCart(String product_id, String quantity, boolean isCustomizable, String product_option_id, String product_option_value_id);
    void onMultipleImages(List<ProductDetailsResponse.ProductBean.ImagesBean.ImageBean> image);
    void onLoadVariations(List<ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean> product_option_value);
    void onLoadRelatedProducts(List<ProductDetailsResponse.RelatedProductBean> relatedProduct);
    void onOpenCartActivity();
    void onAddWishlist(String product_id, String product_option_id, String product_option_value_id, boolean isCustomizable);
    void onDeleteWish(String wishlist_id);
}
