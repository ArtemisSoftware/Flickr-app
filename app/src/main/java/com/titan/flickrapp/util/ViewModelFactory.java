package com.titan.flickrapp.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.titan.flickrapp.repository.FlickrRepository;
import com.titan.flickrapp.viewmodels.LoginViewModel;

import javax.inject.Inject;

import timber.log.Timber;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private FlickrRepository repository;

    @Inject
    public ViewModelFactory(FlickrRepository repository) {
        this.repository = repository;
        Timber.d("ViewModelFactory constructed");
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(LoginViewModel.class)) {

            Timber.d("LoginViewModel found...");
            return (T) new LoginViewModel(repository);
        }

        Timber.d("ViewModel IllegalArgumentException");

        throw new IllegalArgumentException("Unknown class name");
    }
}
