package com.app.demoopencartapp.data;


import com.app.demoopencartapp.data.network.ApiHelper;
import com.app.demoopencartapp.data.prefs.PreferencesHelper;
import com.app.demoopencartapp.data.prefs.SessionPreferenceHelper;

/**
 * Created by svk on 5/6/17.
 */

public interface DataManager extends ApiHelper, PreferencesHelper, SessionPreferenceHelper {

    void logout();


}
