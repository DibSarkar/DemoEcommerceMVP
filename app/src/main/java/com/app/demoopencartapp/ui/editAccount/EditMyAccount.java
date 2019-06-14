package com.app.demoopencartapp.ui.editAccount;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.shared.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditMyAccount extends BaseActivity implements EditAccountMvpView{

    Context mContext;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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

    @BindView(R.id.tv_change_pass)
    TextView tv_change_pass;

    @BindView(R.id.rb_yes)
    RadioButton rb_yes;

    @BindView(R.id.rb_no)
    RadioButton rb_no;

    @BindView(R.id.rg_news)
    RadioGroup rg_news;

    @Inject
    EditAccountPresenter<EditAccountMvpView> editAccountPresenter;

    int news_letter;

    ChangePasswordDialogFragment changePasswordDialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_account);
        mContext = this;
        setUnBinder(ButterKnife.bind(this));
        getActivityComponent().inject(this);
        setUp();
    }


    private void setUp() {
        editAccountPresenter.onAttach(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        editAccountPresenter.onGetInfo();

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


    @OnClick({R.id.btn_update,R.id.tv_change_pass})
    void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.btn_update :

                editAccountPresenter.onConfirmEdit(et_fname.getText().toString(),et_lname.getText().toString(),et_email.getText().toString(),et_telephone.getText().toString(),et_gstin.getText().toString(), news_letter);
                break;

            case R.id.tv_change_pass :
                editAccountPresenter.onShowChangePass();
                break;


        }
    }
    @Override
    protected void onDestroy() {
        editAccountPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void getInfo(String firstname, String lastname, String email, String telephone, String gstin, String newsletter) {

        et_fname.setText(firstname);
        et_lname.setText(lastname);
        et_email.setText(email);
        et_telephone.setText(telephone);
        et_gstin.setText(gstin);
        news_letter=Integer.parseInt(newsletter);
        if(newsletter.equals("1"))
        {
            rb_yes.setChecked(true);
        }
        else {
            rb_no.setChecked(true);
        }

    }

    @Override
    public void confirmUpdate(String firstname, String lastname, String email, String telephone, String gstin, int newsletter) {

        editAccountPresenter.onSubmitEditAccount(firstname,lastname,email,telephone,gstin, String.valueOf(newsletter));
    }

    @Override
    public void showChangePassDialog() {

        changePasswordDialogFragment = new ChangePasswordDialogFragment();
        changePasswordDialogFragment.show(getSupportFragmentManager(), "changePass");


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


