package com.titan.flickrapp.di;

import androidx.lifecycle.ViewModelProvider;

import com.titan.flickrapp.repository.FlickrRepository;
import com.titan.flickrapp.requests.FlickrApi;
import com.titan.flickrapp.util.ApiConstants;
import com.titan.flickrapp.util.UrlInterceptor;
import com.titan.flickrapp.util.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class AppModule {


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new UrlInterceptor())
                .build();

        Timber.d("Providing OkHttpClient: " + client);
        return client;
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Timber.d("Providing retrofit: " + retrofit);
        return retrofit;
    }



    @Provides
    @Singleton
    FlickrApi provideFlickrApiInterface(Retrofit retrofit) {

        FlickrApi api = retrofit.create(FlickrApi.class);
        Timber.d("Providing FlickrApi: " + api);

        return api;
    }


    @Provides
    @Singleton
    FlickrRepository provideRepository(FlickrApi apiInterface) {

        FlickrRepository repository = new FlickrRepository(apiInterface);
        Timber.d("Providing repository: " + repository);

        return repository;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(FlickrRepository flickrRepository) {

        ViewModelFactory viewModel = new ViewModelFactory(flickrRepository);
        Timber.d("Providing ViewModelProvider.Factory: " + viewModel);

        return viewModel;
    }

}
