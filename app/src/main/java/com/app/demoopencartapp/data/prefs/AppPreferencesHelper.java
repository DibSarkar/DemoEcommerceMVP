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

package com.app.demoopencartapp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.app.demoopencartapp.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_CURRENT_FIRST_NAME = "PREF_KEY_CURRENT_FIRST_NAME";
    private static final String PREF_KEY_CURRENT_LAST_NAME = "PREF_KEY_CURRENT_LAST_NAME";
    private static final String PREF_KEY_CURRENT_USER_MOB = "PREF_KEY_CURRENT_MOB";
    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
    private static final String PREF_KEY_CURRENT_USER_GENDER= "PREF_KEY_CURRENT_USER_GENDER";
    private static final String PREF_KEY_CURRENT_USER_REGTYPE= "PREF_KEY_CURRENT_USER_RGTYPE";
    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL
            = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";

    public static final String KEY_SP_LAST_INTERACTION_TIME = "KEY_SP_LAST_INTERACTION_TIME";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public String getCurrentUserId() {
        String userId = mPrefs.getString(PREF_KEY_CURRENT_USER_ID, "");
        return userId;
    }

    @Override
    public void setCurrentUserId(String userId) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_ID, userId).apply();
    }

    @Override
    public String getCurrentFirstName() {
        return mPrefs.getString(PREF_KEY_CURRENT_FIRST_NAME, "");
    }

    @Override
    public void setCurrentFirstName(String firstName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_FIRST_NAME, firstName).apply();
    }

    @Override
    public String getCurrentLastName() {
        return mPrefs.getString(PREF_KEY_CURRENT_LAST_NAME, "");
    }

    @Override
    public void setCurrentLastName(String lastName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_LAST_NAME, lastName).apply();
    }

    @Override
    public String getCurrentMobileNumber() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_MOB, "");
    }

    @Override
    public void setCurrentMobileNumber(String mobile) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_MOB, mobile).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, "");
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, "");
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
    }

    @Override
    public void setCurrentUserGender(String gender) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_GENDER, gender).apply();
    }

    @Override
    public String getCurrentUserGender() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_GENDER, "");
    }

    @Override
    public long getLastIntaractionTimestamp() {
        return mPrefs.getLong(KEY_SP_LAST_INTERACTION_TIME, 0);
    }

    @Override
    public void setLastIntaractionTimestamp(long timeStamp) {
        mPrefs.edit().putLong(KEY_SP_LAST_INTERACTION_TIME, timeStamp).apply();
    }

    @Override
    public void setRegistrationType(String registrationType) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_REGTYPE, registrationType).apply();
    }

    @Override
    public String getRegistrationType() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_REGTYPE, "");
    }

    @Override
    public void destroyPref() {
        mPrefs.edit().clear().apply();
    }

    public static String getSharedPreferencesString(Context context, String key, String _default){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, _default);
    }
}
