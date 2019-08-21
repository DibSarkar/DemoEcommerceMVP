package com.app.demoopencartapp.ui.addAddress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.CountriesStatesResponse;
import com.app.demoopencartapp.shared.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAddressActivity extends BaseActivity implements AddAddressMvpView{

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

    @Inject
    CountryStateAdpater countryAdpater;

    @Inject
    CountryStateAdpater stateAdapter;

    @Inject
    AddAddressPresenter<AddAddressMvpView> addAddressPresenter;

    List<CountriesStatesResponse.DataBean> countries;
    List<CountriesStatesResponse.DataBean> states;

    String country_id,state_id;

    int default_address = 1;

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
        addAddressPresenter.onAttach(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        addAddressPresenter.onGetAllCountries();

        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                addAddressPresenter.onGetStateByCountry(countries.get(position).getId());
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

    }


    @OnClick({R.id.btn_add})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.btn_add :
                addAddressPresenter.onConfirmAddress(et_fname.getText().toString(),et_lname.getText().toString(),et_comp.getText().toString(),et_address1.getText().toString(),et_address2.getText().toString(),et_city.getText().toString(),et_pin.getText().toString(),country_id,state_id,default_address,et_gstin.getText().toString());
                break;


        }
    }



    @Override
    protected void onDestroy() {
        addAddressPresenter.onDetach();
        super.onDestroy();
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

        }
    }

    @Override
    public void confirmAddress(String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address,String gstin) {

        addAddressPresenter.onAddAddress(fname,lname,comp,address1,address2,city,pin,country_id,state_id,default_address,gstin);
    }

    @Override
    public void openAddressBook() {

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

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
