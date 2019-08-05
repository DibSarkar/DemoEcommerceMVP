package com.app.demoopencartapp.ui.productDetails;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.shared.base.BaseActivity;

import com.app.demoopencartapp.ui.cart.CartActivity;
import com.app.demoopencartapp.ui.login.LoginActivity;
import com.app.demoopencartapp.ui.reviews.ReviewsListActivity;
import com.app.demoopencartapp.ui.zoom.ZoomActivity;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.GlideApp;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class ProductDetailsActivity extends BaseActivity implements ProductDetailsMvpView{

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_similar)
    RecyclerView rv_similar;

    @BindView(R.id.rv_multiplelist)
    RecyclerView rv_multiplelist;

    @BindView(R.id.tv_product_old__price)
    TextView tv_product_old__price;

    @BindView(R.id.tv_product_price)
    TextView tv_product_price;

    @BindView(R.id.tv_offer)
    TextView tv_offer;

    @BindView(R.id.tv_delivery)
    TextView tv_delivery;

    @BindView(R.id.tv_desc)
    TextView tv_desc;

    @BindView(R.id.tv_stock)
    TextView tv_stock;

    @BindView(R.id.btndesc)
    ImageView btndesc;

    @BindView(R.id.btnspecific)
    ImageView btnspecific;

    @BindView(R.id.sp_quantity)
    Spinner sp_quantity;

    @BindView(R.id.tv_pro_name)
    TextView tv_pro_name;

    @BindView(R.id.tv_specs)
    TextView tv_specs;

    @BindView(R.id.tv_rating)
     TextView tv_rating;

    @BindView(R.id.tv_features)
    TextView tv_features;

    @BindView(R.id.tv_manufacturer)
    TextView tv_manufacturer;

    @BindView(R.id.ll_offer_price)
    LinearLayout ll_offer_price;

    @BindView(R.id.ll_similar_product)
    LinearLayout ll_similar_product;

    @BindView(R.id.iv_pro)
    ImageView iv_pro;

    @BindView(R.id.rl_multiple)
    RelativeLayout rl_multiple;

    @BindView(R.id.ll_variations)
    LinearLayout ll_variations;

    @BindView(R.id.ll_cart_count)
    LinearLayout ll_cart_count;

    @BindView(R.id.tv_cart_count)
    TextView tv_cart_count;

    @BindView(R.id.rb_product)
    RatingBar rb_product;

    @BindView(R.id.ll_quantity_select)
    LinearLayout ll_quantity_select;

    @BindView(R.id.tv_variation_type)
    TextView tv_variation_type;

    @BindView(R.id.rv_variations)
    RecyclerView rv_variations;

    @BindView(R.id.like_button)
    SparkButton like_button;

    @BindView(R.id.ll_add_cart)
    LinearLayout ll_add_cart;

    @Inject
    MultipleImagesAdapter multipleAdapter;

    @Inject
    QuantityAdapter quantityAdapter;

    @Inject
    VariationsAdapter variationsAdapter;

    @Inject
    SimilarProductsAdapter similarProductsAdapter;

    @Inject
    ProductDetailsPresenter<ProductDetailsMvpView> productDetailsPresenter;

    AddReviewDialogFragment addReviewDialogFragment;
    int wish_id;

    public ArrayList<Integer> quantity_list ;

    String product_show_img="";

    public boolean showDesc = true;
    public boolean showSpec = true;

    public static final int OPEN_LOGIN = 500;
    String reviews = "";
    String quantity = "";
    public boolean isCustomizable = false;
    String product_option_id = "";
    String product_option_value_id = "";
    public boolean isStock = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_details);
        mContext = this;
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));
        setUp();
    }
    public void setUp()
    {

        productDetailsPresenter.onAttach(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
      /*  rv_similar.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_similar.setItemAnimator(new DefaultItemAnimator());
        rv_similar.setNestedScrollingEnabled(false);*/


        if(getIntent().getExtras().getString("product_id")!=null)
        {
            productDetailsPresenter.onGetProductDetails(getIntent().getExtras().getString("product_id"));
        }


        like_button.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {

                if(buttonState)
                {
                    productDetailsPresenter.onCheckWishlistByUser();
                }
                else {

                    productDetailsPresenter.onDeleteWish("");
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

        sp_quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(quantity_list!=null)
               {
                   quantity = quantity_list.get(position).toString();
               }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      /*  iv_heart.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                iv_heart.setLiked(false);
                iv_heart.setLikeDrawableRes(R.drawable.inactive_wish);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                iv_heart.setLiked(true);
                iv_heart.setLikeDrawableRes(R.drawable.active_wish);

            }
        });*/

    }

    @OnClick({R.id.btndesc,R.id.btnspecific,R.id.iv_pro,R.id.tv_add_review,R.id.ll_reviews,R.id.ll_add_cart,R.id.iv_add_cart,R.id.btn_add_cart,R.id.iv_cart})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.btndesc :
                 productDetailsPresenter.onHideDesc(showDesc);
                break;

            case R.id.btnspecific :
                productDetailsPresenter.onHideSpec(showSpec);
                break;

            case R.id.iv_pro :
                if(!product_show_img.equals("")) {
                    productDetailsPresenter.onOpenZoom(product_show_img);
                }
                break;

            case R.id.tv_add_review :
                productDetailsPresenter.onShowAddOpenDialog();
                break;

            case R.id.ll_reviews :
                productDetailsPresenter.onConfirmOpenReviews(reviews);
                break;

            case R.id.ll_add_cart :
                productDetailsPresenter.onConfirmAddCart(getIntent().getExtras().getString("product_id"), quantity,isCustomizable,product_option_id,product_option_value_id,isStock);
                break;

            case R.id.iv_add_cart :
                productDetailsPresenter.onConfirmAddCart(getIntent().getExtras().getString("product_id"), quantity,isCustomizable,product_option_id,product_option_value_id,isStock);
                break;

            case R.id.btn_add_cart :
                productDetailsPresenter.onConfirmAddCart(getIntent().getExtras().getString("product_id"), quantity,isCustomizable,product_option_id,product_option_value_id,isStock);
                break;

            case R.id.iv_cart :
                productDetailsPresenter.onOpenCartActivity();
                break;



        }
    }

    @Override
    protected void onDestroy() {
        productDetailsPresenter.onDetach();
        super.onDestroy();
    }


    @Override
    public void getProductDetails(ProductDetailsResponse.ProductBean product, List<ProductDetailsResponse.RelatedProductBean> relatedProduct, int total_qty) {


        if(product.getWishlist()==1)
        {
            like_button.setChecked(true);
        }
        else {
            like_button.setChecked(false);
        }

        if(total_qty==0)
        {
            ll_cart_count.setVisibility(View.GONE);
        }
        else {
            ll_cart_count.setVisibility(View.VISIBLE);
            tv_cart_count.setText(String.valueOf(total_qty));
        }
        if(product!=null)
        {
            tv_pro_name.setText(Html.fromHtml(product.getName()));
            tv_specs.setText(Html.fromHtml(product.getSpecification()));
            tv_desc.setText(Html.fromHtml(product.getDescription()));
            tv_manufacturer.setText("By - "+product.getManufacturer());
            tv_features.setText(Html.fromHtml(product.getFeatures()));
            tv_stock.setText(product.getStock());
            reviews=String.valueOf(product.getReviews());
            tv_rating.setText(String.valueOf(product.getReviews()));
            rb_product.setRating((float)(product.getRating()));
            if(!product.getThumb().equals("")) {
                GlideApp.with(mContext)
                        .load(product.getThumb())
                        .placeholder(R.drawable.no_image)
                        .centerCrop()
                        .into(iv_pro);
                product_show_img=product.getThumb();
            }


            if(!product.getSpecial().equals("0.00"))
            {
                tv_product_price.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(product.getSpecial()))));
                ll_offer_price.setVisibility(View.VISIBLE);
                tv_product_old__price.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(product.getPrice()))));
                tv_product_old__price.setPaintFlags( tv_product_old__price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                double offer = Math.round(((Double.parseDouble(product.getPrice())-Double.parseDouble(product.getSpecial()))/Double.parseDouble(product.getPrice()))*100);
                DecimalFormat df = new DecimalFormat("###.#");
                tv_offer.setText("("+df.format(offer)+"%"+" "+"OFF"+")");
                tv_product_old__price.setPaintFlags( tv_product_old__price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            }
            else {
               tv_product_price.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(product.getPrice()))));
                tv_product_old__price.setVisibility(View.GONE);
                ll_offer_price.setVisibility(View.GONE);

            }


            if(!product.getDelivery_status().equals(""))
            {
                tv_delivery.setVisibility(View.VISIBLE);
                tv_delivery.setText(product.getDelivery_status());
            }
            else {
                tv_delivery.setVisibility(View.GONE);
            }

            if(product.getStock().equals("In Stock"))
            {
                 isStock = true;
            }
            else {
                isStock = false;
            }

            if(!product.getMinimum().equals("0")) {
                ll_quantity_select.setVisibility(View.VISIBLE);

                if(product.getOptions().isEmpty()) {
                    productDetailsPresenter.onLoadQuantities(product.getMinimum(), product.getQuantity());
                }
                else {
                    productDetailsPresenter.onLoadQuantities("1", product.getOptions().get(0).getProduct_option_value().get(0).getQuantity());

                }
            }
            else {
                ll_quantity_select.setVisibility(View.GONE);
            }

            if(!product.getOptions().isEmpty())
            {
                isCustomizable=true;
                ll_variations.setVisibility(View.VISIBLE);
                tv_variation_type.setText(product.getOptions().get(0).getName());
                product_option_id = product.getOptions().get(0).getProduct_option_id();
                product_option_value_id=product.getOptions().get(0).getProduct_option_value().get(0).getProduct_option_value_id();

                productDetailsPresenter.onLoadVariations(product.getOptions().get(0).getProduct_option_value());


            }
            else {
                isCustomizable=false;
                ll_variations.setVisibility(View.GONE);
            }


            if(!product.getImages().getImage().isEmpty())
            {
                rl_multiple.setVisibility(View.VISIBLE);


                if(!product.getThumb().equals(""))
                {
                  product.getImages().getImage().add(0,new ProductDetailsResponse.ProductBean.ImagesBean.ImageBean(product.getThumb(),product.getThumb()));
                }

                productDetailsPresenter.onMultipleImages(product.getImages().getImage());

            }
            else {
                rl_multiple.setVisibility(View.GONE);
            }
            if(relatedProduct!=null)
            {
                if(relatedProduct.size()>0)
                {
                    ll_similar_product.setVisibility(View.VISIBLE);
                    productDetailsPresenter.onLoadRelatedProducts(relatedProduct);

                }
                else {
                    ll_similar_product.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void changeImage(String url) {
        product_show_img=url;
        GlideApp.with(mContext)
                .load(url)
                .placeholder(R.drawable.no_image)
                .centerCrop()
                .into(iv_pro);
    }

    @Override
    public void hideDesc(boolean showDesc1) {
        showDesc=showDesc1;

        if(showDesc)
        {
            showDesc=false;
            tv_desc.setVisibility(View.GONE);
            btndesc.setImageResource(R.drawable.ic_top_arrow);

        }
        else {
            showDesc=true;
            tv_desc.setVisibility(View.VISIBLE);
            btndesc.setImageResource(R.drawable.ic_bottom_arrow);
        }


    }

    @Override
    public void hideSpec(boolean showSpec1) {
        showSpec=showSpec1;

        if(showSpec)
        {
            showSpec=false;
            tv_specs.setVisibility(View.GONE);
            btnspecific.setImageResource(R.drawable.ic_top_arrow);

        }
        else {
            showSpec=true;
            tv_specs.setVisibility(View.VISIBLE);
            btnspecific.setImageResource(R.drawable.ic_bottom_arrow);
        }
    }

    @Override
    public void loadQuantities(String min, String max) {

        System.out.println("minnnn"+" "+min+" "+max+" "+quantity_list);
        quantity_list = new ArrayList<>();
        int min1=Integer.parseInt(min);
        int max1=Integer.parseInt(max);

        for (int i = min1; i <=max1 ; i++) {
            quantity_list.add(i);
            System.out.println("last quantity"+" "+i);
        }

        sp_quantity.setAdapter(quantityAdapter);
        quantityAdapter.loadQuantities(quantity_list);

    }

    @Override
    public void openProductDetails(String pro_id) {
        Intent intent = new Intent(mContext,ProductDetailsActivity.class);
        intent.putExtra("product_id",pro_id);
        startActivity(intent);

    }

    @Override
    public void openZoomActivity(String product_img) {
        Bundle bundle = new Bundle();
        bundle.putString("ZoomImage",product_img);
        Intent intent = new Intent(mContext, ZoomActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void showAddReviewDialog() {

        addReviewDialogFragment = new AddReviewDialogFragment();
        Bundle bundle = new Bundle();

        bundle.putString("product_id", getIntent().getExtras().getString("product_id"));
        addReviewDialogFragment.setArguments(bundle);
        addReviewDialogFragment.show(getSupportFragmentManager(), "addReview");

    }

    @Override
    public void openReviewsActivity(String total_reviews) {

        Intent intent = new Intent(mContext, ReviewsListActivity.class);
        intent.putExtra("product_id",getIntent().getExtras().getString("product_id"));
        startActivity(intent);

    }

    @Override
    public void checkWishlistByUser() {
       like_button.setChecked(false);
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Please login to add this product to your wishlist");
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    productDetailsPresenter.onOpenLoginActivity();

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e){
            Timber.tag(TAG).e(e);
        }
    }

    @Override
    public void openLoginActvity() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(Constants.OPEN_FROM_LOGIN,1);
        if(getIntent().getExtras().getString("product_id")!=null)
        {
            intent.putExtra("product_id",getIntent().getExtras().getString("product_id"));
        }
        startActivityForResult(intent,OPEN_LOGIN);


    }

    @Override
    public void checkWish(ProductDetailsResponse.RelatedProductBean relatedProductBean, int pos) {
        relatedProductBean.setActiveWish(false);
        if(similarProductsAdapter!=null)
        {
            similarProductsAdapter.changeWish(relatedProductBean,pos);
        }

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setMessage("Please login to add this product to your wishlist");
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    productDetailsPresenter.onOpenLoginActivity();

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e){
            Timber.tag(TAG).e(e);
        }
    }

    @Override
    public void similarAddCart(String product_id, String quantity) {

        productDetailsPresenter.onAddtoCart(product_id,quantity);

    }



    @Override
    public void singleAddCart(String product_id, String quantity, boolean isCustomizable, String product_option_id, String product_option_value_id) {

        if(isCustomizable) {
            productDetailsPresenter.onCustomizableAddCart(product_id, quantity, isCustomizable, product_option_id, product_option_value_id);
        }
        else {
            productDetailsPresenter.onAddtoCart(getIntent().getExtras().getString("product_id"),quantity);
        }
    }

    @Override
    public void addToCartDone(int cart_count) {

        if(cart_count==0)
        {
            ll_cart_count.setVisibility(View.GONE);
        }
        else {
            ll_cart_count.setVisibility(View.VISIBLE);
            tv_cart_count.setText(String.valueOf(cart_count));
        }


    }

    @Override
    public void loadMultipleImages(List<ProductDetailsResponse.ProductBean.ImagesBean.ImageBean> image) {
        rv_multiplelist.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_multiplelist.setHasFixedSize(true);
        rv_multiplelist.setItemAnimator(new DefaultItemAnimator());
        rv_multiplelist.setNestedScrollingEnabled(false);
        rv_multiplelist.setAdapter(multipleAdapter);
        multipleAdapter.setAdapterListener(new MultipleImagesAdapter.MultipleImagesListener() {
            @Override
            public void onItemClick(ProductDetailsResponse.ProductBean.ImagesBean.ImageBean item, int position) {
                productDetailsPresenter.onChangeImage(item.getThumb());
            }
        });
        multipleAdapter.loadImages(image);
    }

    @Override
    public void loadVariations(List<ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean> product_option_value) {
        if(product_option_value.size()>0) {
            product_option_value.get(0).setSelected(true);
            product_option_value_id=product_option_value.get(0).getProduct_option_value_id();
            rv_variations.setHasFixedSize(true);
            rv_variations.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager variationLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            rv_variations.setLayoutManager(variationLayoutManager);
            rv_variations.setAdapter(variationsAdapter);
            rv_variations.setNestedScrollingEnabled(false);
            variationsAdapter.loadVariations(product_option_value);
            System.out.println("proopti"+product_option_value_id);
            variationsAdapter.setAdapterListener(new VariationsAdapter.VariationListener() {
                @Override
                public void onItemClick(ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean item, int position) {

                    product_option_value_id=item.getProduct_option_value_id();
                    System.out.println("proopti"+product_option_value_id);
                    variationsAdapter.changeItemBg(item,position);
                    if(quantity_list!=null)
                    {
                        quantity_list.clear();
                    }
                    productDetailsPresenter.onLoadQuantities("1", item.getQuantity());
                }
            });
        }
    }

    @Override
    public void loadRelatedProducts(List<ProductDetailsResponse.RelatedProductBean> relatedProduct) {
        if(relatedProduct.size()>0) {
            rv_similar.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            rv_similar.setHasFixedSize(true);
            rv_similar.setItemAnimator(new  DefaultItemAnimator());
            rv_similar.setNestedScrollingEnabled(false);
            rv_similar.setAdapter(similarProductsAdapter);
            similarProductsAdapter.loadProducts(relatedProduct);

            similarProductsAdapter.setAdapterListener(new SimilarProductsAdapter.RelatedProductListListener() {
                @Override
                public void onItemClick(ProductDetailsResponse.RelatedProductBean item, int position) {

                    productDetailsPresenter.onOpenProductDetails(item.getProduct_id());
                }

                @Override
                public void onWishSelected(ProductDetailsResponse.RelatedProductBean item, int position) {
                    productDetailsPresenter.onCheckWish(item,position);
                }

                @Override
                public void onAddtoCart(ProductDetailsResponse.RelatedProductBean item, int position, String quantity) {

                    productDetailsPresenter.onConfirmSimilarAddCart(item.getProduct_id(),quantity,item.getStock());
                }
            });
        }
    }

    @Override
    public void openCartActivity() {

        Intent intent = new Intent(mContext, CartActivity.class);
        startActivity(intent);

    }

    @Override
    public void addWishlist() {
        if(getIntent().getExtras()!=null) {
            productDetailsPresenter.onAddWishlist(getIntent().getExtras().getString("product_id"),product_option_id,product_option_value_id,isCustomizable);
        }
    }

    @Override
    public void updateWishDone(int wishlist_id) {
        wish_id = wishlist_id;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void setRatingText(String ratingText, String reviews){
      tv_rating.setText(reviews);
      rb_product.setRating(Float.parseFloat(ratingText));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
