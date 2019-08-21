package com.app.demoopencartapp.ui.cart;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.ShippingMethodsResponse;
import com.app.demoopencartapp.di.components.ActivityComponent;
import com.app.demoopencartapp.shared.base.BaseDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Shipping_Fragment extends BaseDialog implements Shipping_Fragment_MvpView {

    public static final String TAG = Shipping_Fragment.class.getSimpleName();



    double final_shipping_cost = 0;
    double final_shipping_tax_price = 0;

    ShippingMethodsResponse.ShippingBean.WeightBean weightBean;

    String title="";

    @BindView(R.id.rb_ship1)
    RadioButton rb_ship1;

    @BindView(R.id.rb_ship2)
    RadioButton rb_ship2;

    @BindView(R.id.rg_ship)
    RadioGroup rg_ship;

    static int radio_selected = 1;

    @Inject
    Shipping_Fragment_Presenter<Shipping_Fragment_MvpView> shipping_fragment_presenter;

    public static Shipping_Fragment  newInstance(int radio_selected1) {
        Shipping_Fragment fragment = new Shipping_Fragment();

        radio_selected=radio_selected1;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_dialog_shipping, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, v));


        }
        shipping_fragment_presenter.onAttach(this);
        //shipping_fragment_presenter.onAttach(this);
        if(getArguments().getSerializable("weights")!= null){

            weightBean= (ShippingMethodsResponse.ShippingBean.WeightBean) getArguments().getSerializable("weights");
            System.out.println("weightsss"+" "+weightBean.getTitle());
            rb_ship1.setText(weightBean.getQuote().getWeight_5().getTitle()+"("+weightBean.getQuote().getWeight_5().getText()+")");
            rb_ship2.setText(weightBean.getQuote().getWeight_6().getTitle()+"("+weightBean.getQuote().getWeight_6().getText()+")");

            if(radio_selected==1)
            {
                rb_ship1.setChecked(true);
                title = weightBean.getQuote().getWeight_5().getTitle();
                final_shipping_cost = Double.parseDouble(weightBean.getQuote().getWeight_5().getCost());
                final_shipping_tax_price = Double.parseDouble(weightBean.getQuote().getWeight_5().getTax());

            }
            else {
                rb_ship2.setChecked(true);
                title = weightBean.getQuote().getWeight_6().getTitle();
                final_shipping_cost = Double.parseDouble(weightBean.getQuote().getWeight_6().getCost());
                final_shipping_tax_price = Double.parseDouble(weightBean.getQuote().getWeight_6().getTax());

            }


        }
        setUp(v);
        return v;
    }

    @Override
    protected void setUp(View view) {
        setUnBinder(ButterKnife.bind(this,view));

        rg_ship.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
              switch (checkedId) {

                  case R.id.rb_ship1:
                      title = weightBean.getQuote().getWeight_5().getTitle();
                      final_shipping_cost = Double.parseDouble(weightBean.getQuote().getWeight_5().getCost());
                      final_shipping_tax_price = Double.parseDouble(weightBean.getQuote().getWeight_5().getTax());
                      radio_selected = 1;
                      break;

                  case R.id.rb_ship2:
                      title = weightBean.getQuote().getWeight_6().getTitle();
                      final_shipping_cost =  Double.parseDouble(weightBean.getQuote().getWeight_6().getCost());
                      final_shipping_tax_price = Double.parseDouble(weightBean.getQuote().getWeight_6().getTax());
                      radio_selected = 2;
                      break;

              }
          }
      });


    }


    @Override
    public void onDestroyView() {
        shipping_fragment_presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void dismissDialog(String tag) {
        super.dismissDialog(tag);
    }

    @OnClick({R.id.btn_apply,R.id.btn_cancel})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_apply:

                dismissDialog("");

                shipping_fragment_presenter.onSendShippingData(title,final_shipping_tax_price,final_shipping_cost,radio_selected);

                break;

            case R.id.btn_cancel :
                dismissDialog("");
                break;
        }

    }

    @Override
    public void showAlert(String message) {

    }

    @Override
    public void showInactiveUserAlert(String message) {

    }

    @Override
    public void logOut() {

    }

    @Override
    public void getSendShippingData(String title, double cost, double tax, int radio_selected) {

        ((CartActivity) getActivity()).setShippingText(title,cost,tax,radio_selected);

    }

    @Override
    public void closeDialog() {

    }
}
