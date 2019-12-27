package com.titan.flickrapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.titan.flickrapp.App;
import com.titan.flickrapp.R;

import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Timber.d("Init app");

        ((App) getApplication()).getAppComponent().doInjection(this);
    }
}
