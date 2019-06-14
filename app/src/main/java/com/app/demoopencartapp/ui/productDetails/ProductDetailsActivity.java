package com.app.demoopencartapp.ui.productDetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.local_models.ProductListBean;
import com.app.demoopencartapp.data.local_models.SpecsBean;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.data.network.models.ProductDetailsResponse;
import com.app.demoopencartapp.shared.base.BaseActivity;
import com.app.demoopencartapp.ui.SpecificationAdapter;
import com.app.demoopencartapp.ui.zoom.ZoomActivity;
import com.app.demoopencartapp.utils.GlideApp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    @BindView(R.id.rb_product)
    RatingBar rb_product;

    @BindView(R.id.ll_quantity_select)
    LinearLayout ll_quantity_select;

    @BindView(R.id.tv_variation_type)
    TextView tv_variation_type;

    @BindView(R.id.rv_variations)
    RecyclerView rv_variations;


    @Inject
    MultipleImagesAdapter multipleAdapter;

    @Inject
    QuantityAdapter quantityAdapter;

    @Inject
    VariationsAdapter variationsAdapter;

    @Inject
    SimilarProductsAdapter similarProductsAdapter;


    @Inject
    SpecificationAdapter specificationAdapter;

    @Inject
    ProductDetailsPresenter<ProductDetailsMvpView> productDetailsPresenter;

    ArrayList<ProductListBean> productListBeanArrayList;
    ArrayList<SpecsBean> specsBeanArrayList;

    public ArrayList<Integer> quantity_list ;

    String product_show_img="";

    public boolean showDesc = true;
    public boolean showSpec = true;

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

    }

    @OnClick({R.id.btndesc,R.id.btnspecific,R.id.iv_pro})
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



        }
    }

    @Override
    protected void onDestroy() {
        productDetailsPresenter.onDetach();
        super.onDestroy();
    }

    private void loadMultipleImages()
    {

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

    }

    private void loadSimilarProducts(List<ProductDetailsResponse.RelatedProductBean> relatedProduct)
    {
        if(relatedProduct.size()>0) {
            rv_similar.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            rv_similar.setHasFixedSize(true);
            rv_similar.setItemAnimator(new DefaultItemAnimator());
            rv_similar.setNestedScrollingEnabled(false);
            rv_similar.setAdapter(similarProductsAdapter);
            similarProductsAdapter.loadProducts(relatedProduct);

            similarProductsAdapter.setAdapterListener(new SimilarProductsAdapter.RelatedProductListListener() {
                @Override
                public void onItemClick(ProductDetailsResponse.RelatedProductBean item, int position) {

                    productDetailsPresenter.onOpenProductDetails(item.getProduct_id());
                }
            });
        }


    }

    private void loadVariations(List<ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean> product_option_value)
    {

        if(product_option_value.size()>0) {
            product_option_value.get(0).setSelected(true);
            rv_variations.setHasFixedSize(true);
            rv_variations.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager variationLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            rv_variations.setLayoutManager(variationLayoutManager);
            rv_variations.setAdapter(variationsAdapter);
            rv_variations.setNestedScrollingEnabled(false);
            variationsAdapter.loadVariations(product_option_value);

            variationsAdapter.setAdapterListener(new VariationsAdapter.VariationListener() {
                @Override
                public void onItemClick(ProductDetailsResponse.ProductBean.OptionsBean.ProductOptionValueBean item, int position) {

                    variationsAdapter.changeItemBg(item,position);
                }
            });
        }
    }




    @Override
    public void getProductDetails(ProductDetailsResponse.ProductBean product, List<ProductDetailsResponse.RelatedProductBean> relatedProduct) {


        if(product!=null)
        {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            tv_pro_name.setText(Html.fromHtml(product.getName()));
            tv_specs.setText(Html.fromHtml(product.getSpecification()));
            if(!product.getThumb().equals("")) {
                GlideApp.with(mContext)
                        .load(product.getThumb())
                        .placeholder(R.drawable.no_image)
                        .centerCrop()
                        .into(iv_pro);
                product_show_img=product.getThumb();
            }




            if(!product.getSpecial().equals(""))
            {
                tv_product_price.setText('\u20B9'+" "+decimalFormat.format(Double.parseDouble(product.getSpecial())));
                ll_offer_price.setVisibility(View.VISIBLE);
                tv_product_old__price.setText('\u20B9'+" "+decimalFormat.format(Double.parseDouble(product.getPrice())));
                tv_product_old__price.setPaintFlags( tv_product_old__price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                double offer = Math.round(((Double.parseDouble(product.getPrice())-Double.parseDouble(product.getSpecial()))/Double.parseDouble(product.getPrice()))*100);
                DecimalFormat df = new DecimalFormat("###.#");
                tv_offer.setText("("+df.format(offer)+"%"+" "+"OFF"+")");
                tv_product_old__price.setPaintFlags( tv_product_old__price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);



            }
            else {
               tv_product_price.setText('\u20B9'+" "+decimalFormat.format(Double.parseDouble(product.getPrice())));
                tv_product_old__price.setVisibility(View.GONE);
                ll_offer_price.setVisibility(View.GONE);

            }

            tv_desc.setText(Html.fromHtml(product.getDescription()));
            if(!product.getDelivery_status().equals(""))
            {
                tv_delivery.setVisibility(View.VISIBLE);
                tv_delivery.setText(product.getDelivery_status());
            }
            else {
                tv_delivery.setVisibility(View.GONE);
            }
            tv_manufacturer.setText("By - "+product.getManufacturer());
            tv_features.setText(Html.fromHtml(product.getFeatures()));
            tv_stock.setText(product.getStock());
            tv_rating.setText(String.valueOf(product.getRating()));
            rb_product.setRating(Float.parseFloat(String.valueOf(product.getRating())));


            if(!product.getMinimum().equals("0")) {
                ll_quantity_select.setVisibility(View.VISIBLE);
                productDetailsPresenter.onLoadQuantities(product.getMinimum(), product.getQuantity());
            }
            else {
                ll_quantity_select.setVisibility(View.GONE);
            }

            if(!product.getOptions().isEmpty())
            {
                ll_variations.setVisibility(View.VISIBLE);
                tv_variation_type.setText(product.getOptions().get(0).getName());
                loadVariations(product.getOptions().get(0).getProduct_option_value());

            }
            else {
                ll_variations.setVisibility(View.GONE);
            }


            if(!product.getImages().getImage().isEmpty())
            {
                rl_multiple.setVisibility(View.VISIBLE);


                if(!product.getThumb().equals(""))
                {
                  product.getImages().getImage().add(0,new ProductDetailsResponse.ProductBean.ImagesBean.ImageBean(product.getThumb(),product.getThumb()));
                }
                loadMultipleImages();

                multipleAdapter.loadImages(product.getImages().getImage());
            }
            else {
                rl_multiple.setVisibility(View.GONE);
            }
            if(relatedProduct!=null)
            {
                if(relatedProduct.size()>0)
                {
                    ll_similar_product.setVisibility(View.VISIBLE);
                    loadSimilarProducts(relatedProduct);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
