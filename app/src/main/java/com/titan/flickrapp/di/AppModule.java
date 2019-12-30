package com.titan.flickrapp.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
        Timber.d("AppModule constructed: " + context);
    }

    @Provides
    @Singleton
    Context provideContext() {

        Timber.d("Providing Context: " + context);
        return context;
    }
}
