package com.titan.flickrapp;

import android.app.Application;
import android.content.Context;

import com.titan.flickrapp.di.AppComponent;
import com.titan.flickrapp.di.AppModule;
import com.titan.flickrapp.di.DaggerAppComponent;
import com.titan.flickrapp.di.ImageModule;

import timber.log.Timber;

public class App extends Application {

    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        Timber.d("App onCreate...");

        //appComponent = DaggerAppComponent.create();
        appComponent = DaggerAppComponent.builder().imageModule(new ImageModule(this)).build();
        //appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        //appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).utilsModule(new UtilsModule()).build();
        //appComponent = DaggerAppComponent.builder().build();

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
