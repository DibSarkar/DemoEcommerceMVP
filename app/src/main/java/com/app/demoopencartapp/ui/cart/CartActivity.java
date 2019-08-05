package com.app.demoopencartapp.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.local_models.CartListBean;
import com.app.demoopencartapp.data.network.models.CartListResponse;
import com.app.demoopencartapp.shared.CookiesManage;
import com.app.demoopencartapp.shared.base.BaseActivity;

import com.app.demoopencartapp.ui.login.LoginActivity;
import com.app.demoopencartapp.ui.productDetails.ProductDetailsActivity;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends BaseActivity implements CartMvpView{

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_cart)
    RecyclerView rv_cart;

    @BindView(R.id.tv_total)
    TextView tv_total;

    @BindView(R.id.tv_sub_total)
    TextView tv_sub_total;

    @BindView(R.id.tv_total_gst)
    TextView tv_total_gst;

    @BindView(R.id.tv_no_cart)
    TextView tv_no_cart;

    @BindView(R.id.nsv_cart)
    NestedScrollView nsv_cart;

    @BindView(R.id.ll_btn)
    LinearLayout ll_btn;

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    BottomSheetBehavior sheetBehavior;

    @Inject
    CartAdapter cartAdapter;

    @Inject
    CartPresenter<CartMvpView> cartPresenter;

    ArrayList<CartListBean> cartListBeanArrayList;

    @BindView(R.id.btn_loggedin)
    Button btn_loggedin;

    @BindView(R.id.btn_guest)
    Button btn_guest;

    public static final int OPEN_LOGIN = 503;

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
      cartPresenter.onAttach(this);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      setTitle("");
      sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

      sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
          @Override
          public void onStateChanged(@NonNull View bottomSheet, int newState) {
              switch (newState) {
                  case BottomSheetBehavior.STATE_HIDDEN:
                      break;

                  case BottomSheetBehavior.STATE_EXPANDED:
                  break;

                  case BottomSheetBehavior.STATE_COLLAPSED:
                      break;

                  case BottomSheetBehavior.STATE_DRAGGING:
                      break;
                  case BottomSheetBehavior.STATE_SETTLING:
                      break;
              }
          }

          @Override
          public void onSlide(@NonNull View bottomSheet, float slideOffset) {

          }
      });
      cartPresenter.onGetCartList();

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
      cartAdapter.setAdapterListener(new CartAdapter.CartListener() {
          @Override
          public void onItemClick(CartListResponse.CartlistBean.ProductsBean item, int position) {

              cartPresenter.onOpenProductDetails(item.getProduct_id());
          }

          @Override
          public void onUpdate(CartListResponse.CartlistBean.ProductsBean item, int position, int old_value) {

              if(item.getOption().isEmpty())
              {
                  if(old_value>Integer.parseInt(item.getQuantity()))
                  {
                      Toast.makeText(mContext,"Quantity exceeded",Toast.LENGTH_SHORT).show();
                      cartAdapter.changeCartQuantity(item,old_value-1);
                      return;

                  }

                  if(old_value!=0) {
                      System.out.println("called for zero");
                      if (old_value < Integer.parseInt(item.getMinimum())) {
                          Toast.makeText(mContext, "You have to take minimum of this quantity", Toast.LENGTH_SHORT).show();
                          cartAdapter.changeCartQuantity(item, old_value + 1);
                          return;
                      }
                  }

              }
              else {
                  if(old_value>Integer.parseInt(item.getQuantity()))
                  {
                      Toast.makeText(mContext,"Quantity exceeded",Toast.LENGTH_SHORT).show();
                      cartAdapter.changeCartQuantity(item,old_value-1);
                      return;

                  }

                  if(old_value!=0) {
                      if (old_value < Integer.parseInt(item.getMinimum())) {
                          Toast.makeText(mContext, "You have to take minimum of this quantity", Toast.LENGTH_SHORT).show();
                          cartAdapter.changeCartQuantity(item, old_value + 1);
                          return;
                      }
                  }
                  else {

                          cartPresenter.onUpdateCart(item.getCart_id(),old_value);
                          return;

                  }
              }
              cartPresenter.onUpdateCart(item.getCart_id(),old_value);

          }

          @Override
          public void onDelete(CartListResponse.CartlistBean.ProductsBean item, int position) {
              cartPresenter.onCartDelete(item.getCart_id());
          }
      });

  }

    @Override
    public void getCartList(List<CartListResponse.CartlistBean.ProductsBean> products, String total, String subtotal, List<Integer> taxdata) {

        if(products.size()>0)
        {
            nsv_cart.setVisibility(View.VISIBLE);
            ll_btn.setVisibility(View.VISIBLE);
            tv_no_cart.setVisibility(View.GONE);
            loadCartItems();
            cartAdapter.loadProducts(products);

            if(!total.equals(""))
            {
                tv_total.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(total))));
            }
            if(!subtotal.equals(""))
            {
                tv_sub_total.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(subtotal))));
            }

            if(taxdata!=null)
            {
                if(taxdata.size()>0)
                {
                    double total_gst = 0;
                    for (int i=0;i<taxdata.size();i++)
                    {
                       total_gst= total_gst+(double)taxdata.get(i);
                    }
                    tv_total_gst.setText('\u20B9'+" "+String.valueOf(Math.round(total_gst)));

                }
            }

        }
        else {
            nsv_cart.setVisibility(View.GONE);
            ll_btn.setVisibility(View.GONE);
            tv_no_cart.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void updateDone() {
        cartPresenter.onGetCartList();
    }

    @Override
    public void openProductDetails(String pro_id) {

        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
        intent.putExtra("product_id",pro_id);
        startActivity(intent);
    }

    @Override
    public void openCheckoutAsUI(String user_id) {

        if(user_id!=null)
        {
            if(!user_id.equals(""))
            {
                showMessage("Under Development");
            }
            else {
                layoutBottomSheet.setVisibility(View.VISIBLE);
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    layoutBottomSheet.setVisibility(View.GONE);

                }
            }
        }
        else {
            layoutBottomSheet.setVisibility(View.VISIBLE);
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                layoutBottomSheet.setVisibility(View.GONE);

            }
        }

    }

    @Override
    public void openLogin() {

        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(Constants.OPEN_FROM_LOGIN,2);
        startActivityForResult(intent,OPEN_LOGIN);

    }

    @Override
    public void cartUpdate(int count) {
        if(count==0)
        {
           cartPresenter.onClearSession();
        }
        else {
            cartPresenter.onGetCartList();
        }

    }

    @Override
    public void clearSession() {
       //CookiesManage.removeCookies();
        cartPresenter.onGetCartList();
    }


    @Override
    protected void onDestroy() {
        cartPresenter.onDetach();
        super.onDestroy();
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

    @OnClick({R.id.btn_checkout,R.id.btn_loggedin,R.id.btn_guest})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.btn_checkout :
                cartPresenter.onOpenCheckoutAsUI();

                break;
            case R.id.btn_loggedin :
                cartPresenter.onOpenCheckoutAsUI();

                break;
            case R.id.btn_guest :
                cartPresenter.onOpenCheckoutAsUI();

                break;
        }

        }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(layoutBottomSheet.getVisibility()==View.VISIBLE) {
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                    Rect outRect = new Rect();
                    layoutBottomSheet.getGlobalVisibleRect(outRect);

                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        layoutBottomSheet.setVisibility(View.GONE);
                    }
                }
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        System.out.println("requestcodesss"+" "+requestCode+" "+resultCode);
    }
}
