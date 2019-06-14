package com.app.demoopencartapp.ui.productDetails;

import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.shared.base.MvpView;

import java.util.List;

public interface ProductDetailsMvpView extends MvpView {

    void getProductDetails(ProductDetailsResponse.ProductBean product, List<ProductDetailsResponse.RelatedProductBean> relatedProduct);
    void changeImage(String url);
    void hideDesc(boolean showDesc);
    void hideSpec(boolean showSpec);
    void loadQuantities(String min,String max);
    void openProductDetails(String pro_id);
    void openZoomActivity(String product_img);

}
