package com.app.demoopencartapp.data.prefs;

public interface SessionPreferenceHelper {

    String getSessionId();
    void setSessionId(String sessionId);
    void destroySessionPref();

}
