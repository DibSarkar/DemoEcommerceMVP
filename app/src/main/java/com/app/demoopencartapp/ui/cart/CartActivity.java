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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.demoopencartapp.R;

import com.app.demoopencartapp.data.network.models.CartListResponse;
import com.app.demoopencartapp.data.network.models.CountriesStatesResponse;
import com.app.demoopencartapp.data.network.models.ShippingMethodsResponse;
import com.app.demoopencartapp.shared.CookiesManage;
import com.app.demoopencartapp.shared.base.BaseActivity;

import com.app.demoopencartapp.ui.addAddress.CountryStateAdpater;
import com.app.demoopencartapp.ui.checkout.CheckoutActivity;
import com.app.demoopencartapp.ui.home.MainActivity;
import com.app.demoopencartapp.ui.login.LoginActivity;
import com.app.demoopencartapp.ui.productDetails.AddReviewDialogFragment;
import com.app.demoopencartapp.ui.productDetails.ProductDetailsActivity;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.DividerItemDecoration;

import java.text.DecimalFormat;
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

    @BindView(R.id.ll_shipping)
    LinearLayout ll_shipping;

    BottomSheetBehavior sheetBehavior;

    @Inject
    CartAdapter cartAdapter;

    @Inject
    CartPresenter<CartMvpView> cartPresenter;

    @BindView(R.id.btn_loggedin)
    Button btn_loggedin;

    @BindView(R.id.btn_guest)
    Button btn_guest;

    @BindView(R.id.sp_country)
    Spinner sp_country;

    @BindView(R.id.sp_state)
    Spinner sp_state;

    @BindView(R.id.rl_shipping)
    RelativeLayout rl_shipping;

    @BindView(R.id.tv_shipping_title)
    TextView tv_shipping_title;

    @BindView(R.id.tv_shipping_price)
    TextView tv_shipping_price;

    @Inject
    CountryStateAdpater countryAdpater;

    @Inject
    CountryStateAdpater stateAdapter;

    String country_id="",state_id="";

    double shipping_cost1 = 0;
    double shipping_tax_price1 = 0;
    double total_price=0;

    double shipping_cost2 = 0;
    double shipping_tax_price2 = 0;
    double total_gst = 0;
    int radio_selected = 1;

    private boolean isShipping = false;
    public static final int OPEN_LOGIN = 503;
    List<CountriesStatesResponse.DataBean> countries;
    List<CountriesStatesResponse.DataBean> states;
    ShippingMethodsResponse.ShippingBean.WeightBean weightBean;

    @Inject
    Shipping_Fragment shipping_fragment;

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


      sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              cartPresenter.onGetStateByCountry(countries.get(position).getId());
              country_id=countries.get(position).getId();

              if(!state_id.equals(""))
              {
                  cartPresenter.onGetShippingMethods(country_id,state_id);

              }

          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });

      sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(states.get(position).getId()!=null)
              {
                  state_id=states.get(position).getId();

                  if(!country_id.equals(""))
                  {
                      cartPresenter.onGetShippingMethods(country_id,state_id);
                  }
              }

          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });




      cartPresenter.onGetCartList();

  }

  private void loadCartItems()
  {


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
                total_price=Double.parseDouble(total);
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

                    for (int i=0;i<taxdata.size();i++)
                    {
                       total_gst= total_gst+(double)taxdata.get(i);
                    }
                    tv_total_gst.setText('\u20B9'+" "+String.valueOf(Math.round(total_gst)));

                }
            }
            cartPresenter.onGetCountries();
            for (int i = 0; i <products.size() ; i++) {
                if(products.get(i).getShipping().equals("1"))
                {
                    isShipping = true;

                    ll_shipping.setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    ll_shipping.setVisibility(View.GONE);
                    isShipping = false;
                }
            }




        }
        else {
            nsv_cart.setVisibility(View.GONE);
            ll_btn.setVisibility(View.GONE);
            tv_no_cart.setVisibility(View.VISIBLE);
        }


    }

    public void setShippingText(String title, double cost, double tax, int radio_selected){

        if(!title.isEmpty())
        {
            rl_shipping.setVisibility(View.VISIBLE);
            tv_shipping_title.setText(title);
            tv_shipping_price.setText('\u20B9'+" "+new DecimalFormat("0.00").format(cost));
            tv_total_gst.setText('\u20B9'+" "+String.valueOf(Math.round((double)(total_gst+tax))));
            tv_total.setText('\u20B9'+" "+String.valueOf(Math.round((double)(total_price+cost+tax))));

        }

        this.radio_selected = radio_selected;



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
             cartPresenter.onOpenCheckout();
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
    public void getAllCountries(List<CountriesStatesResponse.DataBean> data) {
        if(data.size()>0) {
            countries = new ArrayList<>();
            countries=data;
            sp_country.setAdapter(countryAdpater);
            countryAdpater.loadAllCountries(countries);
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).getId().equals("99"))
                {
                    sp_country.setSelection(i);
                    return;
                }

            }


        }
    }

    @Override
    public void getAllStatesByCountry(List<CountriesStatesResponse.DataBean> data) {
        if(data.size()>0) {
            states = new ArrayList<>();
            states=data;
            states.add(0,new CountriesStatesResponse.DataBean("0","Please Select"));
            sp_state.setAdapter(stateAdapter);
            stateAdapter.loadStates(states);
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).getId().equals("1506"))
                {
                    sp_state.setSelection(i);
                    return;
                }

            }

        }
    }

    @Override
    public void getShippingMethods(ShippingMethodsResponse.ShippingBean.WeightBean weight) {

        if(weight!=null)
        {

            weightBean = weight;

            shipping_cost1 = Double.parseDouble(weight.getQuote().getWeight_5().getCost());
            shipping_tax_price1 = Double.parseDouble(weight.getQuote().getWeight_5().getTax());

            shipping_cost2 = Double.parseDouble(weight.getQuote().getWeight_6().getCost());
            shipping_tax_price2 = Double.parseDouble(weight.getQuote().getWeight_6().getTax());
        }

    }

    @Override
    public void openShippingMethods() {

        shipping_fragment = Shipping_Fragment.newInstance(radio_selected);
        Bundle bundle = new Bundle();

        bundle.putSerializable("weights", weightBean);
        shipping_fragment.setArguments(bundle);
        shipping_fragment.show(getSupportFragmentManager(), "selectshipping");
    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        finish();
        startActivity(intent);

    }

    @Override
    public void openCheckout() {

        Intent intent = new Intent(mContext, CheckoutActivity.class);
        startActivity(intent);
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

    @OnClick({R.id.btn_checkout,R.id.btn_loggedin,R.id.btn_guest,R.id.btn_get_quotes,R.id.btn_continue})
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

            case R.id.btn_get_quotes :
                cartPresenter.onOpenShippingMethods();
                break;

            case R.id.btn_continue :
                cartPresenter.onFinish();
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
