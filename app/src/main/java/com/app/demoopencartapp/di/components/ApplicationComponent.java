package com.app.demoopencartapp.di.components;


import android.app.Application;
import android.content.Context;


import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.di.ApplicationContext;
import com.app.demoopencartapp.di.modules.ApplicationModule;
import com.app.demoopencartapp.di.modules.NetworkModule;
import com.app.demoopencartapp.shared.DemoOpenCart;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(DemoOpenCart app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}