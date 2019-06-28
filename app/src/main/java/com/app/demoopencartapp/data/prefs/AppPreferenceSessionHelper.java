package com.app.demoopencartapp.data.prefs;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.demoopencartapp.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferenceSessionHelper implements SessionPreferenceHelper{

    private static final String PREF_KEY_CURRENT_SESSION = "PREF_KEY_CURRENT_SESSION";


    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferenceSessionHelper(@ApplicationContext Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }


    @Override
    public String getSessionId() {
        return mPrefs.getString(PREF_KEY_CURRENT_SESSION, "");

    }

    @Override
    public void setSessionId(String sessionId) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_SESSION, sessionId).apply();
    }

    @Override
    public void destroySessionPref() {
        mPrefs.edit().clear().apply();
    }
}
