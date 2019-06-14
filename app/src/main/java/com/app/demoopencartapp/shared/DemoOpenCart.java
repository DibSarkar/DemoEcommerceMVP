package com.app.demoopencartapp.shared;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.app.demoopencartapp.BuildConfig;
import com.app.demoopencartapp.data.DataManager;
import com.app.demoopencartapp.di.components.ApplicationComponent;
import com.app.demoopencartapp.di.components.DaggerApplicationComponent;
import com.app.demoopencartapp.di.modules.ApplicationModule;
import com.app.demoopencartapp.di.modules.NetworkModule;
import com.app.demoopencartapp.utils.Constants;
import com.app.demoopencartapp.utils.FakeCrashLibrary;
import com.app.demoopencartapp.utils.TypefaceUtil;


import javax.inject.Inject;


import timber.log.Timber;

public class DemoOpenCart extends Application {

    ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .networkModule(new NetworkModule(Constants.baseUrlLocal)).build();



    @Inject
    DataManager mDataManager;

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate() {
        super.onCreate();


        applicationComponent.inject(this);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "openSans.ttf");

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
        else Timber.plant(new CrashReportingTree());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //  MultiDex.install(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    private static final class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(tag,message);

            if (t != null) {
                FakeCrashLibrary.logError(t);
            }
        }
    }
}
