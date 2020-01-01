package com.titan.flickrapp.ui.gallery;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.titan.flickrapp.App;
import com.titan.flickrapp.R;
import com.titan.flickrapp.models.Picture;
import com.titan.flickrapp.ui.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import timber.log.Timber;

public class PictureActivity extends BaseActivity {

    @Inject
    RequestManager requestManager;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Timber.d("Picture");

        ((App) getApplication()).getAppComponent().doInjection(this);

        showProgressBar(true);
        getIncomingIntent();
        showProgressBar(false);
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("picture")){
            Picture picture = getIntent().getParcelableExtra("picture");
            setPictureProperties(picture);
        }
    }

    private void setPictureProperties(Picture picture) {

        if(picture != null){

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
}
