package com.app.demoopencartapp.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.data.local_models.CartListBean;
import com.app.demoopencartapp.shared.base.BaseActivity;

import com.app.demoopencartapp.ui.home.MainActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterMvpView{

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rg_news)
    RadioGroup rg_news;

    @BindView(R.id.et_fname)
    EditText et_fname;

    @BindView(R.id.et_lname)
    EditText et_lname;

    @BindView(R.id.et_email)
    EditText et_email;

    @BindView(R.id.et_telephone)
    EditText et_telephone;

    @BindView(R.id.et_gstin)
    EditText et_gstin;

    @BindView(R.id.et_pass)
    EditText et_pass;

    @BindView(R.id.et_confirmpass)
    EditText et_confirmpass;

    int news_letter = 1;



    @Inject
    RegisterPresenter<RegisterMvpView> registerPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        mContext = this;
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));
        setUp();
    }


    private void setUp() {
        registerPresenter.onAttach(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        rg_news.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rb_yes:
                        news_letter = 1;

                        break;
                    case R.id.rb_no:
                        news_letter = 0;
                        break;

                }
            }
        });


    }

    @Override
    public void registerDone() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void confirmRegistrationDone(String firstName, String lastName, String email, String telephone, String password, int newsletter, String gstin) {

        registerPresenter.onSubmitRegistration(firstName,lastName,email,telephone,password,newsletter,gstin,"A","123");
    }

    @OnClick({R.id.btn_sign_up})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.btn_sign_up :
              registerPresenter.onConfirmRegistration(et_fname.getText().toString(),et_lname.getText().toString(),et_email.getText().toString(),et_telephone.getText().toString(),et_pass.getText().toString(),news_letter,et_gstin.getText().toString(),et_confirmpass.getText().toString());

                break;


        }
    }

    @Override
    protected void onDestroy() {
        registerPresenter.onDetach();
        super.onDestroy();
    }
}