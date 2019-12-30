package com.titan.flickrapp.di;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.titan.flickrapp.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module
public class ImageModule {


    private Application context;

    public ImageModule(Application context) {
        this.context = context;
        Timber.d("AppModule constructed: " + context);
    }

    @Provides
    @Singleton
    Context provideContext() {

        Timber.d("Providing Context: " + context);
        return context;
    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions(){

        RequestOptions request = RequestOptions.placeholderOf(R.drawable.white_background)
                                               .error(R.drawable.white_background);

        Timber.d("Providing RequestOptions: " + request);

        return request;
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Context application, RequestOptions requestOptions){

        RequestManager request = Glide.with(application).setDefaultRequestOptions(requestOptions);

        Timber.d("Providing RequestManager: " + request);

        return request;
    }

}
