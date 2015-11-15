package ua.com.ukrelektro.passwordrec.model;

import android.app.Application;
import android.content.Context;

/**
 * Created by User on 11.11.2015.
 */
public class PasswordRecoveryApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        PasswordRecoveryApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return PasswordRecoveryApplication.context;
    }
}