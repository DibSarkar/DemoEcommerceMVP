package com.app.demoopencartapp.shared;

import android.content.Context;

import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.inject.Singleton;

@Singleton
public class CookiesManage {

    static CookieManager cookieManager;

    public static CookieManager getCookie()
    {
       cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return  cookieManager;
    }


    public static void removeCookies()
    {
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        cookieManager.getCookieStore().removeAll();
    }
}
