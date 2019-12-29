package com.titan.flickrapp.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.titan.flickrapp.R;

import timber.log.Timber;

public class PictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Timber.d("Picture");
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("photoid")){

        }
    }
}
