package com.app.demoopencartapp.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.shared.base.BaseActivity;
import com.app.demoopencartapp.ui.home.MainActivity;
import com.app.demoopencartapp.ui.register.RegisterActivity;
import com.app.demoopencartapp.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginMvpView{

    Context mContext;

    @Inject
    LoginPresenter<LoginMvpView> loginPresenter;

    @BindView(R.id.et_email)
    EditText et_mail;

    @BindView(R.id.et_pass)
    EditText et_pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mContext = this;
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        setUp();
    }

    private void setUp()
    {

        loginPresenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDetach();
        super.onDestroy();
    }

    @OnClick({R.id.btn_login,R.id.ll_signup})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.btn_login :
              loginPresenter.onConfirmLogin(et_mail.getText().toString(),et_pass.getText().toString());

                break;

            case R.id.ll_signup :
                loginPresenter.onOpenSignup();
                break;


        }
    }


    @Override
    public void confirmLogin(String email, String password) {
        loginPresenter.onLogin(email,password,"A","123");

    }

    @Override
    public void loginDone() {

        if(getIntent().getExtras().getInt(Constants.OPEN_FROM_LOGIN)==1)
        {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        else if(getIntent().getExtras().getInt(Constants.OPEN_FROM_LOGIN)==2)
        {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        else {
            Intent intent = new Intent(mContext,MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void openSignup() {

        Intent intent = new Intent(mContext,RegisterActivity.class);
        startActivity(intent);

    }


}
