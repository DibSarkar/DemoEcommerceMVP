package com.app.demoopencartapp.di.components;




import com.app.demoopencartapp.di.PerActivity;
import com.app.demoopencartapp.di.modules.ActivityModule;
import com.app.demoopencartapp.ui.CartActivity;
import com.app.demoopencartapp.ui.editAccount.ChangePasswordDialogFragment;
import com.app.demoopencartapp.ui.editAccount.EditMyAccount;
import com.app.demoopencartapp.ui.home.MainActivity;
import com.app.demoopencartapp.ui.login.LoginActivity;
import com.app.demoopencartapp.ui.myaccount.MyAccountActivity;
import com.app.demoopencartapp.ui.productDetails.ProductDetailsActivity;
import com.app.demoopencartapp.ui.zoom.ZoomActivity;
import com.app.demoopencartapp.ui.productList.ProductListActivity;
import com.app.demoopencartapp.ui.register.RegisterActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public  interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(ProductDetailsActivity activity);

    void inject(CartActivity activity);

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(ProductListActivity activity);

    void inject(ZoomActivity activity);

    void inject(MyAccountActivity activity);

    void inject(EditMyAccount activity);

    void inject(ChangePasswordDialogFragment dialogFragment);

    /* void inject(OrderSuccessActivity activity);

    void inject(NotificationActivity activity);

    void inject(NotificationDetailsActivity activity);

    void inject(MyAccountActivity activity);

    void inject(AddressActivity activity);

    void inject(AboutActivity activity);

    void inject(PrivacyActivity activity);

    void inject(TermsActivity activity);

    void inject(ContactActivity activity);*/

}