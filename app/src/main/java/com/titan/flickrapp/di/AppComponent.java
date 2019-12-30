package com.titan.flickrapp.di;

import com.titan.flickrapp.ui.gallery.GalleryActivity;
import com.titan.flickrapp.ui.gallery.PictureActivity;
import com.titan.flickrapp.ui.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {/*AppModule.class,*/ NetworkModule.class, ImageModule.class})
@Singleton
public interface AppComponent {

    void doInjection(LoginActivity loginActivity);

    void doInjection(GalleryActivity galleryActivity);

    void doInjection(PictureActivity pictureActivity);
}
