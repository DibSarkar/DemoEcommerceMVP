package com.app.demoopencartapp.ui.myaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.shared.base.BaseActivity;

import com.app.demoopencartapp.ui.addressBook.AddressBookActivity;
import com.app.demoopencartapp.ui.editAccount.EditMyAccount;
import com.app.demoopencartapp.ui.addAddress.AddAddressActivity;
import com.app.demoopencartapp.ui.wishlist.WishlistActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAccountActivity extends BaseActivity implements MyAccountMvpView {

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @Inject
    MyAccountPresenter<MyAccountMvpView> myAccountPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myprofile);
        mContext = this;
        setUnBinder(ButterKnife.bind(this));
        getActivityComponent().inject(this);
        setUp();
    }


    private void setUp() {
        myAccountPresenter.onAttach(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");


    }

    @Override
    protected void onDestroy() {
        myAccountPresenter.onDetach();
        super.onDestroy();
    }

    @OnClick({R.id.tv_edit_acc,R.id.tv_address_book,R.id.tv_wishlist})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.tv_edit_acc :

                myAccountPresenter.onOpenEditAccount();
                break;

            case R.id.tv_address_book :
                myAccountPresenter.onOpenAddressBook();
                break;

            case R.id.tv_wishlist :
                myAccountPresenter.onOpenWishlist();
                break;


        }
    }

    @Override
    public void openEditAccount() {
        Intent intent = new Intent(mContext, EditMyAccount.class);
        startActivity(intent);
    }

    @Override
    public void openAddressBook() {
        Intent intent1 = new Intent(mContext, AddressBookActivity.class);
        startActivity(intent1);
    }

    @Override
    public void openWishlist() {
        Intent intent1 = new Intent(mContext, WishlistActivity.class);
        startActivity(intent1);
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

