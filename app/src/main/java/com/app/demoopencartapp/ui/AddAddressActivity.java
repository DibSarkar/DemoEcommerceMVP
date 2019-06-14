package com.app.demoopencartapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.shared.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressActivity  extends BaseActivity {

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_addressbook);
        mContext = this;
        setUnBinder(ButterKnife.bind(this));
        setUp();
    }


    private void setUp() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

    }
}
