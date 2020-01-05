package com.titan.flickrapp.ui.gallery;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.titan.flickrapp.App;
import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;
import com.titan.flickrapp.ui.BaseActivity;
import com.titan.flickrapp.util.ApiResponse;
import com.titan.flickrapp.util.AppConstants;
import com.titan.flickrapp.util.ViewModelFactory;
import com.titan.flickrapp.viewmodels.GalleryViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import timber.log.Timber;

public class PictureActivity extends BaseActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    RequestManager requestManager;


    private GalleryViewModel galleryViewModel;

    @BindView(R.id.picture_image)
    ImageView picture_image;

    @BindView(R.id.txt_title)
    TextView txt_title;

    @BindView(R.id.txt_description)
    TextView txt_description;

    @BindView(R.id.txt_date)
    TextView txt_date;

    @BindView(R.id.tc_tags)
    TagContainerLayout tc_tags;

    @BindView(R.id.lnr_lyt_picture_data)
    LinearLayout lnr_lyt_picture_data;

    private Picture picture;
    private final String PICTURE = "picture";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Timber.d("Picture");

        ((App) getApplication()).getAppComponent().doInjection(this);

        galleryViewModel = ViewModelProviders.of(this, viewModelFactory).get(GalleryViewModel.class);

        showProgressBar(true);
        subscribeObservers();


        if(savedInstanceState == null) {
            getIncomingIntent();
        }
        else{
            setPictureProperties(savedInstanceState.getParcelable(PICTURE));
        }


        showProgressBar(false);
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

                        setPictureProperties((Picture)apiResponse.data);
                        showProgressBar(false);
                        break;

                    case ERROR:

                        Toast.makeText(getApplicationContext(), "Error: " + apiResponse.message, Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                        break;
                }
            }
        });
    }





    private void getIncomingIntent(){
        if(getIntent().hasExtra(AppConstants.PICTURE)){
            galleryViewModel.searchPicture(getIntent().getStringExtra(AppConstants.PICTURE));
        }
    }

    private void setPictureProperties(Picture picture) {

        if(picture != null){

            this.picture = picture;
            txt_title.setText(picture.getTitle());
            txt_description.setText(picture.getDescription());
            txt_date.setText(picture.getDate());
            tc_tags.setTags(picture.getTags());

            requestManager.load(picture.getUrl()).into(picture_image);

        }
    }

    @OnClick(R.id.picture_image)
    public void picture_image__onImageClick(View view) {

        int visible = lnr_lyt_picture_data.getVisibility();

        if(visible == View.VISIBLE){
            visible= View.GONE;
        }
        else{
            visible= View.VISIBLE;
        }

        lnr_lyt_picture_data.setVisibility(visible);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(PICTURE, this.picture);
    }

}
