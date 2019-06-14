package com.app.demoopencartapp.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.local_models.CartListBean;
import com.app.demoopencartapp.shared.base.BaseActivity;
import com.app.demoopencartapp.utils.DividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends BaseActivity {

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_cart)
    RecyclerView rv_cart;

    @Inject
    CartAdapter cartAdapter;

    ArrayList<CartListBean> cartListBeanArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);
        mContext = this;
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));
        setUp();
    }


  private void setUp()
  {
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      setTitle("");
      loadCartItems();

  }

  private void loadCartItems()
  {

      cartListBeanArrayList = new ArrayList<>();
      rv_cart.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
      rv_cart.setItemAnimator(new DefaultItemAnimator());
      rv_cart.setNestedScrollingEnabled(false);
      Drawable dividerDrawable = ContextCompat.getDrawable(mContext, R.drawable.divider);
      rv_cart.addItemDecoration(new DividerItemDecoration(dividerDrawable,false,false));
      rv_cart.setAdapter(cartAdapter);

      cartListBeanArrayList.add(new CartListBean("1","","Test Product","TEST 123","500","1"));
      cartListBeanArrayList.add(new CartListBean("2","","Test Product","TEST 123","500","1"));
      cartListBeanArrayList.add(new CartListBean("3","","Test Product","TEST 123","500","1"));
      cartAdapter.loadProducts(cartListBeanArrayList);
  }

}
