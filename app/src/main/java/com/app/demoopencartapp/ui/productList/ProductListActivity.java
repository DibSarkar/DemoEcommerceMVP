package com.app.demoopencartapp.ui.productList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.shared.base.BaseActivity;
import com.app.demoopencartapp.ui.cart.CartActivity;
import com.app.demoopencartapp.ui.login.LoginActivity;
import com.app.demoopencartapp.ui.productDetails.ProductDetailsActivity;
import com.app.demoopencartapp.utils.Constants;
import com.baoyz.actionsheet.ActionSheet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class ProductListActivity extends BaseActivity implements ProductListMvpView,ActionSheet.ActionSheetListener {

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_productlist)
    RecyclerView rv_productlist;

    @BindView(R.id.fb_list)
    FloatingActionButton fb_list;

    @BindView(R.id.bottom_sheet)
    View bottom_sheet;

    @BindView(R.id.tv_noproduct)
    TextView tv_noproduct;

    @BindView(R.id.ll_products)
    LinearLayout ll_products;

    @BindView(R.id.ll_cart_count)
    LinearLayout ll_cart_count;

    @BindView(R.id.tv_cart_count)
    TextView tv_cart_count;

    ProductListAdapter productListAdapter;

    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;

    @Inject
    ProductListPresenter<ProductListMvpView> productListPresenter;

    int status=0;
    private BottomSheetBehavior mBottomSheetBehavior;

    List<CategoriesProductsResponse.ProductBean> productBeanList;
    public static final int OPEN_LOGIN = 502;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_list);
        mContext = this;
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));
        setUp();
    }


    private void setUp() {
        productListPresenter.onAttach(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        mBottomSheetBehavior.setPeekHeight(6);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new GridLayoutManager(mContext, 2);


        rv_productlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){
                    fb_list.setVisibility(View.INVISIBLE);
                } else{
                    fb_list.setVisibility(View.VISIBLE);
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });

        getProducts("","");


    }

    @Override
    protected void onDestroy() {
        productListPresenter.onDetach();
        super.onDestroy();
    }

    @OnClick({R.id.fb_list,R.id.iv_sort,R.id.iv_cart})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.fb_list :
                if(status==0)
                {
                    fb_list.setImageResource(R.drawable.grid_view);
                    status=1;
                    loadProducts(status);

                }
                else if(status==1)
                {
                    fb_list.setImageResource(R.drawable.menupro);
                    status=0;
                    loadProducts(status);
                }
                break;

            case R.id.iv_sort :
                productListPresenter.onOpenSort();
                break;

            case R.id.iv_cart :
                productListPresenter.onOpenCartActivity();
                break;

        }
    }

    public void getProducts(String priority,String order)
    {
        if(getIntent().getExtras().getString("sub_category_id")!=null)
        {
            productListPresenter.onGetProductList(priority,order,getIntent().getExtras().getString("sub_category_id"));

        }
    }

    private void loadProducts(int status1)
    {
        productListAdapter = new ProductListAdapter(this,mContext,productBeanList,status);

        rv_productlist.setHasFixedSize(true);

        rv_productlist.setNestedScrollingEnabled(false);
        rv_productlist.setItemAnimator(new DefaultItemAnimator());
        if(status1==1)
        {

        linearLayoutManager.scrollToPosition(0);
        rv_productlist.setLayoutManager(linearLayoutManager);
        linearLayoutManager.scrollToPositionWithOffset(0, 0);
        productListAdapter.notifyDataSetChanged();
        }
        else {
            rv_productlist.setLayoutManager(gridLayoutManager);
            gridLayoutManager.scrollToPositionWithOffset(0, 0);
            rv_productlist.setLayoutManager(gridLayoutManager);
            productListAdapter.notifyDataSetChanged();
        }

        rv_productlist.setAdapter(productListAdapter);

        productListAdapter.setAdapterListener(new ProductListAdapter.ProductListListener() {
            @Override
            public void onItemClick(CategoriesProductsResponse.ProductBean item, int position) {

                productListPresenter.onOpenProductDetails(item.getProduct_id());

            }

            @Override
            public void onWishSelected(CategoriesProductsResponse.ProductBean item, int position) {

                productListPresenter.onCheckWish(item,position);

            }

            @Override
            public void onAddtoCart(CategoriesProductsResponse.ProductBean item, int position, String quantity) {

                productListPresenter.onConfirmAddCart(item.getProduct_id(),quantity,item.getStock());

            }
        });




    }


    @SuppressLint("RestrictedApi")
    @Override
    public void getProducts(List<CategoriesProductsResponse.ProductBean> product, int total_qty) {

        if(total_qty==0)
        {
            ll_cart_count.setVisibility(View.GONE);
        }
        else {
            ll_cart_count.setVisibility(View.VISIBLE);
            tv_cart_count.setText(String.valueOf(total_qty));
        }
        if(product.size()>0)
        {

            productBeanList = new ArrayList<>();
            if(productBeanList!=null)
            {
                productBeanList.clear();
            }
            fb_list.setVisibility(View.VISIBLE);
            ll_products.setVisibility(View.VISIBLE);
            productBeanList.addAll(product);
            rv_productlist.setVisibility(View.VISIBLE);
            loadProducts(status);
            productListAdapter.loadProducts(product);
        }
        else {
            tv_noproduct.setVisibility(View.VISIBLE);
            fb_list.setVisibility(View.INVISIBLE);
            ll_products.setVisibility(View.GONE);
            rv_productlist.setVisibility(View.GONE);
        }
    }

    @Override
    public void openSortUI() {
        mBottomSheetBehavior.setPeekHeight(4);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles("Default", "Price (Low to High)", "Price (High to Low)", "Rating (Highest)", "Rating (Lowest) ")
                .setCancelableOnTouchOutside(true)
                .setListener(this).show();
    }

    @Override
    public void openProductDetails(String pro_id) {

        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
        intent.putExtra("product_id",pro_id);
        startActivity(intent);

    }

    @Override
    public void checkWish(CategoriesProductsResponse.ProductBean productBean, int pos) {
        productBean.setActiveWish(false);
        if(productListAdapter!=null)
        {
            productListAdapter.changeWish(productBean,pos);
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
                    productListPresenter.onOpenLoginActivity();

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e){
            Timber.tag(TAG).e(e);
        }
    }

    @Override
    public void openLoginActivity() {

        Intent intent1 = new Intent(mContext, LoginActivity.class);
        intent1.putExtra(Constants.OPEN_FROM_LOGIN,0);
        startActivityForResult(intent1,OPEN_LOGIN);
    }

    @Override
    public void addToCart(String product_id, String quantity) {

        productListPresenter.onAddtoCart(product_id,quantity);
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
    public void openCartActivity() {

        Intent intent = new Intent(mContext, CartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {



        if(index==0)
        {
            getProducts("","");
        }
        else if(index==1)
        {
            getProducts(Constants.PRIORITY_PRICE,Constants.ASC_ORDER);
        }
        else if(index==2)
        {
            getProducts(Constants.PRIORITY_PRICE,Constants.DESC_ORDER);
        }
        else if(index==3)
        {
            getProducts(Constants.PRIORITY_RATING,Constants.DESC_ORDER);
        }
        else if(index==4)
        {
            getProducts(Constants.PRIORITY_RATING,Constants.ASC_ORDER);
        }
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

