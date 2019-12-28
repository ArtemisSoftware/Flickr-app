package com.titan.flickrapp.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.titan.flickrapp.repository.FlickrRepository;
import com.titan.flickrapp.ui.gallery.GalleryActivity;
import com.titan.flickrapp.viewmodels.GalleryViewModel;
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

        if (modelClass.isAssignableFrom(GalleryViewModel.class)) {

            Timber.d("GalleryViewModel found...");
            return (T) new GalleryViewModel(repository);
        }

        Timber.d("ViewModel Unknown class name");

        throw new IllegalArgumentException("Unknown class name");
    }
}
