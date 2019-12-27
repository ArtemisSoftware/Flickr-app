package com.titan.flickrapp;

import android.app.Application;
import android.content.Context;

import com.titan.flickrapp.di.AppComponent;
import com.titan.flickrapp.di.DaggerAppComponent;

import timber.log.Timber;

public class App extends Application {

    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        Timber.d("App onCreate...");

        appComponent = DaggerAppComponent.create();

        context = this;
    }


    public AppComponent getAppComponent() {
        Timber.d("Getting component...");
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
