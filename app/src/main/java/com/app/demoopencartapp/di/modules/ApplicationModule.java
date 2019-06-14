package com.app.demoopencartapp.di.modules;


import android.app.Application;
import android.content.Context;


import com.app.demoopencartapp.data.AppDataManager;
import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.data.prefs.AppPreferencesHelper;
import com.app.demoopencartapp.data.prefs.PreferencesHelper;
import com.app.demoopencartapp.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by svk on 5/6/17.
 */
@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    PreferencesHelper providePrefHelper(AppPreferencesHelper mAppPreferencesHelper){
        return mAppPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }
}
