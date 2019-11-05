package com.donggeon.honmaker;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

public class App extends Application {
    private static App INSTANCE;

    public static App instance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        FirebaseApp.initializeApp(this);
    }

    public Context getContext() {
        return getApplicationContext();
    }
}
