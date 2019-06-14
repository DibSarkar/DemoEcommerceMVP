package com.app.demoopencartapp.ui.editAccount;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.demoopencartapp.R;
import com.app.demoopencartapp.di.components.ActivityComponent;
import com.app.demoopencartapp.shared.base.BaseDialog;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordDialogFragment extends BaseDialog implements ChangePassDialogMvpView{

    public static final String TAG = ChangePasswordDialogFragment.class.getSimpleName();

    @BindView(R.id.edt_newpass)
    EditText edt_newpass;

    @BindView(R.id.edt_conpass)
    EditText edt_conpass;



    @Inject
    ChangePassDialogPresenter<ChangePassDialogMvpView> changePassDialogPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_dialog_change_pass, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, v));

            changePassDialogPresenter.onAttach(this);
        }
        setUp(v);
        return v;
    }

    @Override
    protected void setUp(View view) {
        setUnBinder(ButterKnife.bind(this,view));

    }

    @OnClick({R.id.btn_change})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_change:


                changePassDialogPresenter.onChangePass(edt_newpass.getText().toString(),edt_conpass.getText().toString());

                break;
        }

    }


    @Override
    public void onDestroyOptionsMenu() {
        changePassDialogPresenter.onDetach();
        super.onDestroyOptionsMenu();
    }

    @Override
    public void onDestroy() {
        changePassDialogPresenter.onDetach();
        super.onDestroy();

    }

    @Override
    public void changePassDone() {
        dismiss();

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
}

