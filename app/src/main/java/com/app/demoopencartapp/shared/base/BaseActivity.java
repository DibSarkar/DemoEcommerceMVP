/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.app.demoopencartapp.shared.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;


import com.app.demoopencartapp.R;
import com.app.demoopencartapp.di.components.ActivityComponent;
import com.app.demoopencartapp.di.components.DaggerActivityComponent;
import com.app.demoopencartapp.di.modules.ActivityModule;
import com.app.demoopencartapp.shared.DemoOpenCart;
import com.app.demoopencartapp.utils.CommonUtils;
import com.app.demoopencartapp.utils.NetworkUtils;

import butterknife.Unbinder;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity
        implements MvpView /*BaseFragment.Callback*/ {


    public static final String TAG = BaseActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;

    private ActivityComponent mActivityComponent;

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((DemoOpenCart) getApplication()).getApplicationComponent())
                .build();
    }


    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(String message) {
        hideKeyboard();
        if (message != null) {
            showErrorToast(message);
        } else {
            showErrorToast("Error Message is null");
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        hideKeyboard();
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Some error", Toast.LENGTH_LONG).show();
        }
    }

    public void showErrorToast(String message) {
        hideKeyboard();
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Some error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void logOut(){
      /*  Intent i = new Intent(this, SignInActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);*/

        /*LockManager<CustomPinActivity> lockManager = LockManager.getInstance();
        lockManager.getAppLock().setLogoId(R.mipmap.ic_launcher);
        lockManager.getAppLock().setShouldShowForgot(true);
        lockManager.getAppLock().setTimeout(Constants.TIMEOUT_MILLIES);
        lockManager.getAppLock().addIgnoredActivity(SplashActivity.class);
        lockManager.getAppLock().addIgnoredActivity(SignInActivity.class);
        lockManager.getAppLock().addIgnoredActivity(LoginEmailActivity.class);*/
    }


    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void showAlert(String message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e){
            Timber.tag(TAG).e(e);
        }
    }


    @Override
    public void showInactiveUserAlert(String message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    logOut();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }catch (Exception e){
            Timber.e(e);
        }
    }

    /*protected abstract void setUp();*/


    //App Lock related code
}
