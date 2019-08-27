package com.app.demoopencartapp.ui.checkout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.AddressListResponse;
import com.app.demoopencartapp.data.network.models.PaymentMethodsResponse;
import com.app.demoopencartapp.data.network.models.ShippingMethodsResponse;
import com.app.demoopencartapp.shared.base.BaseActivity;
import com.app.demoopencartapp.ui.addressBook.AddressBookActivity;
import com.app.demoopencartapp.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckoutActivity extends BaseActivity implements CheckoutMvpView {

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    CheckoutPresenter<CheckoutMvpView> checkoutPresenter;

    @BindView(R.id.rb_online)
    RadioButton rb_online;

    @BindView(R.id.rb_cod)
    RadioButton rb_cod;

    @BindView(R.id.rb_ship1)
    RadioButton rb_ship1;

    @BindView(R.id.rb_ship2)
    RadioButton rb_ship2;

    @BindView(R.id.ll_shipping)
    LinearLayout ll_shipping;

    @BindView(R.id.rl_shipping)
    RelativeLayout rl_shipping;

    @BindView(R.id.rg_shipping_type)
    RadioGroup rg_shipping_type;

    @BindView(R.id.rg_payment_type)
    RadioGroup rg_payment_type;

    @BindView(R.id.tv_billing_address)
    TextView tv_billing_address;

    @BindView(R.id.tv_delivery_address)
    TextView tv_delivery_address;

    @BindView(R.id.tv_total)
    TextView tv_total;

    @BindView(R.id.tv_sub_total)
    TextView tv_sub_total;

    @BindView(R.id.tv_total_gst)
    TextView tv_total_gst;

    @BindView(R.id.tv_shipping_title)
    TextView tv_shipping_title;

    @BindView(R.id.tv_shipping_price)
    TextView tv_shipping_price;

    @BindView(R.id.ll_payment_method)
    LinearLayout ll_payment_method;

    @BindView(R.id.ll_delivery_details)
    LinearLayout ll_delivery_details;

    @BindView(R.id.btn_confirm)
    Button btn_confirm;

    double shipping_cost1 = 0;
    double shipping_tax_price1 = 0;
    double final_shipping_cost = 0;
    double final_shipping_tax_price = 0;
    double shipping_cost2 = 0;
    double shipping_tax_price2 = 0;
    ShippingMethodsResponse.ShippingBean.WeightBean weightBean;
    String weight_code = "";
    String weight_title = "";
    String country_id = "";
    String zone_id = "";
    String title = "";
    String payment_code = "";
    String payment_title = "";
    boolean isShipping = false;
    public static final int OPEN_ADDRESS = 1001;
    public static int SEND_BILLING = 1002;
    public static int SEND_SHIPPING = 1003;

    String total = "";
    String sub_total = "";
    String total_gst = "";
    String shipping_total = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        mContext = this;
        setUnBinder(ButterKnife.bind(this));
        getActivityComponent().inject(this);
        setUp();
    }

    private void setUp() {
        checkoutPresenter.onAttach(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        if (getIntent().getExtras() != null) {
            isShipping = getIntent().getExtras().getBoolean("isShipping", false);
            country_id = getIntent().getExtras().getString("country_id", "");
            zone_id = getIntent().getExtras().getString("zone_id", "");
            total = getIntent().getExtras().getString("total_price", "");
            sub_total = getIntent().getExtras().getString("sub_total", "");
            total_gst = getIntent().getExtras().getString("total_gst", "");
            tv_total_gst.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(total_gst))));
            tv_sub_total.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(sub_total))));
            tv_total.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(total))));

            if (isShipping) {

                ll_shipping.setVisibility(View.VISIBLE);
                rl_shipping.setVisibility(View.VISIBLE);
                ll_delivery_details.setVisibility(View.VISIBLE);
                weight_code =  getIntent().getExtras().getString("weight_code", "");
                weight_title =  getIntent().getExtras().getString("weight_title", "");
                shipping_total = getIntent().getExtras().getString("shipping_cost", "");
                tv_shipping_price.setText('\u20B9'+" "+String.valueOf(Math.round(Double.parseDouble(shipping_total))));
                tv_shipping_title.setText(weight_title);
                if(weight_code.equals("weight.weight_5"))
                {
                    rb_ship1.setChecked(true);
                }
                else {
                    rb_ship2.setChecked(true);
                }
            } else {
                ll_shipping.setVisibility(View.GONE);
                rl_shipping.setVisibility(View.GONE);
                ll_delivery_details.setVisibility(View.GONE);
            }

         //   if (country_id.equals("")) {
                checkoutPresenter.onGetAddress();
           /* } else {
                checkoutPresenter.onGetPaymentMethods(country_id, zone_id);
            }
*/

        }

        rg_shipping_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rb_ship1:
                        title = weightBean.getQuote().getWeight_5().getTitle();
                        final_shipping_cost = Double.parseDouble(weightBean.getQuote().getWeight_5().getCost());
                        final_shipping_tax_price = Double.parseDouble(weightBean.getQuote().getWeight_5().getTax());

                        break;

                    case R.id.rb_ship2:
                        title = weightBean.getQuote().getWeight_6().getTitle();
                        final_shipping_cost = Double.parseDouble(weightBean.getQuote().getWeight_6().getCost());
                        final_shipping_tax_price = Double.parseDouble(weightBean.getQuote().getWeight_6().getTax());

                        break;

                }
            }
        });


        rg_payment_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rb_online:
                        payment_code = "cod";
                        payment_title = "Cash On Delivery";

                        break;

                  /*  case R.id.rb_cod:

                        break;*/

                }
            }
        });

    }

    @OnClick({R.id.tv_change_billing, R.id.tv_change_delivery})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.tv_change_billing:
                checkoutPresenter.onOpenAddress(1);
                break;
            case R.id.tv_change_delivery:
                checkoutPresenter.onOpenAddress(2);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        checkoutPresenter.onDetach();
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


    @Override
    public void getPaymentMethods(PaymentMethodsResponse.PaymentBean payment) {
        if (payment != null) {
            ll_payment_method.setVisibility(View.VISIBLE);
            btn_confirm.setVisibility(View.VISIBLE);

            if(payment.getCod()!=null)
            {
                payment_code = payment.getCod().getCode();
                payment_title = payment.getCod().getTitle();
            }


        } else {
            ll_payment_method.setVisibility(View.GONE);
            btn_confirm.setVisibility(View.GONE);
        }

        if (isShipping) {
            checkoutPresenter.onGetShippingMethods(country_id, zone_id);
        }

    }

    @Override
    public void getShippingMethods(ShippingMethodsResponse.ShippingBean.WeightBean weight) {
        if (weight != null) {

            weightBean = weight;
            shipping_cost1 = Double.parseDouble(weight.getQuote().getWeight_5().getCost());
            shipping_tax_price1 = Double.parseDouble(weight.getQuote().getWeight_5().getTax());

            shipping_cost2 = Double.parseDouble(weight.getQuote().getWeight_6().getCost());
            shipping_tax_price2 = Double.parseDouble(weight.getQuote().getWeight_6().getTax());

            rb_ship1.setText(weightBean.getQuote().getWeight_5().getTitle() + "(" + weightBean.getQuote().getWeight_5().getText() + ")");
            rb_ship2.setText(weightBean.getQuote().getWeight_6().getTitle() + "(" + weightBean.getQuote().getWeight_6().getText() + ")");


        }
    }

    @Override
    public void openAddress(int flag) {
        Intent intent = new Intent(mContext, AddressBookActivity.class);
        intent.putExtra(Constants.OPEN_FROM_CHECKOUT, flag);
        startActivityForResult(intent, OPEN_ADDRESS);

    }

    @Override
    public void getAddress(String country_id, String zone_id, String address_1, String address_2, String zone, String city, String country, String postcode) {

        this.country_id = country_id;
        this.zone_id = zone_id;


        if(isShipping)
        {
          tv_delivery_address.setText(address_1+", "+address_2+", "+zone+", "+city+", "+country+", "+postcode);
        }
        tv_billing_address.setText(address_1+", "+address_2+", "+zone+", "+city+", "+country+", "+postcode);

        checkoutPresenter.onGetPaymentMethods(this.country_id, this.zone_id);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_ADDRESS) {

            if (resultCode == SEND_BILLING) {
                System.out.println("called from billing_address");
                if (data != null) {
                    if (data.getExtras() != null) {
                        Bundle args = data.getBundleExtra("billing_address");
                        AddressListResponse.ResponseDataBean billing_address = (AddressListResponse.ResponseDataBean) args.getSerializable("billing_address");
                        tv_billing_address.setText(billing_address.getAddress_1() + ", " + billing_address.getAddress_2() + ", " + billing_address.getCity() + ", " + billing_address.getPostcode() + ", " + billing_address.getZone() + ", " + billing_address.getCountry());
                        country_id = billing_address.getCountry_id();
                        zone_id = billing_address.getZone_id();
                        checkoutPresenter.onGetPaymentMethods(country_id, zone_id);

                    }
                }


            } else if (resultCode == SEND_SHIPPING) {
                System.out.println("called from shipping_address");
                if (data != null) {
                    if (data.getExtras() != null) {
                        Bundle args = data.getBundleExtra("shipping_address");
                        AddressListResponse.ResponseDataBean shipping_address = (AddressListResponse.ResponseDataBean) args.getSerializable("shipping_address");
                        tv_delivery_address.setText(shipping_address.getAddress_1() + ", " + shipping_address.getAddress_2() + ", " + shipping_address.getCity() + ", " + shipping_address.getPostcode() + ", " + shipping_address.getZone() + ", " + shipping_address.getCountry());
                        country_id = shipping_address.getCountry_id();
                        zone_id = shipping_address.getZone_id();
                        checkoutPresenter.onGetPaymentMethods(country_id, zone_id);

                    }
                }
            }


        }
    }
}
