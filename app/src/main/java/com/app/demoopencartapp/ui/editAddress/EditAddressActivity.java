package com.app.demoopencartapp.ui.editAddress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.CountriesStatesResponse;
import com.app.demoopencartapp.shared.base.BaseActivity;
import com.app.demoopencartapp.ui.addAddress.CountryStateAdpater;
import com.app.demoopencartapp.ui.addressBook.AddressBookActivity;
import com.app.demoopencartapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditAddressActivity extends BaseActivity implements EditAddressMvpView {

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.sp_country)
    Spinner sp_country;

    @BindView(R.id.sp_state)
    Spinner sp_state;

    @BindView(R.id.et_fname)
    EditText et_fname;

    @BindView(R.id.et_lname)
    EditText et_lname;

    @BindView(R.id.et_comp)
    EditText et_comp;

    @BindView(R.id.et_address1)
    EditText et_address1;

    @BindView(R.id.et_address2)
    EditText et_address2;

    @BindView(R.id.et_pin)
    EditText et_pin;

    @BindView(R.id.et_city)
    EditText et_city;

    @BindView(R.id.et_gstin)
    EditText et_gstin;

    @BindView(R.id.rg_default)
    RadioGroup rg_default;

    @BindView(R.id.rb_yes)
    RadioButton rb_yes;

    @BindView(R.id.rb_no)
    RadioButton rb_no;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ll_default)
    LinearLayout ll_default;

    @BindView(R.id.btn_add)
    Button btn_add;






    @Inject
    CountryStateAdpater countryAdpater;

    @Inject
    CountryStateAdpater stateAdapter;

    @Inject
    EditAddressPresenter<EditAddressMvpView> editAddressPresenter;

    List<CountriesStatesResponse.DataBean> countries;
    List<CountriesStatesResponse.DataBean> states;

    String country_id,state_id;

    int default_address = 1;
    String address_id="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_addressbook);
        mContext = this;
        setUnBinder(ButterKnife.bind(this));
        getActivityComponent().inject(this);
        setUp();
    }


    private void setUp() {
        editAddressPresenter.onAttach(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");





        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                editAddressPresenter.onGetStateByCountry(countries.get(position).getId());
                country_id=countries.get(position).getId();

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

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rg_default.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rb_yes:
                        default_address = 1;

                        break;
                    case R.id.rb_no:
                        default_address = 0;
                        break;

                }
            }
        });

        editAddressPresenter.onUpdateTextUI();



    }


    @OnClick({R.id.btn_add})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.btn_add :
                editAddressPresenter.onConfirmEditAddress(et_fname.getText().toString(),et_lname.getText().toString(),et_comp.getText().toString(),et_address1.getText().toString(),et_address2.getText().toString(),et_city.getText().toString(),et_pin.getText().toString(),country_id,state_id,default_address,et_gstin.getText().toString(),address_id);
                break;


        }
    }



    @Override
    protected void onDestroy() {
        editAddressPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void getAllCountries(List<CountriesStatesResponse.DataBean> data) {
        if(data.size()>0) {
            countries = new ArrayList<>();
            countries=data;
            sp_country.setAdapter(countryAdpater);
            countryAdpater.loadAllCountries(countries);
            if(!country_id.equals(""))
            {
                for (int i = 0; i < data.size(); i++) {
                    if(data.get(i).getId().equals(country_id))
                    {
                        sp_country.setSelection(i);
                        return;
                    }

                }
            }
            else {

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId().equals("99")) {
                    sp_country.setSelection(i);
                    return;
                }
            }
            }


        }


    }

    @Override
    public void getAllStatesByCountry(List<CountriesStatesResponse.DataBean> data) {

        if(data.size()>0) {
            states = new ArrayList<>();
            states=data;
            sp_state.setAdapter(stateAdapter);
            stateAdapter.loadStates(states);
            if(!state_id.equals(""))
            {
                for (int j = 0; j < data.size(); j++) {
                    if(data.get(j).getId().equals(state_id))
                    {
                        sp_state.setSelection(j);
                        return;
                    }

                }
            }
            else {
                states.add(0,new CountriesStatesResponse.DataBean("0","Please Select"));

            }

        }
    }

    @Override
    public void confirmEditAddress(String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin, String address_id) {
        editAddressPresenter.onEditAddress(fname,lname,comp,address1,address2,city,pin,country_id,state_id,default_address,gstin,address_id);

    }


    @Override
    public void openAddressBook() {

        Intent returnIntent = new Intent();
        if(getIntent().getExtras()!=null) {
            if (getIntent().getExtras().getInt(Constants.OPEN_FROM_CHECKOUT) == 1) {
                returnIntent.putExtra(Constants.OPEN_FROM_CHECKOUT, getIntent().getExtras().getInt(Constants.OPEN_FROM_CHECKOUT));
            } else if (getIntent().getExtras().getInt(Constants.OPEN_FROM_CHECKOUT) == 2) {
                returnIntent.putExtra(Constants.OPEN_FROM_CHECKOUT, getIntent().getExtras().getInt(Constants.OPEN_FROM_CHECKOUT));
            }
        }
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }

    @Override
    public void getAddressData() {
        Bundle bundle = this.getIntent().getExtras();

        if(bundle !=null)
        {
            address_id=bundle.getString("address_id");
            et_fname.setText(bundle.getString("fname"));
            et_lname.setText(bundle.getString("fname"));
            if(!bundle.getString("comp").equals(""))
            {
                et_comp.setText(bundle.getString("comp"));
            }
            if(!bundle.getString("gstin").equals(""))
            {
                et_gstin.setText(bundle.getString("gstin"));
            }
            et_address1.setText(bundle.getString("address1"));
            if(!bundle.getString("address2").equals(""))
            {
                et_address2.setText(bundle.getString("address2"));
            }
            et_city.setText(bundle.getString("city"));
            et_pin.setText(bundle.getString("pin"));
            country_id=bundle.getString("country_id");
            state_id=bundle.getString("state_id");

            if(bundle.getInt("default_address")==0)
            {
                ll_default.setVisibility(View.VISIBLE);
                rb_no.setChecked(true);
                default_address = 0;
            }
            else {
                ll_default.setVisibility(View.GONE);
                default_address = 1;
            }


                if (getIntent().getExtras().getInt(Constants.OPEN_FROM_CHECKOUT) == 1) {
                    bundle.putInt("billing_address",getIntent().getExtras().getInt(Constants.OPEN_FROM_CHECKOUT));
                }
                else  if (getIntent().getExtras().getInt(Constants.OPEN_FROM_CHECKOUT) == 2) {
                    bundle.putInt("shipping_address",getIntent().getExtras().getInt(Constants.OPEN_FROM_CHECKOUT));
            }
            editAddressPresenter.onGetAllCountries();
        }

    }

    @Override
    public void updateTextUI() {
        toolbar_title.setText("Edit Address");
        btn_add.setText("Update");
        editAddressPresenter.onGetAddressData();
    }

    @Override
    public void updateDefaultAddress() {



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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
