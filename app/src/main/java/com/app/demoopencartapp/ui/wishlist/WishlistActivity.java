package com.app.demoopencartapp.ui.wishlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.app.demoopencartapp.data.local_models.CartListBean;
import com.app.demoopencartapp.data.network.models.CategoriesProductsResponse;
import com.app.demoopencartapp.shared.base.BaseActivity;

import com.app.demoopencartapp.ui.productDetails.ProductDetailsActivity;
import com.app.demoopencartapp.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishlistActivity extends BaseActivity implements WishlistMvpView{

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ll_wishlist)
    LinearLayout ll_wishlist;

    @BindView(R.id.rv_wishlist)
    RecyclerView rv_wishlist;

    @BindView(R.id.tv_no_wishlist)
    TextView tv_no_wishlist;

    @Inject
    WishlistAdapter wishlistAdapter;

    @Inject
    WishlistPresenter<WishlistMvpView> wishlistPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wishlist);
        mContext = this;
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));
        setUp();
    }


    private void setUp()
    {
        wishlistPresenter.onAttach(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        wishlistPresenter.onGetWishlist();


    }

    @Override
    protected void onDestroy() {
        wishlistPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void loadProducts() {


        rv_wishlist.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_wishlist.setItemAnimator(new DefaultItemAnimator());
        rv_wishlist.setNestedScrollingEnabled(false);
    /*    Drawable dividerDrawable = ContextCompat.getDrawable(mContext, R.drawable.divider);
        rv_wishlist.addItemDecoration(new DividerItemDecoration(dividerDrawable,false,false));*/
        rv_wishlist.setAdapter(wishlistAdapter);
        wishlistAdapter.setAdapterListener(new WishlistAdapter.WishlistProductListener() {
            @Override
            public void onItemClick(CategoriesProductsResponse.ProductBean item, int position) {

                wishlistPresenter.onOpenProductDetails(item.getProduct_id());
            }

            @Override
            public void onWishDeleteSelected(CategoriesProductsResponse.ProductBean item, int position) {

            }


            @Override
            public void onAddtoCart(CategoriesProductsResponse.ProductBean item, int position, String quantity) {


            }
        });

    }

    @Override
    public void getWislist(List<CategoriesProductsResponse.ProductBean> product, int total_qty) {

        if(product.size()>0)
        {
            tv_no_wishlist.setVisibility(View.GONE);
            ll_wishlist.setVisibility(View.VISIBLE);
            wishlistAdapter.loadWishlistProducts(product);
            wishlistPresenter.onLoadProducts();
        }
        else {
            tv_no_wishlist.setVisibility(View.VISIBLE);
            ll_wishlist.setVisibility(View.GONE);
        }


    }

    @Override
    public void openProductDetails(String pro_id) {
        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
        intent.putExtra("product_id",pro_id);
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

