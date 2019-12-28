package com.titan.flickrapp.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.titan.flickrapp.App;
import com.titan.flickrapp.R;
import com.titan.flickrapp.ui.BaseActivity;
import com.titan.flickrapp.util.ApiResponse;
import com.titan.flickrapp.util.ViewModelFactory;
import com.titan.flickrapp.viewmodels.GalleryViewModel;
import com.titan.flickrapp.viewmodels.LoginViewModel;

import javax.inject.Inject;

import timber.log.Timber;

public class GalleryActivity extends BaseActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    GalleryViewModel galleryViewModel;


    private String nsid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Timber.d("Gallery app");

        ((App) getApplication()).getAppComponent().doInjection(this);

        galleryViewModel = ViewModelProviders.of(this, viewModelFactory).get(GalleryViewModel.class);

        getIncomingIntent();
        galleryViewModel.searchGallery(nsid, "1");
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("nsid")){
            nsid = getIntent().getStringExtra("nsid");
            subscribeObservers();
        }
    }


    private void subscribeObservers(){

        galleryViewModel.observeGallery().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {

                Timber.d("onChanged: " + apiResponse.toString());

                switch (apiResponse.status){


                    case LOADING:

                        showProgressBar(true);
                        break;

                    case SUCCESS:


                        showProgressBar(false);
                        break;

                    case ERROR:

                        Toast.makeText(getApplicationContext(),"Error: " + apiResponse.message,Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                        break;
                }
            }
        });
    }
}
