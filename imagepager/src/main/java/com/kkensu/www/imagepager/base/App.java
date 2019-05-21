package com.kkensu.www.imagepager.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

public class App extends Application {
    public static int VERSION_CODE;
    public static String PACKAGE_NAME;
    public static String VERSION_NAME;
    public static String CONTENT_PROVIDER_AUTHORITIES;
    public static Context context;
    public static boolean isCheckPerm = false;

    private static BaseActivity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }

    public static String getAppVersion() {
        try {
            VERSION_NAME = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return VERSION_NAME;
    }

    public static int getAppVersionCode() {
        try {
            VERSION_CODE = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // Huh? Really?
        }
        return VERSION_CODE;
    }

    public static Context getContext() {
        return context;
    }

    public static BaseActivity currentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(BaseActivity activity) {
        currentActivity = activity;
    }
}