package com.titan.flickrapp.di;

import com.titan.flickrapp.ui.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void doInjection(LoginActivity loginActivity);
}
