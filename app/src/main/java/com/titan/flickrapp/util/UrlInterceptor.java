package com.titan.flickrapp.util;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class UrlInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        HttpUrl url;
        HttpUrl.Builder urlBuilder = request.url().newBuilder();

        if(request.url().toString().contains(ApiConstants.PUBLIC_PHOTOS_METHOD) == true){
            urlBuilder = urlPhotoList(urlBuilder);
        }

        url = urlBuilder
                .addQueryParameter("format", ApiConstants.FORMAT)
                .addQueryParameter("api_key", ApiConstants.API_KEY)
                .addQueryParameter("nojsoncallback", ApiConstants.NO_JSON_CALLBACK)
                .build();

        request = request.newBuilder().url(url).build();
        Timber.d("Intercepting OkHttpClient: " + request);
        return chain.proceed(request);
    }

    private HttpUrl.Builder urlPhotoList(HttpUrl.Builder urlBuilder){

        urlBuilder.addQueryParameter("per_page", ApiConstants.PER_PAGE);
        return urlBuilder;
    }


}
