package ua.com.ukrelektro.passwordrec.model;

import android.app.Application;
import android.content.Context;

/**
 * Created by User on 11.11.2015.
 */
public class MyApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}