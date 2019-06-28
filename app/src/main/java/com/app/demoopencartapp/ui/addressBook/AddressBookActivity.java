package com.app.demoopencartapp.ui.addressBook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.network.models.AddressListResponse;
import com.app.demoopencartapp.shared.base.BaseActivity;
import com.app.demoopencartapp.ui.addAddress.AddAddressActivity;
import com.app.demoopencartapp.ui.editAddress.EditAddressActivity;
import com.app.demoopencartapp.utils.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressBookActivity extends BaseActivity implements AddressBookMvpView{

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_address_book)
    RecyclerView rv_address_book;

    @BindView(R.id.ll_address_book)
    LinearLayout ll_address_book;

    @BindView(R.id.tv_no_address)
    TextView tv_no_address;

    @Inject
    AddressBookAdapter addressBookAdapter;

    @Inject
    AddressBookPresenter<AddressBookMvpView> addressBookPresenter;

    public  static final int ADD_CODE = 400;
    public  static final int EDIT_CODE = 401;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressbook);
        mContext = this;
        setUnBinder(ButterKnife.bind(this));
        getActivityComponent().inject(this);
        setUp();
    }

    private void setUp()
    {
        addressBookPresenter.onAttach(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        addressBookPresenter.onGetAddress();
    }

    private void loadAdapter()
    {
        rv_address_book.setHasFixedSize(true);

        rv_address_book.setNestedScrollingEnabled(false);
        rv_address_book.setItemAnimator(new DefaultItemAnimator());
        rv_address_book.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_address_book.setAdapter(addressBookAdapter);
        Drawable dividerDrawable = ContextCompat.getDrawable(mContext, R.drawable.divider);
        rv_address_book.addItemDecoration(new DividerItemDecoration(dividerDrawable,false,false));

        addressBookAdapter.setAdapterListener(new AddressBookAdapter.AddressBookListener() {
            @Override
            public void onItemClick(AddressListResponse.ResponseDataBean item, int position) {

            }

            @Override
            public void onItemDelete(AddressListResponse.ResponseDataBean item, int position) {
                addressBookPresenter.onDeleteAddress(item,item.getAddress_id());

            }

            @Override
            public void onItemEdit(AddressListResponse.ResponseDataBean item) {
                addressBookPresenter.onOpenEditAddress(item.getAddress_id(),item.getFirstname(),item.getLastname(),item.getCompany(),item.getAddress_1(),item.getAddress_2(),item.getCity(),item.getPostcode(),item.getCountry_id(),item.getZone_id(),item.getDefault_address(),item.getGstin());

            }
        });
    }


    @OnClick({R.id.btn_add})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.btn_add :
                addressBookPresenter.onOpenAddAddress();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        addressBookPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void openAddAddress() {

        Intent intent = new Intent(mContext, AddAddressActivity.class);
        startActivityForResult(intent,ADD_CODE);

    }

    @Override
    public void getAddress(List<AddressListResponse.ResponseDataBean> responseData) {

        if(responseData.size()>0)
        {
            tv_no_address.setVisibility(View.GONE);
            ll_address_book.setVisibility(View.VISIBLE);
            loadAdapter();
            addressBookAdapter.loadAddress(responseData);
        }
        else {
            ll_address_book.setVisibility(View.GONE);
            tv_no_address.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void deleteAddress(AddressListResponse.ResponseDataBean responseDataBean) {

      /* if(addressBookAdapter!=null) {
           addressBookAdapter.setAdapterListener1(new AddressBookAdapter.AddressBookDeleteListener() {
               @Override
               public void onItemCheckAddress(List<AddressListResponse.ResponseDataBean> responseDataBeanList) {

                   System.out.println("caldedd"+responseDataBeanList);
                   if (responseDataBeanList.size() < 0) {

                       ll_address_book.setVisibility(View.GONE);
                       tv_no_address.setVisibility(View.VISIBLE);
                   }

               }
           });
           addressBookAdapter.deleteAddress(responseDataBean);*/

      addressBookPresenter.onGetAddress();


    }

    @Override
    public void openEditAddress(String address_id, String fname, String lname, String comp, String address1, String address2, String city, String pin, String country_id, String state_id, int default_address, String gstin) {

        Bundle bundle = new Bundle();
        bundle.putString("address_id",address_id);
        bundle.putString("fname",fname);
        bundle.putString("lname",lname);
        bundle.putString("comp",comp);
        bundle.putString("address1",address1);
        bundle.putString("address2",address2);
        bundle.putString("city",city);
        bundle.putString("pin",pin);
        bundle.putString("country_id",country_id);
        bundle.putString("state_id",state_id);
        bundle.putInt("default_address",default_address);
        bundle.putString("gstin",gstin);

        Intent intent = new Intent(mContext, EditAddressActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent,EDIT_CODE);

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
        if (requestCode == ADD_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                addressBookPresenter.onGetAddress();
            }
            else {
                System.out.println("new address added");
            }
        }
        else if (requestCode == EDIT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                addressBookPresenter.onGetAddress();
            }
            else {
                System.out.println("edit address modified");
            }
        }
    }


}
